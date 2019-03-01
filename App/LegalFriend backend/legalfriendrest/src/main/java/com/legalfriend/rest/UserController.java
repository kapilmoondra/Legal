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

import com.legalfriend.model.JwtAuthenticationToken;
import com.legalfriend.model.Role;
import com.legalfriend.model.User;
import com.legalfriend.model.UserLogin;
import com.legalfriend.webserviceprocessor.UserWebServiceProcessor;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserWebServiceProcessor userWebServiceProcessor;
    
    @PostMapping("/user")
    public Long signup(@RequestBody User user) {
    	return userWebServiceProcessor.signup(user);
    }
    
    @GetMapping("/listusers")
    public List<User> listUser(@RequestParam String clientId) {
    	return userWebServiceProcessor.listUser(clientId);
    }
    
    @PostMapping("/login")
    public JwtAuthenticationToken login(@RequestBody UserLogin userLogin) {
    	return userWebServiceProcessor.login(userLogin.getUsername(), userLogin.getPassword());
    }
    
    @PostMapping("/addusers")  // add user to client(isClient-> no and client id should not be null)
    public User addUser(@RequestBody User user, @RequestHeader("Authorization") String authorization) {
    	return userWebServiceProcessor.addUser(user, authorization);
    }
    
    @PostMapping("/editusers")  // edit user to client(isClient-> no and client id should not be null)
    public User editUser(@RequestBody User user, @RequestHeader("Authorization") String authorization) {
    	return userWebServiceProcessor.editUser(user, authorization);
    }
    
    @GetMapping("/listroles")
    public List<Role> listRoles(@RequestHeader("Authorization") String authorization) {
    	return userWebServiceProcessor.listRoles(authorization);
    }
    
}
