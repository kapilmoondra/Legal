package com.legalfriend.webserviceprocessor;

import java.util.List;

import com.legalfriend.model.JwtAuthenticationToken;
import com.legalfriend.model.Role;
import com.legalfriend.model.User;

public interface UserWebServiceDAOProcessor {
	Long signup(User user);

	JwtAuthenticationToken loginUser(String username, String password);

	List<User> listUser(String clientId);
	
	User addUser(User user);
	
	User editUser(User user);
	
	List<Role> listRoles(String token);
}
