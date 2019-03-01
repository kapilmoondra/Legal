package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.ComplianceStatus;

/**
 * Created by deepak.j
 */
@Repository
public interface ComplianceStatusRepository extends CrudRepository<ComplianceStatus, Long> {
	List<ComplianceStatus> findAll();
}
