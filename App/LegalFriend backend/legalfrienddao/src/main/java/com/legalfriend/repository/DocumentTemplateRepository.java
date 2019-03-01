package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.DocumentTemplate;

@Repository
public interface DocumentTemplateRepository extends CrudRepository<DocumentTemplate, Long> {

	List<DocumentTemplate> findByUserId(Long id);

}
