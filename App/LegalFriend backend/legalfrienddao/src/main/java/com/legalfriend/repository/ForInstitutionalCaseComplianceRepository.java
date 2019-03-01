package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.entities.ForInstitutionalCaseCompliance;

/**
 * Created by deepak.j
 */
@Repository
public interface ForInstitutionalCaseComplianceRepository extends CrudRepository<ForInstitutionalCaseCompliance, Long> {

	ForInstitutionalCaseCompliance findById(Long id);

	List<ForInstitutionalCaseCompliance> findByLegalCaseAndStatus(ForInstitutionalCase legalCase, String name);

}
