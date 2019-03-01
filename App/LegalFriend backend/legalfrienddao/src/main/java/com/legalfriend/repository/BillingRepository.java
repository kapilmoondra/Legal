package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Billing;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;
import com.legalfriend.util.CustomQuery;

@Repository
public interface BillingRepository extends CrudRepository<Billing, Long> {

	@Query(value = CustomQuery.BILLING_BY_INSTITUTION, nativeQuery = true)
	List<Object[]> findBillingByInstitution(Long userId);
	
	@Query(value = CustomQuery.BILLING_BY_INSTITUTION_NAME, nativeQuery = true)
	List<Object[]> findBillingByInstitution(Long userId, String institutionName);

	@Query(value = CustomQuery.INDIVIDUAL_BILLING, nativeQuery = true)
	List<Object[]> findIndividualBilling(Long userId);

	@Query(value = CustomQuery.TOTAL_BILLING, nativeQuery = true)
	List<Object[]> findTotalBilling(Long userId);

	Billing findById(Long id);

	@Query(value = "SELECT * FROM billing WHERE fk_user_id = ?1 and fk_invoice_id is null and fk_recourse_id is not null", nativeQuery = true)
	List<Billing> findByUserId(Long userId);

	@Query(value = "SELECT * FROM billing WHERE fk_user_id = ?1 and fk_invoice_id is not null and fk_institution_id is not null and fk_recourse_id is not null", nativeQuery = true)
	List<Billing> findByUserIdAndInst(Long userId);

	@Query(value = "SELECT * FROM billing WHERE fk_invoice_id = ?1", nativeQuery = true)
	List<Billing> findByInvoice(Long id);

	Billing findByStageAndRecourse(Stage stage, Recourse recourse);

	Billing findByInstitutionAndStageAndRecourseAndAmount(Institution institution, Stage stage, Recourse recourse,
			Double amount);
}