package com.legalfriend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.BranchService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class BranchServiceImpl implements BranchService{
	
	@Autowired
	BranchRepository branchRepo;
	
	@Autowired
	UserRoleRepository userRoleRepo;
	
	@Autowired
	UserRepository userRepository;
	
	public List<DashboardReport> findTotalUsersByBranch(Long userId){
		
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects= branchRepo.findTotalUsersByBranch(userId);
				
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findBranchBilling(Long userId, String start, String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects;
		if(end.isEmpty()) {
			objects = branchRepo.findBranchBilling(userId,start);
		}
		else {
			objects = branchRepo.findRangeBranchBilling(userId,start, end);
		}
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findBranchBilling(Long userId, List<String> branches, String start, String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects;
		if(end.isEmpty()) {
			objects= branchRepo.findBranchBilling(userId, branches,start);
		}
		else {
			objects= branchRepo.findRangeBranchBilling(userId, branches,start, end);
		}
		
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findBranchInstitutionBilling(Long userId,String start,String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if(end.isEmpty()) {
			objects= branchRepo.findBranchInstitutionBilling(userId,start);
		}
		else {
			objects= branchRepo.findRangeBranchInstitutionBilling(userId,start, end);
		}
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findBranchInstitutionBilling(Long userId, List<String> branches,
			String start,String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if(end.isEmpty()) {
			objects= branchRepo.findBranchInstitutionBilling(userId, branches,start);
		}
		else{
			objects= branchRepo.findRangeBranchInstitutionBilling(userId, branches,start, end);
		}
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	public List<DashboardReport> findBranchInstitutionBilling(Long userId,
			List<String> branches,String month, String start, String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if(end.isEmpty()) {
			objects= branchRepo.findBranchInstitutionBilling(userId, branches,month,start);
		}
		else{
			objects= branchRepo.findRangeBranchInstitutionBilling(userId, branches,
					month,start, end);
		}
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}

	public List<DashboardReport> findBranchInstitutionBilling(Long userId,
			String month, String start, String end){
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects;
		if(end.isEmpty()) {
			if(month.isEmpty()) {
				objects= branchRepo.findBranchInstitutionBilling(userId,start);
			}
			else {
				objects= branchRepo.findBranchInstitutionBilling(userId,month,start);
			}
		}
		else {
			if(month.isEmpty()) {
				objects= branchRepo.findRangeBranchInstitutionBilling(userId,start, end);
			}
			else {
				objects= branchRepo.findRangeBranchInstitutionBilling(userId,month,start, end);
			}
		}
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	

}
