package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.LegalCase;
import com.legalfriend.util.CustomQuery;


/**
 * Created by deepak.j
 */
@Repository
public interface CaseRepository extends CrudRepository<LegalCase, Long> {

	@Query(value = CustomQuery.CASES_WITH_HEARING_DATE, nativeQuery = true)
	List<Object[]> findAllCasesWithNextHearingDateTommorrow();
	
	LegalCase findById(Long id);

	List<LegalCase> findByUserId(Long userId);

	LegalCase findByCaseId(String caseId);

	@Query(value = "SELECT * FROM legal_case WHERE fk_user_id is null", nativeQuery = true)
	List<LegalCase> findAll();

	@Query(value = CustomQuery.CASE_COUNT_BY_MONTH, nativeQuery = true)
	List<Object[]> findCaseCountByMonth(Long userId);

	@Query(value = CustomQuery.CASE_COUNT_BY_DATE, nativeQuery = true)
	List<Object[]> findCaseCountByDate(Long userId, String startDate, String endDate);

	@Query(value = CustomQuery.CASE_COUNT_BY_WEEK, nativeQuery = true)
	List<Object[]> findCaseCountByWeek(Long userId);

	@Query(value = CustomQuery.RECENT_UPDATE_CASES, nativeQuery = true)
	List<Object[]> findUpdatedCases(Long userId);

	@Query(value = CustomQuery.TOTAL_CASES, nativeQuery = true)
	Long findTotalCases(Long userId);

	@Query(value = CustomQuery.ORG_LEGAL_CASE_COUNT, nativeQuery = true)
	List<Object[]> findOrganizationsCaseCount();
	
	@Query(value = CustomQuery.INSTITUTIONAL_CASES_MONTH, nativeQuery = true)
	List<Object[]> findInstitutionalCasesMonth(Long userId);
	
	@Query(value = CustomQuery.INSTITUTIONAL_CASES_WEEK, nativeQuery = true)
	List<Object[]> findInstitutionalCasesWeek(Long userId);
	
	@Query(value = CustomQuery.INSTITUTIONAL_CASES_DATE, nativeQuery = true)
	List<Object[]> findInstitutionalCasesDate(Long userId,String startDate, String endDate);
	
	@Query(value = CustomQuery.ALL_CASES_MONTH, nativeQuery = true)
	List<Object[]> findAllCasesMonth(Long userId);
	
	@Query(value = CustomQuery.ALL_CASES_WEEK, nativeQuery = true)
	List<Object[]> findAllCasesWeek(Long userId);
	
	@Query(value = CustomQuery.ALL_CASES_DATE, nativeQuery = true)
	List<Object[]> findAllCasesDate(Long userId,String startDate, String endDate);
	
	@Query(value = CustomQuery.ALL_LEGAL_CASES_YEAR, nativeQuery = true)
	List<Object[]> findAllOrgCases(String year);
	
	@Query(value = CustomQuery.ALL_LEGAL_CASES_DATE, nativeQuery = true)
	List<Object[]> findAllOrgCases(String startDate, String endDate);
}
