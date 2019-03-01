package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.DashboardReport;

public interface BillingService {

	List<DashboardReport> findBillingByInstitution(Long userId);
	
	List<DashboardReport> findIndividualBilling(Long userId);
	
	List<DashboardReport> findBillingByInstitution(Long userId, String name);
	
	List<Object[]>  findTotalBilling(Long userId);
}
