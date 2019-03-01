package com.legalfriend.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.legalfriend.entities.Role;
import com.legalfriend.entities.User;
import com.legalfriend.repository.UserRepository;

/**
 * Created by deepak.j
 */
@Component
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		List<User> users = new ArrayList<>();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		if (request.getParameter("clientId") != null) {
			Long clientId = Long.valueOf(request.getParameter("clientId"));
			users = userRepository.findByEmailAndClientId(s.toLowerCase(), clientId);
		} else {
			users = userRepository.findByEmail(s.toLowerCase());
		}
		if (users.isEmpty()) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		User user = users.get(0);
		for (Role userRole : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), authorities);

		return userDetails;
	}
}
