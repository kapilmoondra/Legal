package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.legalfriend.entities.User;
import com.legalfriend.util.CustomQuery;

/**
 * Created by deepak.j
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByEmail(String email);

	@Query(value = "SELECT * FROM user WHERE fk_userlogin = ?1", nativeQuery = true)
	User findByLogin(Long fkUserLogin);

	List<User> findByClientId(Long clientId);

	List<User> findById(Long id);

	@Modifying
	@Transactional
	@Query(value = "update user set password = ?1 WHERE id = ?2", nativeQuery = true)
	void updatePassword(String password, Long fkUserLogin);

	@Query(value = "SELECT * FROM user WHERE email = ?1 and client_id = ?2", nativeQuery = true)
	List<User> findByEmailAndClientId(String email, Long clientId);

	@Query(value = "SELECT * FROM user WHERE email = ?1 and client_id is not null", nativeQuery = true)
	List<User> findClientsEmail(String email);

	@Query(value = "SELECT * FROM user WHERE email = ?1 and client_id is null", nativeQuery = true)
	List<User> findSelfEmail(String email);

	@Query(value = CustomQuery.USER_CREATED_BY_MONTH, nativeQuery = true)
	List<Object[]> findUsersCreatedByMonth();

	@Query(value = CustomQuery.USER_CREATED_BY_DATE, nativeQuery = true)
	List<Object[]> findUsersCreatedByDate(Long userId, String role, String startDate, String endDate);

	@Query(value = CustomQuery.USER_CREATED_BY_WEEK, nativeQuery = true)
	List<Object[]> findUsersCreatedByWeek(Long userId, String role);

	@Query(value = CustomQuery.ACTIVE_USERS, nativeQuery = true)
	List<Object[]> findActiveUsers(Long userId);

	@Query(value = CustomQuery.LOGIN_COUNT_MONTH, nativeQuery = true)
	List<Object[]> findMonthLogins(Long userId, List roleList);

	@Query(value = CustomQuery.LOGIN_COUNT_WEEK, nativeQuery = true)
	List<Object[]> findWeekLogins(Long userId, List roleList);

	@Query(value = CustomQuery.LOGIN_COUNT_DATE, nativeQuery = true)
	List<Object[]> findDateLogins(Long userId, List roleList, String startDate, String endDate);

	@Query(value = CustomQuery.TOTAL_CUSTOMERS, nativeQuery = true)
	Long findTotalCustomers(Long userId, String role);

	@Query(value = "select * from user", nativeQuery = true)
	List<User> findAllUsers();

	@Query(value = CustomQuery.TRIAL_USERS, nativeQuery = true)
	List<User> findTrialUsers();

	@Query(value = CustomQuery.TRIAL_USERS_COUNT, nativeQuery = true)
	Long findTrialUsersCount();

	@Query(value = CustomQuery.PREMIUM_USERS_COUNT, nativeQuery = true)
	Long findPremiumUsersCount();

	@Query(value = CustomQuery.USERS_AMONTH_INACTIVE_COUNT, nativeQuery = true)
	Long findOneMonthInactiveUsers();

	@Query(value = CustomQuery.ORGANIZATION_COUNT, nativeQuery = true)
	Long findOrganizationCount();

	@Query(value = CustomQuery.USERS_AMONTH_INACTIVE, nativeQuery = true)
	List<User> findMonthInactiveUsers();

	@Query(value = CustomQuery.TRIAL_EXPIRY_ORGANIZATION, nativeQuery = true)
	List<String> findTrialExpiryOrganization();

	@Query(value = CustomQuery.ORG_USERS_COUNT, nativeQuery = true)
	List<Object[]> findOrganizationUsersCount();

	@Query(value = CustomQuery.CUSTOMERS_DETAIL, nativeQuery = true)
	List<User> findAllCustomers(Long userId, String role);

	@Query(value = CustomQuery.ORG_USERS_DETAILS, nativeQuery = true)
	List<User> findOrganizationUsers(Long userId);

	@Query(value = CustomQuery.ORG_WITH_INACTIVE_USERS, nativeQuery = true)
	List<String> findInactiveOrganization();

	@Query(value = CustomQuery.MONTH_SUBSCRIBE_USERS_COUNT, nativeQuery = true)
	List<Object[]> findMonthUsers(Long userId, String subscriptionType, List roleList);

	@Query(value = CustomQuery.WEEK_SUBSCRIBE_USERS_COUNT, nativeQuery = true)
	List<Object[]> findWeekUsers(Long userId, String subscriptionType, List roleList);

	@Query(value = CustomQuery.DATE_SUBSCRIBE_USERS_COUNT, nativeQuery = true)
	List<Object[]> findDateUsers(Long userId, String subscriptionType, List roleList, String start, String end);

	@Query(value = CustomQuery.ALL_USERS_MONTH, nativeQuery = true)
	public List<Object[]> findAllUsersCountMonth(Long userId, List roleList);

	@Query(value = CustomQuery.ALL_USERS_WEEK, nativeQuery = true)
	public List<Object[]> findAllUsersCountWeek(Long userId, List roleList);

	@Query(value = CustomQuery.ALL_USERS_DATE, nativeQuery = true)
	public List<Object[]> findAllUsersCountDate(Long userId, List roleList, String startDate, String endDate);

	@Query(value = CustomQuery.ORGANIZATION, nativeQuery = true)
	List<User> findOrganizations();

	@Query(value = CustomQuery.ALL_TRIAL_USERS_YEAR, nativeQuery = true)
	List<Object[]> findTrialUsers(String year);

	@Query(value = CustomQuery.ALL_TRIAL_USERS_DATE, nativeQuery = true)
	List<Object[]> findTrialUsers(String startDate, String endDate);

	@Query(value = CustomQuery.ALL_PREMIUM_USERS_YEAR, nativeQuery = true)
	List<Object[]> findPremiumUsers(String year);

	@Query(value = CustomQuery.ALL_PREMIUM_USERS_DATE, nativeQuery = true)
	List<Object[]> findPremiumUsers(String startDate, String endDate);

	@Query(value = CustomQuery.ADMIN_PREMIUM_COUNT, nativeQuery = true)
	Long findPremiumAdminCount();

	@Query(value = CustomQuery.PREMIUM_USER_BYROLE_COUNT, nativeQuery = true)
	List<Object[]> findPremiumUserCountByRole();

	List<User> findByEmailContaining(String email);

	@Query(value = "Select * from user where client_id is null", nativeQuery = true)
	List<User> findAllAdmin();

	List<User> findByIdIn(List<Long> userIds);
}