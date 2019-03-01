package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Court;

/**
 * Created by deepak.j
 */
@Repository
public interface CourtRepository extends CrudRepository<Court, Long> {

	Court findById(Long id);

	List<Court> findByCourtName(String courtName);

	@Query(value = "SELECT * FROM court WHERE fk_user_id = ?1", nativeQuery = true)
	List<Court> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM court WHERE fk_user_id is null", nativeQuery = true)
	List<Court> findAll();

}
