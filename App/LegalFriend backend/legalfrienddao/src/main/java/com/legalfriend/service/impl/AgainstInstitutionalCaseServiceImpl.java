package com.legalfriend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Organization;
import com.legalfriend.repository.AgainstInstitutionalCaseRepository;
import com.legalfriend.service.AgainstInstitutionalCaseService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class AgainstInstitutionalCaseServiceImpl implements AgainstInstitutionalCaseService {

	@Autowired
	AgainstInstitutionalCaseRepository againstInstRepo;
	
	public List<AgainstInstitutionalCase> findAll(){
		return (List<AgainstInstitutionalCase>)againstInstRepo.findAll();
	}


	public Long count(){		
		return againstInstRepo.count();
	}
	
	public List<Organization> findOrganizationsAgainstCaseCount(){
		List<Object[]> objects = againstInstRepo.findOrganizationsAgainstCaseCount();
		List<Organization> orgAgainstCases = new ArrayList<>();
		for(Object[] object : objects) {
			Organization caseCount = new Organization();
			caseCount.setOrganization(object[0]!=null?object[0].toString():null);
			caseCount.setTotalUsers(object[1]!=null?Long.parseLong(object[1].toString()):null);
			orgAgainstCases.add(caseCount);
		}
		
		return orgAgainstCases;
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String year){
		List<Object[]> objects = againstInstRepo.findAllOrgCases(year);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String startDate, String endDate){
		
		List<Object[]> objects = againstInstRepo.findAllOrgCases(startDate, endDate);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
}
