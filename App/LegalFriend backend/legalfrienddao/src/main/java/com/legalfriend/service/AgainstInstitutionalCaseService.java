package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Organization;

public interface AgainstInstitutionalCaseService {
	
	public List<AgainstInstitutionalCase> findAll();
	
	public Long count();
	
	public List<Organization> findOrganizationsAgainstCaseCount();
	
	List<DashboardReport> findAllOrgCases(String year);
	
	List<DashboardReport> findAllOrgCases(String startDate, String endDate);
}
