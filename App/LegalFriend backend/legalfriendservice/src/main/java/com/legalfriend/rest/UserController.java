package com.legalfriend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.model.Role;
import com.legalfriend.model.User;
import com.legalfriend.webserviceprocessor.UserWebServiceDAOProcessor;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserWebServiceDAOProcessor userWebServiceProcessor;

	@PostMapping("/user")
	public Long signup(@RequestBody User user) {
		return userWebServiceProcessor.signup(user);
	}

	@GetMapping("/listusers")
	public List<User> listUser(@RequestParam String clientId) {
		return userWebServiceProcessor.listUser(clientId);
	}
	
	@PostMapping("/adduser")
	public User addUser(@RequestBody User user) {
		return userWebServiceProcessor.addUser(user);
	}
	
	@PostMapping("/edituser")
	public User editUser(@RequestBody User user) {
		return userWebServiceProcessor.editUser(user);
	}
	
	@GetMapping("/listroles")
	public List<Role> listRoles(@RequestHeader("Authorization") String authorization) {
		return userWebServiceProcessor.listRoles(authorization);
	}


}
