package com.legalfriend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.ActiveUserDashboard;
import com.legalfriend.entities.Branch;
import com.legalfriend.entities.ComplianceStatus;
import com.legalfriend.entities.CustomerType;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Employee;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.Manager;
import com.legalfriend.entities.Role;
import com.legalfriend.entities.Status;
import com.legalfriend.entities.User;
import com.legalfriend.entities.UserAddress;
import com.legalfriend.entities.UserPassword;
import com.legalfriend.entities.UserRelationship;
import com.legalfriend.entities.UserSubscription;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.ComplianceStatusRepository;
import com.legalfriend.repository.InstitutionRepository;
import com.legalfriend.repository.RoleRepository;
import com.legalfriend.repository.StatusRepository;
import com.legalfriend.repository.UserRelationshipRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserSubscriptionRepository;
import com.legalfriend.response.LegalFriendResponse;
import com.legalfriend.service.UserService;
import com.legalfriend.util.EmailService;

@RestController
@RequestMapping("/usermanagement")
public class UserMangementController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private UserRelationshipRepository userRelationshipRepo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserSubscriptionRepository userSubscriptionRepo;

	@Autowired
	private ComplianceStatusRepository complStatusRepo;

	@Autowired
	EmailService emailService;

	private String FETCH_USER_ROLE = " select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = :userId";

	@GetMapping("/listusers")
	public List<User> listUser(@RequestParam Long clientId) {
		return userRepository.findByClientId(clientId);
	}

	@GetMapping("/listroles")
	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

	@GetMapping("/liststatus")
	public List<Status> listStatus() {
		return statusRepository.findAll();
	}

	@GetMapping("/compliance/status")
	public List<ComplianceStatus> listComplianceStatus() {
		return complStatusRepo.findAll();
	}

	private static long daysBetween(Date one, Date two) {
		long difference = (one.getTime() - two.getTime()) / 86400000;
		return Math.abs(difference);
	}

	@GetMapping("/user")
	public User getUser(@RequestParam String userId) {
		List<User> users = userRepository.findById(Long.valueOf(userId));
		if (users.size() > 0) {
			User user = users.get(0);
			List<String> userRoles = findUserRole(Long.valueOf(userId));
			if (!userRoles.contains(UserRole.ADMIN.name())) {
				Long clientId = user.getClientId();
				CustomerType c = userRepository.findById(clientId).get(0).getCustomerType();
				user.setCustomerType(c);
			}
			if (!userRoles.contains(UserRole.CLIENT.name())) {
				user.setUserType(null);
			}
			UserSubscription userSubscription = userSubscriptionRepo.findByUser(user);
			if (userSubscription != null) {
				user.setSubscriptionEndDate(userSubscription.getThruDate());
				long daysDiff = daysBetween(user.getSubscriptionEndDate(), new Date());
				user.setShowSubscriptionFlash(daysDiff <= 3 ? Boolean.TRUE : Boolean.FALSE);
				user.setSubscriptionEnded(daysDiff <= -1 ? Boolean.TRUE : Boolean.FALSE);
				user.setDaysLeftForRenew(daysDiff);
			}
			user.setPassword("");
			return user;
		}
		return null;
	}

	@PostMapping("/adduser")
	public LegalFriendResponse addUserDetails(@RequestBody User user) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		if (user.getClientId() == null) {
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			friendResponse.setFailureReason("No client Id found");
			return friendResponse;
		}
		List<User> persistedUser = userRepository.findByEmailAndClientId(user.getEmail().toLowerCase(),
				Long.valueOf(user.getClientId()));
		if (persistedUser.size() > 0) {
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			friendResponse.setFailureReason("User already exists");
			return friendResponse;
		}
		Status status = statusRepository.findByStatusId(Long.valueOf(user.getStatus().getStatusId()));
		user.setStatus(status);
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findById(user.getRoles().get(0).getId());
		roles.add(role);
		user.setRoles(roles);
		user.setEmail(user.getEmail().toLowerCase());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setVerified(true);
		if (user.getBranch() != null) {
			Branch branch = branchRepository.findById(user.getBranch().getId());
			user.setBranch(branch);
		}
		if (user.getInstitution() != null) {
			Institution institution = institutionRepo.findById(user.getInstitution().getId());
			user.setInstitution(institution);
		}
		User u = userRepository.save(user);
		// UserRegisterToken registerToken = new UserRegisterToken();
		// registerToken.setUserId(u.getId());
		/*
		 * Calendar cal = Calendar.getInstance(); cal.setTime(new Date());
		 * cal.add(Calendar.HOUR_OF_DAY, 24);
		 * registerToken.setExpiryDateTime(cal.getTime()); String token =
		 * UUID.randomUUID().toString(); registerToken.setToken(token);
		 * registrationRepository.save(registerToken);
		 */
		List<String> userRoles = findUserRole(Long.valueOf(user.getClientId()));
		String fromRole = UserRole.ADMIN.name();
		createUserRelationship(Long.valueOf(user.getClientId()), u.getId(), fromRole, role.getRoleName());
		// emailService.sendUserVerificationEmail(token, u.getEmail());
		friendResponse.setId(u.getId());
		friendResponse.setSuccessMessage("User created successfully");
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	private List<String> findUserRole(Long userId) {
		Session session = sessionFactory.openSession();
		List<String> list = new ArrayList<>();
		Query query = session.createSQLQuery(FETCH_USER_ROLE);
		query.setLong("userId", userId);
		List<String> rows = (List<String>) query.list();
		for (String role : rows) {
			list.add(role);
		}
		session.close();
		return list;
	}

	private void createUserRelationship(Long fromUserId, Long toUserId, String fromRoleId, String toRoleId) {
		UserRelationship userRelationship = new UserRelationship();
		userRelationship.setFromUserId(fromUserId);
		userRelationship.setToUserId(toUserId);
		userRelationship.setFromRoleId(fromRoleId);
		userRelationship.setToRoleId(toRoleId);
		userRelationship.setFromDate(new Date());
		userRelationshipRepo.save(userRelationship);
	}

	@PostMapping("/edituser")
	public LegalFriendResponse editUserDetails(@RequestBody User user) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		List<User> users = userRepository.findById(user.getId());
		if (!users.isEmpty()) {
			User u = users.get(0);
			String pwd = u.getPassword();
			if (user.getEmail() != null && !u.getEmail().toLowerCase().equals(user.getEmail().toLowerCase())) {
				List<User> existingUser = userRepository.findByEmailAndClientId(user.getEmail().toLowerCase(),
						u.getClientId());
				if (!existingUser.isEmpty()) {
					friendResponse.setFailureReason("Email already exists");
					friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return friendResponse;
				}
			}
			BeanUtils.copyProperties(user, u);
			u.setPassword(pwd);
			if (user.getBranch() != null) {
				Branch branch = branchRepository.findById(user.getBranch().getId());
				u.setBranch(branch);
			}
			if (user.getInstitution() != null) {
				Institution institution = institutionRepo.findById(user.getInstitution().getId());
				u.setInstitution(institution);
			}
			u.setVerified(true);
			userRepository.save(u);
			friendResponse.setSuccessMessage("User updated successfully");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("No user with this userId exists");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping("/updatePassword")
	public LegalFriendResponse updatePassword(@RequestBody UserPassword user) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Session session = sessionFactory.openSession();
		List<User> users = userRepository.findById(Long.valueOf(user.getUserId()));
		if (users.size() == 0) {
			friendResponse.setFailureReason("No user found with this clientId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		User u = users.get(0);
		if (bCryptPasswordEncoder.matches(user.getOldPassword(), u.getPassword())) {
			friendResponse.setFailureReason("Current password is not correct");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return friendResponse;
		}
		session = sessionFactory.openSession();
		Query query = session.createQuery("update user set password ='"
				+ bCryptPasswordEncoder.encode(user.getPassword()) + "' WHERE id = " + u.getId());
		Transaction transaction = session.beginTransaction();
		query.executeUpdate();
		transaction.commit();
		session.close();
		friendResponse.setSuccessMessage("Password updated successfully");
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	@PostMapping("/changePassword")
	public LegalFriendResponse changePassword(@RequestBody UserPassword user) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Session session = sessionFactory.openSession();
		List<User> users = userRepository.findById(Long.valueOf(user.getUserId()));
		if (users.size() == 0) {
			friendResponse.setFailureReason("No user found with this clientId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		User u = users.get(0);
		session = sessionFactory.openSession();
		Query query = session.createQuery("update user set password ='"
				+ bCryptPasswordEncoder.encode(user.getPassword()) + "' WHERE id = " + u.getId());
		Transaction transaction = session.beginTransaction();
		query.executeUpdate();
		transaction.commit();
		session.close();
		friendResponse.setSuccessMessage("Password updated successfully");
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/address")
	public UserAddress getUserAddress(@RequestParam Long userId) {
		List<User> u = userRepository.findById(userId);
		return u.get(0).getAddress();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/managers")
	public List<Manager> getManagers(@RequestParam Long userId) {
		Role role = roleRepository.findByRoleName(UserRole.MANAGER.name());
		List<Manager> managers = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(
				"select u.id, u.first_name, u.last_name from user_role ur left join user u on u.id=ur.user_id WHERE ur.role_id = :roleId and client_id = :clientId");
		query.setLong("roleId", role.getId());
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CLIENT.name())
				|| roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		query.setLong("clientId", userId);
		List<Object[]> rows = (List<Object[]>) query.list();
		for (Object[] row : rows) {
			Manager manager = new Manager();
			manager.setId(Long.parseLong(row[0].toString()));
			manager.setName(row[1].toString() + " " + row[2].toString());
			managers.add(manager);
		}
		session.close();
		return managers;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/employees")
	public List<Employee> getEmployees(@RequestParam Long userId) {
		Role role = roleRepository.findByRoleName(UserRole.EMPLOYEE.name());
		Session session = sessionFactory.openSession();
		List<Employee> employees = new ArrayList<>();
		Query query = session.createSQLQuery(
				"select u.id, u.first_name, u.last_name from user_role ur left join user u on u.id=ur.user_id WHERE ur.role_id = :roleId and client_id = :clientId");
		query.setLong("roleId", role.getId());
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CLIENT.name())
				|| roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		query.setLong("clientId", userId);
		List<Object[]> rows = (List<Object[]>) query.list();
		for (Object[] row : rows) {
			Employee employee = new Employee();
			employee.setId(Long.parseLong(row[0].toString()));
			employee.setName(row[1].toString() + " " + row[2].toString());
			employees.add(employee);
		}
		session.close();
		return employees;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/customers")
	public List<Employee> getCustomers(@RequestParam Long userId, @RequestParam Boolean isInstitutional) {
		Role role = roleRepository.findByRoleName(UserRole.CLIENT.name());
		Session session = sessionFactory.openSession();
		List<Employee> employees = new ArrayList<>();
		String sql = "select u.id, u.first_name, u.last_name from user_role ur left join user u on u.id=ur.user_id WHERE ur.role_id = :roleId and client_id = :clientId and  u.fk_institution_id is null";
		if (isInstitutional) {
			sql = "select u.id, u.first_name, u.last_name from user_role ur left join user u on u.id=ur.user_id WHERE ur.role_id = :roleId and client_id = :clientId and u.fk_institution_id is not null";
		}
		Query query = session.createSQLQuery(sql);
		query.setLong("roleId", role.getId());
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CLIENT.name())
				|| roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		query.setLong("clientId", userId);
		List<Object[]> rows = (List<Object[]>) query.list();
		for (Object[] row : rows) {
			Employee employee = new Employee();
			employee.setId(Long.parseLong(row[0].toString()));
			employee.setName(row[1].toString() + " " + row[2].toString());
			employees.add(employee);
		}
		session.close();
		return employees;
	}

	@GetMapping("/customers/month")
	public List<DashboardReport> getUserCreatedByMonth(Long userId) {
		return userService.findClientCreatedByMonth(userId);
	}

	@GetMapping("/customers/date")
	public List<DashboardReport> getUserCreatedByDate(@RequestParam Long userId, @RequestParam String startDate,
			@RequestParam String endDate) {
		return userService.findClientCreatedByDate(userId, startDate, endDate);
	}

	@GetMapping("/customers/week")
	public List<DashboardReport> getUserCreatedByWeek(Long userId) {
		return userService.findClientCreatedByWeek(userId);
	}

	@GetMapping("/customers/active")
	public List<ActiveUserDashboard> getActiveUsers(@RequestParam Long userId) {
		return userService.findActiveUsers(userId);
	}

	@GetMapping("/month/online")
	public List<DashboardReport> getMonthLogins(@RequestParam Long userId) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.EMPLOYEE.toString());
		roleList.add(UserRole.ADMIN.toString());
		roleList.add(UserRole.MANAGER.toString());
		return userService.findMonthLogins(userId, roleList);
	}

	@GetMapping("/customers/month/online")
	public List<DashboardReport> getCustomerMonthLogins(@RequestParam Long userId) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findMonthLogins(userId, roleList);
	}

	@GetMapping("/week/online")
	public List<DashboardReport> getWeekLogins(@RequestParam Long userId) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.EMPLOYEE.toString());
		roleList.add(UserRole.ADMIN.toString());
		roleList.add(UserRole.MANAGER.toString());
		return userService.findWeekLogins(userId, roleList);
	}

	@GetMapping("/customers/week/online")
	public List<DashboardReport> getCustomerWeekLogins(@RequestParam Long userId) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findWeekLogins(userId, roleList);
	}

	@GetMapping("/date/online")
	public List<DashboardReport> getDateLogins(@RequestParam Long userId, @RequestParam String startDate,
			@RequestParam String endDate) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.EMPLOYEE.toString());
		roleList.add(UserRole.ADMIN.toString());
		roleList.add(UserRole.MANAGER.toString());
		return userService.findDateLogins(userId, roleList, startDate, endDate);
	}

	@GetMapping("/customers/date/online")
	public List<DashboardReport> getCustomerDateLogins(@RequestParam Long userId, @RequestParam String startDate,
			@RequestParam String endDate) {
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findDateLogins(userId, roleList, startDate, endDate);
	}

	@GetMapping("/users")
	List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping("/trialusers")
	List<User> getTrialUsers() {
		return userService.findTrialUsers();
	}

	/*
	 * @GetMapping("/trial/customers/month") List<DashboardReport>
	 * getTrialCustomersByMonth(@RequestParam Long userId) { List<String>
	 * roleList = new ArrayList<>(); roleList.add(UserRole.CLIENT.toString());
	 * return userService.findMonthUsers(userId,
	 * Subscription.TRIAL.toString(),roleList); }
	 */

	/*
	 * @GetMapping("/trial/customers/week") List<DashboardReport>
	 * getTrialCustomersByWeek(@RequestParam Long userId) { List<String>
	 * roleList = new ArrayList<>(); roleList.add(UserRole.CLIENT.toString());
	 * return userService.findWeekUsers(userId,Subscription.TRIAL.toString(),
	 * roleList); }
	 * 
	 * @GetMapping("/trial/customers/date") List<DashboardReport>
	 * getTrialCustomersByDate(@RequestParam Long userId,
	 * 
	 * @RequestParam String startDate,@RequestParam String endDate) {
	 * List<String> roleList = new ArrayList<>();
	 * roleList.add(UserRole.CLIENT.toString()); return
	 * userService.findDateUsers(userId,Subscription.TRIAL.toString(), roleList,
	 * startDate, endDate); }
	 * 
	 * @GetMapping("/paid/customers/month") List<DashboardReport>
	 * getPremiumCustomersByMonth(@RequestParam Long userId) { List<String>
	 * roleList = new ArrayList<>(); roleList.add(UserRole.CLIENT.toString());
	 * return userService.findMonthUsers(userId,
	 * Subscription.PREMIUM.toString(),roleList); }
	 * 
	 * @GetMapping("/paid/customers/week") List<DashboardReport>
	 * getPremiumCustomersByWeek(@RequestParam Long userId) { List<String>
	 * roleList = new ArrayList<>(); roleList.add(UserRole.CLIENT.toString());
	 * return userService.findWeekUsers(userId,Subscription.PREMIUM.toString(),
	 * roleList); }
	 */

	/*
	 * @GetMapping("/paid/customers/date") List<DashboardReport>
	 * getPremiumCustomersByDate(@RequestParam Long userId,
	 * 
	 * @RequestParam String startDate,@RequestParam String endDate) {
	 * List<String> roleList = new ArrayList<>();
	 * roleList.add(UserRole.CLIENT.toString()); return
	 * userService.findDateUsers(userId,Subscription.PREMIUM.toString(),
	 * roleList, startDate, endDate); }
	 */

	@GetMapping("/inactiveusers")
	List<User> getInactiveUsers() {
		return userService.findMonthInactiveUsers();
	}

	@GetMapping("/allcustomers")
	List<User> getAllCustomers(@RequestParam Long userId) {
		return userService.findAllCustomers(userId);
	}
	
	@GetMapping("/allcustomers/month")
	List<DashboardReport> getAllCustomersMonth(@RequestParam Long userId){
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findAllUsersCountMonth(userId, roleList);
	}
	
	@GetMapping("/allcustomers/week")
	List<DashboardReport> getAllCustomersWeek(@RequestParam Long userId){
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findAllUsersCountWeek(userId, roleList);
	}
	
	@GetMapping("/allcustomers/date")
	List<DashboardReport> getAllCustomersWeek(@RequestParam Long userId,
			@RequestParam String startDate,@RequestParam String endDate){
		List<String> roleList = new ArrayList<>();
		roleList.add(UserRole.CLIENT.toString());
		return userService.findAllUsersCountDate(userId, roleList,startDate,endDate);
	}
}
