package com.legalfriend.webserviceprocessor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.legalfriend.model.JwtAuthenticationToken;
import com.legalfriend.model.Role;
import com.legalfriend.model.User;
import com.legalfriend.model.UserLogin;

@Service
public class UserWebServiceProcessorImpl implements UserWebServiceProcessor {

	private static final Logger logger = LoggerFactory.getLogger(UserWebServiceProcessor.class);

	@Override
	public Long signup(User user) {
		RestTemplate restTemplate = new RestTemplate();
		Long userId = restTemplate.postForObject("http://localhost:8888/users/user", user, Long.class);
		logger.info(userId.toString());
		return userId;
	}

	@Override
	public JwtAuthenticationToken login(String username, String password) {
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(username);
		userLogin.setPassword(password);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject("http://localhost:8888/login/dologin", userLogin,
				JwtAuthenticationToken.class);
	}

	@Override
	public List<User> listUser(String clientId) {
		RestTemplate restTemplate = new RestTemplate();
		List<User> users = restTemplate.getForObject("http://localhost:8888/users/listusers?clientId=" + clientId,
				List.class);
		return users;
	}

	@Override
	public User addUser(User user, String token) {
		RestTemplate restTemplate = new RestTemplate();
		User addedUser = restTemplate.postForObject("http://localhost:8888/users/adduser", user, User.class);
		return addedUser;
	}

	@Override
	public User editUser(User user, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<User> responseEntity = restTemplate.exchange(
				"http://localhost:8888/users/edituser", HttpMethod.POST, request,
				new ParameterizedTypeReference<User>() {
				});

		return responseEntity.getBody();
	}

	@Override
	public List<Role> listRoles(String authHeader) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authHeader);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<List<Role>> responseEntity = restTemplate.exchange(
				"http://localhost:7777/usermanagement/listroles", HttpMethod.GET, request,
				new ParameterizedTypeReference<List<Role>>() {
				});

		return responseEntity.getBody();

	}

}
