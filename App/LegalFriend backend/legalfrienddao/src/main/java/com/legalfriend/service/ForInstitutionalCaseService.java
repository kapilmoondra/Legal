package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.entities.Organization;

public interface ForInstitutionalCaseService {

	List<ForInstitutionalCase> findAll();
	
	Long count();
	
	List<Organization> findOrganizationsForCaseCount();
	
	List<DashboardReport> findAllOrgCases(String year);
	
	List<DashboardReport> findAllOrgCases(String startDate, String endDate);
}
