package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Status;

/**
 * Created by deepak.j
 */
@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {

	Status findByStatusId(Long statusId);
	
	List<Status> findAll();
}
