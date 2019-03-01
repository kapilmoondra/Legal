package com.legalfriend.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.DashboardCountDetail;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Notification;
import com.legalfriend.entities.SystemDashTiles;
import com.legalfriend.entities.User;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.LoginTrackRepository;
import com.legalfriend.repository.NotificationRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.AgainstInstitutionalCaseService;
import com.legalfriend.service.BillingService;
import com.legalfriend.service.CaseService;
import com.legalfriend.service.ForInstitutionalCaseService;
import com.legalfriend.service.UserService;

@RestController
@RequestMapping("/dash")
public class DashboardController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BillingService billingService;
	
	@Autowired
	CaseService caseService;
	
	@Autowired
	LoginTrackRepository loginTrackRepo;
	
	@Autowired
	UserRoleRepository userRoleRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ForInstitutionalCaseService institutionalCaseService;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	AgainstInstitutionalCaseService againstCaseService;
	
	@GetMapping("/total")
	DashboardCountDetail getTotal(@RequestParam Long userId) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		DashboardCountDetail dashDetail = new DashboardCountDetail();
		dashDetail.setTotalCustomers(userService.findTotalCustomers(userId));
		
		List<Object[]> billingDetails = billingService.findTotalBilling(userId);
		
		for(Object[] ob : billingDetails) {
			dashDetail.setTotalBillings(ob[0]!=null?((BigInteger)ob[0]).longValue():null);
			dashDetail.setTotalAmount(ob[1]!=null?(Double)ob[1]:null);
		}
		dashDetail.setTotalCases(caseService.findTotalCases(userId));
		dashDetail.setTotalLogins(loginTrackRepo.findTotalLogin(userId));
		
		return dashDetail;
	}
	
	@GetMapping("/systemtiles")
	SystemDashTiles getDetails() {
		SystemDashTiles systemTile = new SystemDashTiles();
		
		systemTile.setTotalUsers(userService.findTotalUsers());
		systemTile.setTotalCases(caseService.findTotalCases());
		systemTile.setTrialUsers(userService.findTrialUsersCount());
		systemTile.setOneMonthInActiveUsers(userService.findOneMonthInactiveUsers());
		systemTile.setTotalOrganization(userService.findOrganizationCount());
		systemTile.setInstitutionalCases(institutionalCaseService.count());
		systemTile.setAgainstInstitutionalCases(againstCaseService.count());
		return systemTile;		
	}
	
	@GetMapping("/cases")
	List<DashboardReport> getLegalCasesByMonth(@RequestParam String year) {
		return caseService.findAllOrgCases(year);
	}
	
	@GetMapping("/cases/date")
	List<DashboardReport> getLegalCasesByDate(@RequestParam String startDate,
			@RequestParam String endDate) {
		return caseService.findAllOrgCases(startDate, endDate);
	}
	
	@GetMapping("/forcases")
	List<DashboardReport> getForCasesByMonth(@RequestParam String year) {
		return institutionalCaseService.findAllOrgCases(year);
	}
	
	@GetMapping("/forcases/date")
	List<DashboardReport> getForCasesByDate(@RequestParam String startDate,
			@RequestParam String endDate) {
		return institutionalCaseService.findAllOrgCases(startDate, endDate);
	}
	
	@GetMapping("/againstcases")
	List<DashboardReport> getAgainstCasesByMonth(@RequestParam String year) {
		return againstCaseService.findAllOrgCases(year);
	}
	
	@GetMapping("/againstcases/date")
	List<DashboardReport> getAgainstCasesByDate(@RequestParam String startDate,
			@RequestParam String endDate) {
		return againstCaseService.findAllOrgCases(startDate, endDate);
	}
	
	@GetMapping("/organizations")
	List<User> getOrganization(){
		return userService.findOrganizations();
	}
	
	@GetMapping("/trialusers/year")
    List<DashboardReport> getTrialUsersByYear(@RequestParam String year){
    	return userService.findTrialUsers(year);
    }
	
	@GetMapping("/trialusers/date")
    List<DashboardReport> getTrialUsersByDate(@RequestParam String startDate,
    		@RequestParam String endDate){
    	return userService.findTrialUsers(startDate, endDate);
    }
	
	@GetMapping("/premiumusers/year")
    List<DashboardReport> getPremiumUsersByYear(@RequestParam String year){
    	return userService.findPremiumUsers(year);
    }
	
	@GetMapping("/premiumusers/date")
    List<DashboardReport> getPremiumUsersByDate(@RequestParam String startDate,
    		@RequestParam String endDate){
    	return userService.findPremiumUsers(startDate, endDate);
    }
	
	@GetMapping("/role/premiumusers")
	Map<String, Long> getPremiumUsersByRole(){
		return userService.findPremiumUsersByRole();
	}
	
	@GetMapping("/notification/list")
	List<Notification> getNotifications(){
		return (List<Notification>) notificationRepository.findAll();
	}
	
	
}
