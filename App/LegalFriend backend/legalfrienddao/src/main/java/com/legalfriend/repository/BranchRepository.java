package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Branch;
import com.legalfriend.util.CustomQuery;


@Repository
public interface BranchRepository extends CrudRepository<Branch, Long> {

	Branch findById(Long id);

	List<Branch> findByBranchName(String BranchName);
	
	List<Branch> findByBranchCode(String BranchCode);

	@Query(value = "SELECT * FROM branch WHERE fk_user_id = ?1", nativeQuery = true)
	List<Branch> findByUserId(Long fkUserId);
	
	@Query(value = "SELECT * FROM branch WHERE fk_user_id is null", nativeQuery = true)
	List<Branch> findAll();

	List<Branch> findByBranchCodeAndUserId(String lowerCase, Long userId);

	List<Branch> findByBranchCodeAndUserIdAndBranchName(String lowerCase, Long userId,
			String branchName);
	
	@Query(value = CustomQuery.ORG_BRANCHES, nativeQuery = true)
	List<Object[]> findTotalUsersByBranch(Long userId);
	
	@Query(value = CustomQuery.BRANCH_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchBilling(Long userId,String startDate, String endDate);
	
	@Query(value = CustomQuery.SELECTED_BRANCH_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchBilling(Long userId, List<String> branches,String startDate
			, String endDate);
	
	@Query(value = CustomQuery.SEL_MONTH_BR_INST_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchInstitutionBilling(Long userId, List<String> branches, String month
			,String startDate, String endDate);
	
	@Query(value = CustomQuery.SEL_BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchInstitutionBilling(Long userId, List<String> branches,
			String startDate, String endDate);
	
	@Query(value = CustomQuery.BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchInstitutionBilling(Long userId,String startDate, String endDate);
	
	@Query(value = CustomQuery.MONTH_BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findRangeBranchInstitutionBilling(Long userId, String month,String startDate,
			String endDate);
	
	@Query(value = CustomQuery.YEAR_BRANCH_BILLING, nativeQuery = true)
	List<Object[]> findBranchBilling(Long userId, String year);
	
	@Query(value = CustomQuery.YEAR_SELECTED_BRANCH_BILLING, nativeQuery = true)
	List<Object[]> findBranchBilling(Long userId, List<String> branches,String year);
	
	@Query(value = CustomQuery.YEAR_BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findBranchInstitutionBilling(Long userId, String year);
	
	@Query(value = CustomQuery.YEAR_MONTH_BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findBranchInstitutionBilling(Long userId, String month, String year);
	
	@Query(value = CustomQuery.YEAR_SEL_BRANCH_INST_BILLING, nativeQuery = true)
	List<Object[]> findBranchInstitutionBilling(Long userId, List<String> branches, String year);
	
	@Query(value = CustomQuery.YEAR_SEL_MONTH_BR_INST_BILLING, nativeQuery = true)
	List<Object[]> findBranchInstitutionBilling(Long userId, List<String> branches,
			String month,String year);
}
