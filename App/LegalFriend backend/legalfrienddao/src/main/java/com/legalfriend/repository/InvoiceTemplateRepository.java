package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.InvoiceTemplate;

@Repository
public interface InvoiceTemplateRepository extends CrudRepository<InvoiceTemplate, Long> {

	InvoiceTemplate findById(Long id);

	InvoiceTemplate findByUserId(Long userId);

}
