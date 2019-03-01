package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.InvoiceNumber;

@Repository
public interface InvoiceNumberRepository extends CrudRepository<InvoiceNumber, Long> {

	InvoiceNumber findByUserId(Long userId);

}
