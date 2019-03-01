package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.District;

/**
 * Created by deepak.j
 */
@Repository
public interface DistrictRepository extends CrudRepository<District, Long> {

	District findById(Long id);

	List<District> findByDistrictName(String cityName);

	@Query(value = "SELECT * FROM district WHERE fk_user_id = ?1", nativeQuery = true)
	List<District> findByUserId(Long fkUserId);
	
	@Query(value = "SELECT * FROM district WHERE fk_user_id is null", nativeQuery = true)
	List<District> findAll();
}
