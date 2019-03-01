package com.legalfriend.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.LegalCase;
import com.legalfriend.entities.LegalCaseDashboard;
import com.legalfriend.entities.Organization;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.CaseRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.repository.UserRoleRepository;
import com.legalfriend.service.CaseService;
import com.legalfriend.util.DashboardReportUtil;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseRepository caseRepository;

	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Override
	public List<DashboardReport> findCaseCountByMonth(Long userId) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Object[]> objects = caseRepository.findCaseCountByMonth(userId);
		List<DashboardReport> casesByMonth = new ArrayList<>();
		for (Object[] object : objects) {
			DashboardReport caseDash = new DashboardReport();
			caseDash.setX(object[0] != null ? (String) object[0] : null);
			caseDash.setY(object[1] != null ? ((BigInteger) object[1]).doubleValue() : null);
			casesByMonth.add(caseDash);
		}
		return casesByMonth;
	}

	public List<DashboardReport> findCaseCountByDate(Long userId, String startDate, String endDate) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<DashboardReport> casesByMonth = new ArrayList<>();
		if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
			List<Object[]> objects = caseRepository.findCaseCountByDate(userId, startDate, endDate);

			for (Object[] object : objects) {
				DashboardReport caseDash = new DashboardReport();
				caseDash.setX(object[0] != null ? (String) object[0] : null);
				caseDash.setY(object[1] != null ? ((BigInteger) object[1]).doubleValue() : null);
				casesByMonth.add(caseDash);
			}
		}
		return casesByMonth;
	}

	public List<DashboardReport> findCaseCountByWeek(Long userId) {
		List<String> roles = userRoleRepo.findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		
		List<Object[]> objects = caseRepository.findCaseCountByWeek(userId);
		List<DashboardReport> casesByMonth = new ArrayList<>();
		int value;
		for (Object[] object : objects) {
			DashboardReport caseDash = new DashboardReport();
			if (object[0].toString() != null) {
				value = Integer.parseInt(object[0].toString());

				if (value < 8) {
					caseDash.setX("1-7");
				} else if (value < 15) {
					caseDash.setX("8-14");
				} else if (value < 22) {
					caseDash.setX("15-21");
				} else if (value < 29) {
					caseDash.setX("22-28");
				} else if (value < 36) {
					caseDash.setX("29-35");
				} else if (value < 43) {
					caseDash.setX("36-42");
				} else if (value < 50) {
					caseDash.setX("43-49");
				} else {
					caseDash.setX("50-52");
				}
			}
			caseDash.setY(object[1] != null ? ((BigInteger) object[1]).doubleValue() : null);
			casesByMonth.add(caseDash);
		}
		return casesByMonth;
	}

	public List<LegalCaseDashboard> findUpdatedCases(Long userId) {
		List<Object[]> objects = caseRepository.findUpdatedCases(userId);
		List<LegalCaseDashboard> recentCases = new ArrayList<>();
		for (Object[] object : objects) {
			LegalCaseDashboard casedata = new LegalCaseDashboard();
			casedata.setCaseId(object[0] != null ?object[0].toString() : null);
			casedata.setName(object[1] != null ? object[1].toString() : null);
			casedata.setNextHearingDate(object[2] != null ? object[2].toString() : null);
			casedata.setBranch(object[3] != null ? object[3].toString() : null);
			casedata.setEmployee(object[4] != null ? object[4].toString() : null);
			casedata.setLastUpdatedDate(object[5] != null ? object[5].toString() : null);
			casedata.setRecourceType(object[6] != null ? object[6].toString() : null);
			recentCases.add(casedata);
		}
		return recentCases;
	}

	public List<Organization> findOrganizationCases() {
		List<Object[]> objects = caseRepository.findOrganizationsCaseCount();
		List<Organization> orgCases = new ArrayList<>();
		for (Object[] object : objects) {
			Organization caseCount = new Organization();
			caseCount.setOrganization(object[0] != null ? object[0].toString() : null);
			caseCount.setTotalUsers(object[1] != null ? Long.parseLong(object[1].toString()) : null);
			orgCases.add(caseCount);
		}

		return orgCases;
	}

	public Long findTotalCases(Long userId) {
		return caseRepository.findTotalCases(userId);
	}

	public Long findTotalCases() {
		return caseRepository.count();
	}

	public List<LegalCase> findAll() {
		return caseRepository.findAll();
	}

	@Override
	public List<DashboardReport> findInstitutionalCasesMonth(Long userId) {
		List<Object[]> objects = caseRepository.findInstitutionalCasesMonth(userId);

		List<DashboardReport> instCasesMonth = new ArrayList<>();

		objects.forEach(data -> {
			DashboardReport ob = new DashboardReport();
			ob.setX(data[0] != null ? data[0].toString() : null);
			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
			instCasesMonth.add(ob);
		});

		return instCasesMonth;
	}

	@Override
	public List<DashboardReport> findInstitutionalCasesWeek(Long userId) {
		List<Object[]> objects = caseRepository.findInstitutionalCasesWeek(userId);

		List<DashboardReport> instCasesWeek = new ArrayList<>();

		objects.forEach(data -> {
			DashboardReport ob = new DashboardReport();
			int value;
			if (data[0].toString() != null) {
				value = Integer.parseInt(data[0].toString());

				if (value < 8) {
					ob.setX("1-7");
				} else if (value < 15) {
					ob.setX("8-14");
				} else if (value < 22) {
					ob.setX("15-21");
				} else if (value < 29) {
					ob.setX("22-28");
				} else if (value < 36) {
					ob.setX("29-35");
				} else if (value < 43) {
					ob.setX("36-42");
				} else if (value < 50) {
					ob.setX("43-49");
				} else {
					ob.setX("50-52");
				}
			}

			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
			instCasesWeek.add(ob);
		});

		return instCasesWeek;
	}

	@Override
	public List<DashboardReport> findInstitutionalCasesDate(Long userId, String startDate, String endDate) {
		List<Object[]> objects = caseRepository.findInstitutionalCasesDate(userId, startDate, endDate);

		List<DashboardReport> instCasesDate = new ArrayList<>();

		objects.forEach(data -> {
			DashboardReport ob = new DashboardReport();
			ob.setX(data[0] != null ? data[0].toString() : null);
			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
			instCasesDate.add(ob);
		});

		return instCasesDate;
	}

	@Override
	public List<DashboardReport> findAllCasesMonth(Long userId) {
		List<Object[]> objects = caseRepository.findAllCasesMonth(userId);

		List<DashboardReport> allCasesMonth = new ArrayList<>();

		objects.forEach(data -> {
			DashboardReport ob = new DashboardReport();
			ob.setX(data[0] != null ? data[0].toString() : null);
			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
			allCasesMonth.add(ob);
		});

		return allCasesMonth;
	}

	@Override
	public List<DashboardReport> findAllCasesWeek(Long userId) {
		List<Object[]> objects = caseRepository.findAllCasesWeek(userId);

		List<DashboardReport> allCasesWeek = new ArrayList<>();

		objects.forEach(data -> {
			DashboardReport ob = new DashboardReport();
			int value;
			if (data[0].toString() != null) {
				value = Integer.parseInt(data[0].toString());

				if (value < 8) {
					ob.setX("1-7");
				} else if (value < 15) {
					ob.setX("8-14");
				} else if (value < 22) {
					ob.setX("15-21");
				} else if (value < 29) {
					ob.setX("22-28");
				} else if (value < 36) {
					ob.setX("29-35");
				} else if (value < 43) {
					ob.setX("36-42");
				} else if (value < 50) {
					ob.setX("43-49");
				} else {
					ob.setX("50-52");
				}
			}

			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
			allCasesWeek.add(ob);
		});

		return allCasesWeek;
	}

	@Override
	public List<DashboardReport> findAllCasesDate(Long userId, String startDate, String endDate) {
		List<Object[]> objects = caseRepository.findAllCasesDate(userId, startDate, endDate);

		return DashboardReportUtil.getDashboardReportList(objects);
//		List<DashboardReport> allCasesDate = new ArrayList<>();
//
//		objects.forEach(data -> {
//			DashboardReport ob = new DashboardReport();
//			ob.setX(data[0] != null ? data[0].toString() : null);
//			ob.setY(data[1] != null ? Double.parseDouble(data[1].toString()) : null);
//			allCasesDate.add(ob);
//		});
//
//		return allCasesDate;
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String year){
		List<Object[]> objects = caseRepository.findAllOrgCases(year);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
	
	@Override
	public List<DashboardReport> findAllOrgCases(String startDate, String endDate){
		
		List<Object[]> objects = caseRepository.findAllOrgCases(startDate, endDate);
		
		return DashboardReportUtil.getDashboardReportList(objects);
	}
}
