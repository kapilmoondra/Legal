package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.RecourseAbbreviation;

@Repository
public interface RecourseAbbreviationRepository extends CrudRepository<RecourseAbbreviation, Long> {

}
