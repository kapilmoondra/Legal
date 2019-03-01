package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Branch;
import com.legalfriend.entities.IndividualBilling;
import com.legalfriend.entities.Invoice;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;

@Repository
public interface IndividualBillingRepository extends CrudRepository<IndividualBilling, Long> {

	IndividualBilling findById(Long id);

	@Query(value = "SELECT * FROM individual_billing WHERE fk_user_id = ?1 and fk_invoice_id is null and fk_recourse_id is not null", nativeQuery = true)
	List<IndividualBilling> findByUserId(Long userId);

	List<IndividualBilling> findByInvoice(Invoice invoice);

	IndividualBilling findByStageAndRecourse(Stage stage, Recourse recourse);

	IndividualBilling findByUserIdAndStageAndRecourseAndAmount(Long userId, Stage stage, Recourse recourse,
			Double amount);

	@Query(value = "SELECT * FROM individual_billing WHERE fk_invoice_id = ?1", nativeQuery = true)
	List<IndividualBilling> findByInvoice(Long id);

	List<IndividualBilling> findByUserIdAndBranch(Long userId, Branch findById);

}
