package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.State;

/**
 * Created by deepak.j
 */
@Repository
public interface StateRepository extends CrudRepository<State, Long> {

	State findById(Long id);

	List<State> findByStateName(String cityName);

	@Query(value = "SELECT * FROM state WHERE fk_user_id = ?1", nativeQuery = true)
	List<State> findByUserId(Long fkUserId);
	
	
	@Query(value = "SELECT * FROM state WHERE fk_user_id is null", nativeQuery = true)
	List<State> findAll();

}
