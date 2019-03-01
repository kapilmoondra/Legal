package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Recourse;

/**
 * Created by deepak.j
 */
@Repository
public interface RecourseRepository extends CrudRepository<Recourse, Long> {

	Recourse findById(Long id);

	List<Recourse> findByRecourseName(String recourseName);

	@Query(value = "SELECT * FROM recourse WHERE fk_user_id = ?1", nativeQuery = true)
	List<Recourse> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM recourse WHERE fk_user_id is null", nativeQuery = true)
	List<Recourse> findAll();

	List<Recourse> findByRecourseCode(String recourseCode);

}
