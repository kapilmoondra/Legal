package com.legalfriend.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.ActiveUserDashboard;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Organization;
import com.legalfriend.entities.User;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.UserService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepo;
	
	public List<DashboardReport> findClientCreatedByMonth(Long userId) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects = userRepository.findUsersCreatedByMonth();
		List<DashboardReport> casesByMonth = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport caseDash = new DashboardReport();
			caseDash.setX(object[0]!=null?(String)object[0]:null);
			caseDash.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			casesByMonth.add(caseDash);
		}
		return casesByMonth;
	}
	
	public List<DashboardReport> findClientCreatedByDate(Long userId, String startDate, String endDate) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<DashboardReport> casesByDate = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(startDate)&&StringUtils.isNotEmpty(endDate))
		{
			List<Object[]> objects = userRepository.findUsersCreatedByDate(userId,UserRole.CLIENT.toString(), startDate, endDate);
			
			for(Object[] object : objects) {
				DashboardReport caseDash = new DashboardReport();
				caseDash.setX(object[0]!=null?(String)object[0]:null);
				caseDash.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
				casesByDate.add(caseDash);
			}
		}
		return casesByDate;
	}
	
	public List<DashboardReport> findClientCreatedByWeek(Long userId) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = userRepository.findUsersCreatedByWeek(userId, UserRole.CLIENT.toString());
		List<DashboardReport> casesByWeek = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport caseDash = new DashboardReport();
			caseDash.setX(object[0]!=null?object[0].toString():null);
			caseDash.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			casesByWeek.add(caseDash);
		}
		return casesByWeek;
	}
	
	public List<ActiveUserDashboard> findActiveUsers(Long userId){
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = userRepository.findActiveUsers(userId);
		List<ActiveUserDashboard> activeUsers = new ArrayList<>();
		for(Object[] object : objects) {
			ActiveUserDashboard user = new ActiveUserDashboard();
			user.setName(object[0]!=null?object[0].toString():null);
			user.setDesignation(object[1]!=null?object[1].toString():null);
			user.setTotalLogin(object[2]!=null?(BigInteger)object[2]:null);
			user.setLastLogin(object[3]!=null?object[3].toString():null);
			activeUsers.add(user);
		}
		return activeUsers;
	}

	@Override
	public List<DashboardReport> findDateLogins(Long userId,List roleList, String startDate, String endDate) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<DashboardReport> loginsByDate = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(startDate)&&StringUtils.isNotEmpty(endDate))
		{
			List<Object[]> objects = userRepository.findDateLogins(userId,roleList, startDate, endDate);
			
			for(Object[] object : objects) {
				DashboardReport dateLogin = new DashboardReport();
				dateLogin.setX(object[0]!=null?(String)object[0]:null);
				dateLogin.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
				loginsByDate.add(dateLogin);
			}
		}
		return loginsByDate;
	}

	@Override
	public List<DashboardReport> findMonthLogins(Long userId, List roleList) {
	
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = userRepository.findMonthLogins(userId, roleList);
		List<DashboardReport> loginsByMonth = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport dateLogin = new DashboardReport();
			dateLogin.setX(object[0]!=null?(String)object[0]:null);
			dateLogin.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			loginsByMonth.add(dateLogin);
		}
		return loginsByMonth;
	}

	@Override
	public List<DashboardReport> findWeekLogins(Long userId, List roleList) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = userRepository.findWeekLogins(userId, roleList);
		List<DashboardReport> loginsByMonth = new ArrayList<>();
		int value;
		for(Object[] object : objects) {
			DashboardReport dateLogin = new DashboardReport();
			
			if(object[0].toString()!=null) {
				value = Integer.parseInt(object[0].toString());
			
				if(value<8) {
					dateLogin.setX("1-7");
				}
				else if(value<15) {
					dateLogin.setX("8-14");
				}
				else if(value<22) {
					dateLogin.setX("15-21");		
				}
				else if(value<29) {
					dateLogin.setX("22-28");
				}
				else if(value<36) {
					dateLogin.setX("29-35");
				}else if(value<43) {
					dateLogin.setX("36-42");
				}else if(value<50) {
					dateLogin.setX("43-49");
				}else{
					dateLogin.setX("50-52");
				}
			}	
		
			dateLogin.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			loginsByMonth.add(dateLogin);
		}
		return loginsByMonth;
	}
	
	@Override
	public List<Organization> findOrganizationUsersCount(){
		List<Object[]> objects = userRepository.findOrganizationUsersCount();
		List<Organization> orgUsers = new ArrayList<>();
		
		for(Object[] object: objects) {
			Organization ob = new Organization();
			ob.setOrganization(object[0]!=null?object[0].toString():null);
			ob.setTotalUsers(object[1]!=null?Long.parseLong(object[1].toString()):null);
			orgUsers.add(ob);
		}
		
		return orgUsers;
	}
	
	public List<User> findOrganizationUsers(Long userId){
		return userRepository.findOrganizationUsers(userId);
	}
	
	/*public List<Organization> findInactiveOrganization(){
		List<String> data =  userRepository.findInactiveOrganization();
		List<Organization> inactiveOrganizations = new ArrayList<>();
		Organization org = new Organization();
		data.forEach(result -> {
			org.setOrganization(result);
			inactiveOrganizations.add(org);
		});
		
		return inactiveOrganizations;
	}*/

	@Override
	public Long findTotalCustomers(Long userId) {
		return userRepository.findTotalCustomers(userId, UserRole.CLIENT.toString());
	}
	
	public Long findTotalUsers() {
		return userRepository.findPremiumUsersCount();
	}
	
	@Override
	public List<User> findAll() {
		List<User> users =  (List)userRepository.findAll();
		return users;
	}
	
	public List<User> findTrialUsers(){
		return userRepository.findTrialUsers();
	}
	
	public Long findTrialUsersCount() {
		return userRepository.findTrialUsersCount();
	}
	
	public Long findOneMonthInactiveUsers() {
		return userRepository.findOneMonthInactiveUsers();
	}
	
	public Long findOrganizationCount() {
		return userRepository.findOrganizationCount();
	}
	
	public List<User> findMonthInactiveUsers() {
		return userRepository.findMonthInactiveUsers();
	}

	@Override
	public List<User> findAllCustomers(Long userId) {
	
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		return userRepository.findAllCustomers(userId, UserRole.CLIENT.toString());
	}

	@Override
	public List<DashboardReport> findAllUsersCountMonth(Long userId, List roleList) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects = userRepository.findAllUsersCountMonth(userId, roleList);
		List<DashboardReport> monthUsers = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?(String)object[0]:null);
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			monthUsers.add(data);
		}
		return monthUsers;
		
	}
	
	@Override
	public List<DashboardReport> findAllUsersCountWeek(Long userId, List roleList) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = userRepository.findAllUsersCountWeek(userId, roleList);
		List<DashboardReport> monthUsers = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			int value;				
				if(object[0].toString()!=null) {
					value = Integer.parseInt(object[0].toString());
				
					if(value<8) {
						data.setX("1-7");
					}
					else if(value<15) {
						data.setX("8-14");
					}
					else if(value<22) {
						data.setX("15-21");		
					}
					else if(value<29) {
						data.setX("22-28");
					}
					else if(value<36) {
						data.setX("29-35");
					}else if(value<43) {
						data.setX("36-42");
					}else if(value<50) {
						data.setX("43-49");
					}else{
						data.setX("50-52");
					}
				}	
			
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			monthUsers.add(data);
		}
		return monthUsers;
		
	}
	
	@Override
	public List<DashboardReport> findAllUsersCountDate(Long userId, List roleList,
			String startDate, String endDate) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects = userRepository.findAllUsersCountDate(userId, roleList,
				startDate, endDate);
		List<DashboardReport> monthUsers = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?(String)object[0]:null);
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			monthUsers.add(data);
		}
		return monthUsers;
		
	}
	
	@Override
	public List<DashboardReport> findMonthUsers(Long userId,String subscriptionType, List roleList) {
		List<Object[]> objects = userRepository.findMonthUsers(userId,subscriptionType, roleList);
		List<DashboardReport> monthTrialCustomers = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?(String)object[0]:null);
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			monthTrialCustomers.add(data);
		}
		return monthTrialCustomers;
	}
	
	@Override
	public List<DashboardReport> findDateUsers(Long userId,String subscriptionType, List roleList,
			String start, String end) {
		List<Object[]> objects = userRepository.findDateUsers(userId, subscriptionType, roleList, start, end);
		List<DashboardReport> monthTrialCustomers = new ArrayList<>();
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?(String)object[0]:null);
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			monthTrialCustomers.add(data);
		}
		return monthTrialCustomers;
	}

	@Override
	public List<DashboardReport> findWeekUsers(Long userId,String subscriptionType, List roleList) {
		List<Object[]> objects = userRepository.findWeekUsers(userId,subscriptionType, roleList);
		List<DashboardReport> weekTrialCustomers = new ArrayList<>();
		int value;
		for(Object[] object : objects) {
			DashboardReport data = new DashboardReport();
			
			if(object[0].toString()!=null) {
				value = Integer.parseInt(object[0].toString());
			
				if(value<8) {
					data.setX("1-7");
				}
				else if(value<15) {
					data.setX("8-14");
				}
				else if(value<22) {
					data.setX("15-21");		
				}
				else if(value<29) {
					data.setX("22-28");
				}
				else if(value<36) {
					data.setX("29-35");
				}else if(value<43) {
					data.setX("36-42");
				}else if(value<50) {
					data.setX("43-49");
				}else{
					data.setX("50-52");
				}
			}	
		
			data.setY(object[1]!=null?((BigInteger)object[1]).doubleValue():null);
			weekTrialCustomers.add(data);
		}
		return weekTrialCustomers;
	}
	
	public List<User> findOrganizations(){
		return userRepository.findOrganizations();
	}
	
	public List<DashboardReport> findTrialUsers(String year){
		
		List<Object[]> objects = userRepository.findTrialUsers(year);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findTrialUsers(String startDate, String endDate){
		
		List<Object[]> objects = userRepository.findTrialUsers(startDate,endDate);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findPremiumUsers(String year){
		
		List<Object[]> objects = userRepository.findPremiumUsers(year);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findPremiumUsers(String startDate, String endDate){
		
		List<Object[]> objects = userRepository.findPremiumUsers(startDate,endDate);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public Map<String, Long> findPremiumUsersByRole(){
		
		Map<String, Long> userCountByRole = new HashMap<>();
		Long adminCount = userRepository.findPremiumAdminCount();
		
		List<Object[]> objects = userRepository.findPremiumUserCountByRole();
		
		objects.forEach(data -> {
			Long value = data[1]!=null?Long.valueOf(data[1].toString()):null;
			
			if(data[0]!=null) {
				userCountByRole.put(data[0].toString(), value);
			}
			
			
		});
		
		userCountByRole.put("Admin", adminCount);
		
		return userCountByRole;		
	}
}
