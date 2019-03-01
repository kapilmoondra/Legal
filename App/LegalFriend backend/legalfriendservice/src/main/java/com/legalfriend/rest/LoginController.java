package com.legalfriend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.model.JwtAuthenticationToken;
import com.legalfriend.model.UserLogin;
import com.legalfriend.webserviceprocessor.UserWebServiceDAOProcessor;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserWebServiceDAOProcessor userWebServiceProcessor;
        
    @PostMapping("/dologin")
    public JwtAuthenticationToken addUser(@RequestBody UserLogin userLogin) {
    	return userWebServiceProcessor.loginUser(userLogin.getUsername(), userLogin.getPassword());
    }
    
}
