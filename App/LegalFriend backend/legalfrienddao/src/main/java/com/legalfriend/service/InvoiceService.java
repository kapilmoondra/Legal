package com.legalfriend.service;

import java.util.List;

import com.legalfriend.entities.DashboardReport;

public interface InvoiceService {

	List<DashboardReport> findInvoicesAmount(Long userId, String start,
			String end);
	
	List<DashboardReport> findInvoicesInstAmount(Long userId, String start,
			String end);
	
	List<DashboardReport> findInvoicesInstAmount(Long userId, String start,
			String end,String month);
	
	List<DashboardReport> findInvoicesAmountByBranch(Long userId, String start,
			String end,String branch);
	
	List<DashboardReport> findInvoicesBranchInstAmount(Long userId, String start,
			String end, String branch);
	
	List<DashboardReport> findInvoicesBranchInstAmount(Long userId, String start,
			String end, String month, String branch);
}
