package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.util.CustomQuery;

/**
 * Created by deepak.j
 */
@Repository
public interface ForInstitutionalCaseRepository extends CrudRepository<ForInstitutionalCase, Long> {

	ForInstitutionalCase findByInstitutionIdAndId(Long institutionId, Long institutionalCaseId);

	ForInstitutionalCase findById(Long institutionCaseId);

	List<ForInstitutionalCase> findByInstitutionIdAndUserId(Long institutionId, Long userId);

	ForInstitutionalCase findByCaseId(String caseId);

	List<ForInstitutionalCase> findByInstitutionId(Long institutionId);

	List<ForInstitutionalCase> findByInstitutionIdAndBranchIdAndUserId(Long institutionId, Long branchId, Long userId);

	ForInstitutionalCase findByInstitutionIdAndBranchIdAndId(Long institutionId, Long branchId,
			Long institutionalCaseId);

	List<ForInstitutionalCase> findByInstitutionIdAndBranchId(Long institutionId, Long branchId);
	
	@Query(value = CustomQuery.ORG_FOR_INST_COUNT, nativeQuery = true)
	List<Object[]> findOrganizationsForCaseCount();
	
	@Query(value = CustomQuery.FOR_INST_HEARING_DATE, nativeQuery = true)
	List<Object[]> findCasesWithNextHearingDateTommorrow();
	
	@Query(value = CustomQuery.ALL_FOR_CASES_YEAR, nativeQuery = true)
	List<Object[]> findAllOrgCases(String year);
	
	@Query(value = CustomQuery.ALL_FOR_CASES_DATE, nativeQuery = true)
	List<Object[]> findAllOrgCases(String startDate, String endDate);

}
