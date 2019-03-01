package com.legalfriend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.InvoiceRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.InvoiceService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepo;

	@Override
	public List<DashboardReport> findInvoicesAmount(Long userId, String start,
			String end) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null) {
			objects = invoiceRepo.findInvoicesAmount(userId, start);
		} else {
			objects = invoiceRepo.findInvoicesAmount(userId, start, end);
		}
		return DashboardReportUtil.getDashboardReportList(objects);
	}

	@Override
	public List<DashboardReport> findInvoicesInstAmount(Long userId, String start,
			String end) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null) {
			objects = invoiceRepo.findInvoicesInstAmount(userId, start);
		} else {
			objects = invoiceRepo.findDateInvoicesInst(userId, start, end);
		}
		return DashboardReportUtil.getDashboardReportList(objects);
	}

	@Override
	public List<DashboardReport> findInvoicesInstAmount(Long userId, String start,
			String end, String month) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null) {
			objects = invoiceRepo.findInvoicesInstAmount(userId, start, month);
		} else {
			objects = invoiceRepo.findDateInvoicesInst(userId, start, end, month);
		}
		return DashboardReportUtil.getDashboardReportList(objects);


	}
	
	public List<DashboardReport> findInvoicesAmountByBranch(Long userId, String start,
			String end, String branch) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null||end.isEmpty()) {
			objects = invoiceRepo.findInvoicesAmountByBranch(userId, start, branch);
		} else {
			objects = invoiceRepo.findInvoicesAmountByBranch(userId, start, end, branch);
		}
		return DashboardReportUtil.getDashboardReportList(objects);

	}
	
	@Override
	public List<DashboardReport> findInvoicesBranchInstAmount(Long userId, String start,
			String end, String branch) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null||end.isEmpty()) {
			objects = invoiceRepo.findInvoicesBranchInstAmount(userId, start, branch);
		} else {
			objects = invoiceRepo.findInvoicesBranchInstAmount(userId, start, end, branch);
		}
		return DashboardReportUtil.getDashboardReportList(objects);
	}

	@Override
	public List<DashboardReport> findInvoicesBranchInstAmount(Long userId, String start,
			String end, String month, String branch) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects;
		if (end==null||end.isEmpty()) {
			objects = invoiceRepo.findInvoicesBranchInstMonthAmount(userId, start, month, branch);
		} else {
			objects = invoiceRepo.findInvoicesBranchInstMonthAmount(userId, start, end, month, branch);
		}
		return DashboardReportUtil.getDashboardReportList(objects);


	}

}
