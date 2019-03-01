package com.legalfriend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BillingRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService{

	@Autowired
	BillingRepository billingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepo;
	
	@Override
	public List<DashboardReport> findBillingByInstitution(Long userId) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = billingRepository.findBillingByInstitution(userId);
		List<DashboardReport> institutionBillings = new ArrayList<>();
		
		objects.forEach(object->{
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?object[0].toString():null);
			data.setY(object[1]!=null?(Double)object[1]:null);
			institutionBillings.add(data);
		});

		return institutionBillings;
	}
	
	@Override
	public List<DashboardReport> findBillingByInstitution(Long userId, String name) {
		List<Object[]> objects = billingRepository.findBillingByInstitution(userId, name);
		List<DashboardReport> institutionBillings = new ArrayList<>();
		objects.forEach(object->{
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?object[0].toString():null);
			data.setY(object[1]!=null?(Double)object[1]:null);
			institutionBillings.add(data);
		});
		
		return institutionBillings;
	}
	
	@Override
	public List<DashboardReport> findIndividualBilling(Long userId) {
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = billingRepository.findIndividualBilling(userId);
		List<DashboardReport> individualBillings = new ArrayList<>();
		
		objects.forEach(object->{
			DashboardReport data = new DashboardReport();
			data.setX(object[0]!=null?object[0].toString():null);
			data.setY(object[1]!=null?(Double)object[1]:null);
			individualBillings.add(data);
		});

		return individualBillings;
	}

	@Override
	public List<Object[]> findTotalBilling(Long userId) {
		return billingRepository.findTotalBilling(userId);
	}

	
}
