package com.legalfriend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.legalfriend.entities.LoginTrack;
import com.legalfriend.entities.User;
import com.legalfriend.repository.LoginTrackRepository;
import com.legalfriend.repository.UserLoginRepository;
import com.legalfriend.repository.UserRepository;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserRepository repository;

	private LoginTrackRepository loginTrackRepo;

	private AccountCredentials creds;

	public JWTLoginFilter(String url, AuthenticationManager authManager, UserRepository repository,
			UserLoginRepository loginRepository, LoginTrackRepository loginTrackRepo) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.repository = repository;
		this.loginTrackRepo = loginTrackRepo;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
				creds.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		List<User> user = new ArrayList<>();
		if (creds.getClientId() == null) {
			user = repository.findByEmail(creds.getUsername());
		} else {
			user = repository.findByEmailAndClientId(creds.getUsername(), creds.getClientId());
		}
		if (user.size() > 0 && user.get(0).isVerified() && user.get(0).getStatus().getStatusName().equals("ACTIVE")) {
			TokenAuthenticationService.addAuthentication(res, auth.getName(), user.get(0).getId());
			loginTrackRepo.save(new LoginTrack(user.get(0).getId(), new Date()));
		} else {
			TokenAuthenticationService.throwAuthError(res);
		}
	}
}
