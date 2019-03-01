package com.legalfriend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.CaseFiles;

@Repository
public interface CaseFilesRepository extends CrudRepository<CaseFiles, Long> {

	CaseFiles findById(Long id);

	CaseFiles findByCaseId(String caseId);

	List<CaseFiles> findByCaseIdAndThruDateAndRemarkOnly(String caseId, Date thruDate, Boolean remarkOnly);

	CaseFiles findByIdAndThruDate(Long fileId, Date thruDate);

}
