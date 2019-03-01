package com.legalfriend.webserviceprocessor;

import java.util.List;

import com.legalfriend.model.JwtAuthenticationToken;
import com.legalfriend.model.Role;
import com.legalfriend.model.User;

public interface UserWebServiceProcessor {
	Long signup(User user);

	JwtAuthenticationToken login(String username, String password);

	List<User> listUser(String clientId);
	
	List<Role> listRoles(String authorization);
	
	User editUser(User user, String token);

	User addUser(User user, String token);
}
