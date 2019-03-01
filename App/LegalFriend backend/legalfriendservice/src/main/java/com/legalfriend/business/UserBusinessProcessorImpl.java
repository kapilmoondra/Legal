package com.legalfriend.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.legalfriend.model.User;

@Service
public class UserBusinessProcessorImpl implements UserBusinessProcessor {

	private static final Logger logger = LoggerFactory.getLogger(UserBusinessProcessor.class);

	@Override
	public Long addUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		Long userId = restTemplate.postForObject("http://localhost:7777/users/user", user, Long.class);
		logger.info(userId.toString());
		return userId;
	}

}
