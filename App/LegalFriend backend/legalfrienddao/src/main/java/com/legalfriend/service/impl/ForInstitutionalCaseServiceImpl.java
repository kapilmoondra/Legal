package com.legalfriend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.entities.Organization;
import com.legalfriend.repository.ForInstitutionalCaseRepository;
import com.legalfriend.service.ForInstitutionalCaseService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class ForInstitutionalCaseServiceImpl implements ForInstitutionalCaseService{

	@Autowired
	ForInstitutionalCaseRepository institutionalCaseRepo;
	
	
	public List<ForInstitutionalCase> findAll(){
		return (List<ForInstitutionalCase>)institutionalCaseRepo.findAll();
	}


	@Override
	public Long count() {
		
		return institutionalCaseRepo.count();
	}
	
	public List<Organization> findOrganizationsForCaseCount(){
		List<Object[]> objects = institutionalCaseRepo.findOrganizationsForCaseCount();
		List<Organization> orgForCases = new ArrayList<>();
		for(Object[] object : objects) {
			Organization caseCount = new Organization();
			caseCount.setOrganization(object[0]!=null?object[0].toString():null);
			caseCount.setTotalUsers(object[1]!=null?Long.parseLong(object[1].toString()):null);
			orgForCases.add(caseCount);
		}
		
		return orgForCases;
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String year){
		List<Object[]> objects = institutionalCaseRepo.findAllOrgCases(year);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String startDate, String endDate){
		
		List<Object[]> objects = institutionalCaseRepo.findAllOrgCases(startDate, endDate);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
}
