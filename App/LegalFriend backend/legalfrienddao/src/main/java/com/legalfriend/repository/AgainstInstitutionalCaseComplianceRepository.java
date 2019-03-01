package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.entities.AgainstInstitutionalCaseCompliance;

/**
 * Created by deepak.j
 */
@Repository
public interface AgainstInstitutionalCaseComplianceRepository
		extends CrudRepository<AgainstInstitutionalCaseCompliance, Long> {

	AgainstInstitutionalCaseCompliance findById(Long id);

	List<AgainstInstitutionalCaseCompliance> findByLegalCaseAndStatus(AgainstInstitutionalCase legalCase, String name);
}
