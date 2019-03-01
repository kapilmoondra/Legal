package com.legalfriend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.Notification;
import com.legalfriend.entities.NotificationType;
import com.legalfriend.entities.User;
import com.legalfriend.entities.UserResponse;
import com.legalfriend.repository.NotificationRepository;
import com.legalfriend.repository.NotificationTypeRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.response.LegalFriendResponse;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	NotificationTypeRepository notificationTypeRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/email")
	public ResponseEntity<List<UserResponse>> getClientDetails(@RequestParam("username") String username) {
		List<User> users = userRepository.findByEmailContaining(username.toLowerCase());
		List<UserResponse> emails = new ArrayList<>();
		for (User user : users) {
			UserResponse response = new UserResponse();
			response.setEmail(user.getEmail());
			response.setId(user.getId());
			response.setName(user.getFirstName() + " " + user.getLastName());
			emails.add(response);
		}
		return new ResponseEntity<List<UserResponse>>(emails, HttpStatus.OK);
	}

	@GetMapping("/type")
	public List<NotificationType> getNotificationType() {
		List<NotificationType> notificationTypes = new ArrayList<>();
		notificationTypeRepository.findAll().forEach(notificationTypes::add);
		return notificationTypes;
	}

	@PostMapping
	public ResponseEntity<LegalFriendResponse> createNotification(@RequestBody Notification notification) {
		String notificationType = notificationTypeRepository.findById(notification.getNotificationType().getId())
				.getName();
		if (notificationType.equals("ALL_USERS")) {
			List<User> users = userRepository.findAllAdmin();
			for (User user : users) {
				Notification notification2 = new Notification();
				notification2.setNotificationType(notification.getNotificationType());
				notification2.setUserId(user.getId());
				notification2.setSubject(notification.getSubject());
				notification2.setDescription(notification.getDescription());
				notificationRepository.save(notification2);
			}
		} else if (notificationType.equals("ALL_EMP")) {

		} else if (notificationType.equals("OTHER")) {
			List<Long> userIds = notification.getSendTo();
			List<User> users = userRepository.findByIdIn(userIds);
			for (User user : users) {
				Notification notification2 = new Notification();
				notification2.setNotificationType(notification.getNotificationType());
				notification2.setUserId(user.getId());
				notification2.setSubject(notification.getSubject());
				notification2.setDescription(notification.getDescription());
				notificationRepository.save(notification2);
			}
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		friendResponse.setHttpCode(HttpStatus.OK.value());
		friendResponse.setSuccessMessage("Notification has been sent successfully");
		return new ResponseEntity<>(friendResponse, HttpStatus.OK);
	}

	@GetMapping
	public List<Notification> getNotifications(@RequestParam("userId") Long userId) {
		return notificationRepository.findByUserId(userId);
	}
}
