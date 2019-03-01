package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.LegalCase;
import com.legalfriend.entities.CaseCompliance;

@Repository
public interface CaseComplianceRepository extends CrudRepository<CaseCompliance, Long> {

	CaseCompliance findById(Long id);

	List<CaseCompliance> findByLegalCaseAndStatus(LegalCase legalCase, String string);

}
