package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.util.CustomQuery;

/**
 * Created by deepak.j
 */
@Repository
public interface AgainstInstitutionalCaseRepository extends CrudRepository<AgainstInstitutionalCase, Long> {

	AgainstInstitutionalCase findByInstitutionIdAndId(Long institutionId, Long institutionalCaseId);

	AgainstInstitutionalCase findById(Long institutionCaseId);

	List<AgainstInstitutionalCase> findByInstitutionIdAndUserId(Long institutionId, Long userId);

	AgainstInstitutionalCase findByCaseId(String caseId);

	List<AgainstInstitutionalCase> findByInstitutionId(Long institutionId);

	List<AgainstInstitutionalCase> findByInstitutionIdAndBranchIdAndUserId(Long institutionId, Long branchId,
			Long userId);

	AgainstInstitutionalCase findByInstitutionIdAndBranchIdAndId(Long institutionId, Long branchId,
			Long institutionalCaseId);

	List<AgainstInstitutionalCase> findByInstitutionIdAndBranchId(Long institutionId, Long branchId);

	@Query(value = CustomQuery.ORG_AGAINST_INST_COUNT, nativeQuery = true)
	List<Object[]> findOrganizationsAgainstCaseCount();
	
	@Query(value = CustomQuery.AGAINST_INST_HEARING_DATE, nativeQuery = true)
	List<Object[]> findCasesWithNextHearingDateTommorrow();
	
	@Query(value = CustomQuery.ALL_AGAINST_CASES_YEAR, nativeQuery = true)
	List<Object[]> findAllOrgCases(String year);
	
	@Query(value = CustomQuery.ALL_AGAINST_CASES_DATE, nativeQuery = true)
	List<Object[]> findAllOrgCases(String startDate, String endDate);
}
