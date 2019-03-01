package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.DashboardReport;

public interface BranchService {

	List<DashboardReport> findTotalUsersByBranch(Long userId);
	
	List<DashboardReport> findBranchBilling(Long userId,String start,String end);
	
	List<DashboardReport> findBranchBilling(Long userId, List<String> branches,String start,String end);
	
	List<DashboardReport> findBranchInstitutionBilling(Long userId, List<String> branches,String start,String end);
	
	List<DashboardReport> findBranchInstitutionBilling(Long userId,String start,String end);
	
	List<DashboardReport> findBranchInstitutionBilling(Long userId, List<String> branches,
			String month,String start,String end);
	
	List<DashboardReport> findBranchInstitutionBilling(Long userId,
			String month,String start,String end);
	

}
