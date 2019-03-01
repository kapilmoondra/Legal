package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.City;

/**
 * Created by deepak.j
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

	City findById(Long id);

	List<City> findByCityName(String cityName);

	@Query(value = "SELECT * FROM city WHERE fk_user_id = ?1", nativeQuery = true)
	List<City> findByUserId(Long fkUserId);
	
	@Query(value = "SELECT * FROM city WHERE fk_user_id is null", nativeQuery = true)
	List<City> findAll();

}
