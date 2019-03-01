package com.legalfriend.service;

import java.util.List;
import java.util.Map;

import com.legalfriend.entities.ActiveUserDashboard;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Organization;
import com.legalfriend.entities.User;

public interface UserService {

	 List<DashboardReport> findClientCreatedByMonth(Long userId);
	
	 List<DashboardReport> findClientCreatedByDate(Long userId,String startDate, String endDate);
	
	 List<DashboardReport> findClientCreatedByWeek(Long userId);
	 
	 List<ActiveUserDashboard> findActiveUsers(Long userId);
	 
	 List<DashboardReport> findDateLogins(Long userId,List roleList, String startDate, String endDate);
	 
	 List<DashboardReport> findMonthLogins(Long userId,List roleList);
	 
	 List<DashboardReport> findWeekLogins(Long userId, List roleList);
	 
	 Long findTotalCustomers(Long userId);
	 
	 Long findTotalUsers(); 
	 
	 List<User> findAll();
	 
	 List<User> findTrialUsers();
	 
	 List<DashboardReport> findMonthUsers(Long userId,String subscriptionType,List roleList);
	 
	 List<DashboardReport> findWeekUsers(Long userId,String subscriptionType,List roleList);
	 
	 List<DashboardReport> findDateUsers(Long userId,String subscriptionType,List roleList,
			 String startDate, String endDate);
	 
	 Long findTrialUsersCount();
	 
	 Long findOneMonthInactiveUsers();
	 
	 Long findOrganizationCount();
	 
	 List<User> findMonthInactiveUsers();
	 
	 List<Organization> findOrganizationUsersCount();
	 
	 List<User> findOrganizationUsers(Long userId);
	 
	// List<Organization> findInactiveOrganization();
	 
	 List<User> findAllCustomers(Long userId);
	 
	 List<DashboardReport> findAllUsersCountMonth(Long userId,List roleList);
	 
	 List<DashboardReport> findAllUsersCountWeek(Long userId,List roleList);
	 
	 List<DashboardReport> findAllUsersCountDate(Long userId,List roleList,
			 String startDate, String endDate);
	 
	 List<DashboardReport> findTrialUsers(String year);
	 
	 List<DashboardReport> findTrialUsers(String startDate, String endDate);
	 
	 List<DashboardReport> findPremiumUsers(String year);
	 
	 List<DashboardReport> findPremiumUsers(String startDate, String endDate);
	 
	 List<User> findOrganizations();
	 
	 Map<String, Long> findPremiumUsersByRole();
}
