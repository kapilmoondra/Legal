package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.LegalCase;
import com.legalfriend.entities.LegalCaseDashboard;
import com.legalfriend.entities.Organization;

public interface CaseService {

	List<DashboardReport> findCaseCountByMonth(Long userId);
	
	List<DashboardReport> findCaseCountByDate(Long userId,
			String startDate, String endDate);
	
	List<DashboardReport> findCaseCountByWeek(Long userId);
	
	List<LegalCaseDashboard> findUpdatedCases(Long userId);
	
	Long findTotalCases(Long userId);
	
	Long findTotalCases();
	
	List<LegalCase> findAll();
	
	List<Organization> findOrganizationCases();
	
	List<DashboardReport> findInstitutionalCasesMonth(Long userId);
	
	List<DashboardReport> findInstitutionalCasesWeek(Long userId);
	
	List<DashboardReport> findInstitutionalCasesDate(Long userId,
			String startDate, String endDate);
	
	List<DashboardReport> findAllCasesMonth(Long userId);
	
	List<DashboardReport> findAllCasesWeek(Long userId);
	
	List<DashboardReport> findAllCasesDate(Long userId,
			String startDate, String endDate);
	
	List<DashboardReport> findAllOrgCases(String year);
	
	List<DashboardReport> findAllOrgCases(String startDate, String endDate);
}
