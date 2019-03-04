package com.legalfriend.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.InvoiceNumber;
import com.legalfriend.entities.Organization;
import com.legalfriend.entities.Referral;
import com.legalfriend.entities.Role;
import com.legalfriend.entities.Status;
import com.legalfriend.entities.SubscriptionMaster;
import com.legalfriend.entities.User;
import com.legalfriend.entities.UserClient;
import com.legalfriend.entities.UserPassword;
import com.legalfriend.entities.UserPasswordToken;
import com.legalfriend.entities.UserRegisterToken;
import com.legalfriend.entities.UserSubscription;
import com.legalfriend.entities.UserType;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.InvoiceNumberRepository;
import com.legalfriend.repository.ReferralRepository;
import com.legalfriend.repository.RoleRepository;
import com.legalfriend.repository.StatusRepository;
import com.legalfriend.repository.SubscriptionRepository;
import com.legalfriend.repository.UserPasswordLoginRepository;
import com.legalfriend.repository.UserRegistrationRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserSubscriptionRepository;
import com.legalfriend.repository.UserTypeRepository;
import com.legalfriend.response.LegalFriendResponse;
import com.legalfriend.service.UserService;
import com.legalfriend.util.EmailServiceImpl;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/users")
public class GeneralController {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ReferralRepository referralRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private InvoiceNumberRepository invoiceNumberRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserPasswordLoginRepository passwordLoginRepository;

	@Autowired
	private UserRegistrationRepository registrationRepository;

	@Autowired
	private UserSubscriptionRepository userSubscriptionRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private UserTypeRepository userTypeRepo;

	@Autowired
	private EmailServiceImpl emailService;

