package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Invoice;
import com.legalfriend.util.CustomQuery;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	Invoice findById(Long id);

	Invoice findByIdAndStatus(Long invoiceId, String string);

	List<Invoice> findByUserId(Long userId);

	@Query(value = "SELECT * FROM invoice WHERE fk_user_id = ?1 and status <> ?2 and  fk_institution_id is not null", nativeQuery = true)
	List<Invoice> findInstitutionalInvoices(Long userId, String status);

	@Query(value = "SELECT * FROM invoice WHERE fk_user_id = ?1 and status <> ?2 and fk_institution_id is null and fk_branch_id = ?3", nativeQuery = true)
	List<Invoice> findIndividualInvoices(Long userId, String name, Long branchId);

	Invoice findByInvoiceNumberAndUserId(Long invoiceId, Long userId);
	
	@Query(value = CustomQuery.YEAR_INVOICE_AMOUNT, nativeQuery = true)
	List<Object[]> findInvoicesAmount(Long userId, String year);
	
	@Query(value = CustomQuery.YEAR_INVOICE_INST, nativeQuery = true)
	List<Object[]> findInvoicesInstAmount(Long userId, String year);
	
	@Query(value = CustomQuery.YEAR_INVOICE_INST_MONTH, nativeQuery = true)
	List<Object[]> findInvoicesInstAmount(Long userId, String year, String month);
	
	@Query(value = CustomQuery.YEAR_INVOICE_BRANCH_AMOUNT, nativeQuery = true)
	List<Object[]> findInvoicesAmountByBranch(Long userId, String year, String branch);
	
	@Query(value = CustomQuery.DATE_INVOICE_BRANCH_AMOUNT, nativeQuery = true)
	List<Object[]> findInvoicesAmountByBranch(Long userId, String start, String end, String branch);
	
	
	@Query(value = CustomQuery.DATE_INVOICE_AMOUNT, nativeQuery = true)
	List<Object[]> findInvoicesAmount(Long userId, String start,String end);
	
	@Query(value = CustomQuery.DATE_INVOICE_INST, nativeQuery = true)
	List<Object[]> findDateInvoicesInst(Long userId, String start, String end);
	
	@Query(value = CustomQuery.DATE_INVOICE_INST_MONTH, nativeQuery = true)
	List<Object[]> findDateInvoicesInst(Long userId, String start, String end, String month);
	
	@Query(value = CustomQuery.YEAR_INVOICE_BRANCH_INST, nativeQuery = true)
	List<Object[]> findInvoicesBranchInstAmount(Long userId, String year, String branch);
	
	@Query(value = CustomQuery.DATE_INVOICE_BRANCH_INST, nativeQuery = true)
	List<Object[]> findInvoicesBranchInstAmount(Long userId, String start,
			String end,String branch);
	
	@Query(value = CustomQuery.YEAR_INVOICE_BRANCH_INST_MONTH, nativeQuery = true)
	List<Object[]> findInvoicesBranchInstMonthAmount(Long userId, String year,
			String month, String branch);
	
	@Query(value = CustomQuery.DATE_INVOICE_BRANCH_INST_MONTH, nativeQuery = true)
	List<Object[]> findInvoicesBranchInstMonthAmount(Long userId, String start,
			String end,String month,String branch);
	

}
