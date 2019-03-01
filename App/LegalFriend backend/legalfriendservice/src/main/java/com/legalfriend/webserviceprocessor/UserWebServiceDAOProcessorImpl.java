package com.legalfriend.webserviceprocessor;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
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
public class UserWebServiceDAOProcessorImpl implements UserWebServiceDAOProcessor {

	private static final Logger logger = LoggerFactory.getLogger(UserWebServiceDAOProcessor.class);

	@Override
	public Long signup(User user) {
		RestTemplate restTemplate = new RestTemplate();
		Long userId = restTemplate.postForObject("http://localhost:7777/users/user", user, Long.class);
		logger.info(userId.toString());
		return userId;
	}

	@Override
	public JwtAuthenticationToken loginUser(String username, String password) {
		 
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.set("Authorization", getAuthorizationHeader("testjwtclientid","XY7kmzoNzl100"));
		//RestTemplate template = new RestTemplate();
		//MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(username);
		userLogin.setPassword(password);
		//String url = "http://localhost:7777/login";
		//HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		JwtAuthenticationToken jwtAuthenticationToken = restTemplate.postForObject("http://localhost:7777/login", userLogin, JwtAuthenticationToken.class);
		return jwtAuthenticationToken;
	}
	
	private String getAuthorizationHeader(String clientId, String clientSecret) {
        String creds = String.format("%s:%s", clientId, clientSecret);
        try {
            return "Basic " + new String(Base64.getEncoder().encode(creds.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not convert String");
        }
    }

	@Override
	public List<User> listUser(String clientId) {
		RestTemplate restTemplate = new RestTemplate();
		List<User> users = restTemplate.getForObject("http://localhost:7777/usermanagement/listusers?clientId=" + clientId, List.class);
		return users;
	}
	
	@Override
	public User addUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		User userdetails = restTemplate.postForObject("http://localhost:7777/users/user", user, User.class);		
		return userdetails;
	}
	
	@Override
	public User editUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		User userdetails = restTemplate.postForObject("http://localhost:7777/usermanagement/edituser", user, User.class);		
		return userdetails;
	}
	
	private User getClientDetails(String username){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:7777/users/getclientdetails?username="+username,User.class);
	}
	
	@Override
	public List<Role> listRoles(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<List<Role>> responseEntity =  restTemplate.exchange("http://localhost:7777/usermanagement/listroles", HttpMethod.GET, request, new ParameterizedTypeReference<List<Role>>() {});
		/*HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		List<Role> roles = restTemplate.getForObject("http://localhost:7777/usermanagement/listroles", List.class);*/
		return responseEntity.getBody();
	}

}