	// private String FETCH_CLIENT_USERS = "select client.id, client.first_name,
	// client.last_name from user u inner join user client on u.client_id =
	// client.id where u.email=:email and (u.is_client is null or u.is_client =
	// false)";

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralController.class);

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public LegalFriendResponse signup(HttpServletRequest request, @RequestBody User user)
			throws MessagingException, IOException, TemplateException {
		List<User> persistedUser = userRepository.findByEmail(user.getEmail().toLowerCase());
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		if (persistedUser.size() > 0) {
			friendResponse.setFailureReason("User already exists");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return friendResponse;
		}
		if (user.getOrganization() == null) {
			friendResponse.setFailureReason("Organization is compulsory");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return friendResponse;
		}
		Status status = statusRepository.findByStatusId(Long.valueOf(user.getStatus().getStatusId()));
		user.setStatus(status);
		String pwd = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findByRoleName(UserRole.ADMIN.name());
		roles.add(role);
		user.setRoles(roles);
		user.setEmail(user.getEmail().toLowerCase());
		User u = userRepository.save(user);
		UserRegisterToken registerToken = new UserRegisterToken();
		registerToken.setUserId(u.getId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 24);
		registerToken.setExpiryDateTime(cal.getTime());
		String token = UUID.randomUUID().toString();
		registerToken.setToken(token);
		registrationRepository.save(registerToken);
		emailService.sendEmail(token, u.getEmail());
		createUserSubscription(user);
		friendResponse.setSuccessMessage("User has been created successfully");
		friendResponse.setId(u.getId());
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	@RequestMapping(value = "/referral", method = RequestMethod.POST)
	public User referralSignup(@RequestBody com.legalfriend.entities.User user) {
		Status status = statusRepository.findByStatusId(Long.valueOf(user.getStatus().getStatusId()));
		user.setStatus(status);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		List<Role> roles = new ArrayList<>();
		for (Role role : user.getRoles()) {
			Role persistRole = roleRepository.findById(role.getId());
			roles.add(persistRole);
		}
		user.setRoles(roles);
		user.setEmail(user.getEmail().toLowerCase());
		User u = userRepository.save(user);
		UserRegisterToken registerToken = new UserRegisterToken();
		registerToken.setUserId(u.getId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 24);
		registerToken.setExpiryDateTime(cal.getTime());
		String token = UUID.randomUUID().toString();
		registerToken.setToken(token);
		registrationRepository.save(registerToken);
		emailService.sendReferralEmail(u.getEmail(), token);
		u.setPassword(null);
		return u;
	}

	@RequestMapping(value = "/getclientdetails", method = RequestMethod.GET)
	public List<User> getClientDetails(@RequestParam String username) {
		List<User> users = userRepository.findByEmail(username.toLowerCase());
		return users;
	}

	@RequestMapping(value = "/subscription", method = RequestMethod.GET)
	public List<SubscriptionMaster> getUsersSubscription() {
		List<SubscriptionMaster> subscriptionMasters = new ArrayList<>();
		subscriptionRepo.findAll().forEach(subscriptionMasters::add);
		return subscriptionMasters;
	}

	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public List<UserType> getUserType() {
		List<UserType> userTypes = new ArrayList<>();
		userTypeRepo.findAll().forEach(userTypes::add);
		return userTypes;
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<UserClient> getUserClient(@RequestParam String email) {
		List<User> u = userRepository.findByEmail(email);
		List<UserClient> clients = new ArrayList<>();
		if (u.size() > 0) {
			List<User> users = userRepository.findClientsEmail(email);
			for (User user : users) {
				User c = userRepository.findById(user.getClientId()).get(0);
				UserClient client = new UserClient();
				client.setOrganization(c.getOrganization());
				client.setClientId(c.getId());
				clients.add(client);
			}
			users.clear();
			users = userRepository.findSelfEmail(email);
			if (users.size() > 0) {
				UserClient client = new UserClient();
				client.setOrganization(u.get(0).getOrganization());
				clients.add(client);
			}
		}
		return clients;
	}

	@GetMapping("/forgotpwd")
	public LegalFriendResponse forgotPassword(@RequestParam String email) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		List<User> users = userRepository.findByEmail(email.toLowerCase());
		if (users.size() == 0) {
			friendResponse.setFailureReason("No user found with this email");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return friendResponse;
		}
		User u = users.get(0);
		UserPasswordToken passwordToken = new UserPasswordToken();
		passwordToken.setUserId(u.getId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 24);
		passwordToken.setExpiryDateTime(cal.getTime());
		String token = UUID.randomUUID().toString();
		passwordToken.setToken(token);
		passwordLoginRepository.save(passwordToken);
		emailService.sendForgotPasswordEmail(email, token, "Reset password");
		friendResponse.setSuccessMessage("Email has been sent to reset the password");
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	@PostMapping("/updatePassword")
	public LegalFriendResponse updatePassword(@RequestBody UserPassword user) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Session session = sessionFactory.openSession();
		List<User> users = userRepository.findByEmail(user.getEmail().toLowerCase());
		if (users.size() == 0) {
			friendResponse.setFailureReason("No user found with this email");
			friendResponse.setHttpCode(HttpStatus.FORBIDDEN.value());
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User u = users.get(0);
		List<UserPasswordToken> passwordTokens = passwordLoginRepository.findByUserId(u.getId());
		if (passwordTokens.size() > 0 && passwordTokens.get(0).getToken().equals(user.getToken())
				&& new Timestamp(passwordTokens.get(0).getExpiryDateTime().getTime()).after(timestamp)) {
			session = sessionFactory.openSession();
			Query query = session.createQuery("update user set password ='"
					+ bCryptPasswordEncoder.encode(user.getPassword()) + "' WHERE email = " + u.getEmail());
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
			passwordLoginRepository.delete(passwordTokens.get(0).getId());
			friendResponse.setSuccessMessage("Password updated successfully");
			friendResponse.setHttpCode(HttpStatus.OK.value());
			session.close();
		} else {
			friendResponse.setFailureReason("Invalid user token");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;

	}

	@PostMapping("/verifyEmail")
	public LegalFriendResponse verifyEmail(@RequestParam String token, @RequestParam String isReferral) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Session session = sessionFactory.openSession();
		UserRegisterToken registerToken = registrationRepository.findByToken(token);
		if (registerToken == null) {
			friendResponse.setFailureReason("Invalid token");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (new Timestamp(registerToken.getExpiryDateTime().getTime()).after(timestamp)) {
			User user = userRepository.findOne(registerToken.getUserId());
			user.setVerified(true);
			session = sessionFactory.openSession();
			Query query = session.createQuery("update user set verified =1 WHERE id = " + user.getId());
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
			query = session.createQuery(
					"update user_register_token set expiry_date_time = NOW() WHERE fk_user_id = " + user.getId());
			transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
			friendResponse.setSuccessMessage("Email has been verified successfully");
			friendResponse.setHttpCode(HttpStatus.OK.value());
			session.close();
			if (isReferral.equals("Y")) {
				Referral referral = referralRepository.findByEmailAndReferrerId(user.getEmail(), user.getClientId());
				referral.setSignUpDate(new Date());
				referral.setUpdatedDate(new Date());
				referralRepository.save(referral);
				List<User> referredUser = userRepository.findById(referral.getReferrerId());
				emailService.sendReferralSignupEmail(referredUser.get(0).getEmail(), referral.getName());
			}
		} else {
			friendResponse.setFailureReason("Token has been expired");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping("/verifyUser")
	public LegalFriendResponse verifyUser(@RequestParam String token, @RequestParam String isReferral,
			@RequestParam String password) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Session session = sessionFactory.openSession();
		UserRegisterToken registerToken = registrationRepository.findByToken(token);
		if (registerToken == null) {
			friendResponse.setFailureReason("Invalid token");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (new Timestamp(registerToken.getExpiryDateTime().getTime()).after(timestamp)) {
			User user = userRepository.findOne(registerToken.getUserId());
			user.setVerified(true);
			session = sessionFactory.openSession();
			Query query = session.createQuery("update user set verified =1 WHERE id = " + user.getId());
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
			query = session.createQuery(
					"update user_register_token set expiry_date_time = NOW() WHERE fk_user_id = " + user.getId());
			transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
			friendResponse.setSuccessMessage("Email has been verified successfully");
			friendResponse.setHttpCode(HttpStatus.OK.value());
			session.close();
			String pwd = bCryptPasswordEncoder.encode(password);
			user.setPassword(pwd);
			userRepository.save(user);
			if (isReferral.equals("Y")) {
				Referral referral = referralRepository.findByEmailAndReferrerId(user.getEmail(), user.getClientId());
				referral.setSignUpDate(new Date());
				referral.setUpdatedDate(new Date());
				referralRepository.save(referral);
				List<User> referredUser = userRepository.findById(referral.getReferrerId());
				emailService.sendReferralSignupEmail(referredUser.get(0).getEmail(), referral.getName());
			}
		} else {
			friendResponse.setFailureReason("Token has been expired");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	private void createUserSubscription(User user) {
		SubscriptionMaster subscriptionMaster = subscriptionRepo.findById(user.getSubscriptionId());
		UserSubscription userSubscription = new UserSubscription();
		userSubscription.setUser(user);
		userSubscription.setSubscriptionMaster(subscriptionMaster);
		userSubscription.setFromDate(new Date());
		userSubscription.setUpdatedDate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, subscriptionMaster.getDays());
		Date thruDate = cal.getTime();
		userSubscription.setThruDate(thruDate);
		userSubscriptionRepo.save(userSubscription);
	}

	@GetMapping("/trialorg")
	public List<String> getTrialExpiringOrganization() {
		return userRepository.findTrialExpiryOrganization();
	}

	@GetMapping("/org/totalusers")
	public List<Organization> getOrganizationUsers() {
		return userService.findOrganizationUsersCount();
	}
	/*
	 * *
	 * 
	 * @GetMapping("/org/") public List<User> getOrgUsers(@RequestParam Long
	 * userId) { return userService.findOrganizationUsers(userId); }
	 * 
	 * @GetMapping("/org/inactive") public List<Organization> getInactiveOrg() {
	 * return userService.findInactiveOrganization(); }
	 */
}
