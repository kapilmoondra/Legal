package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Institution;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, Long> {
	Institution findById(Long id);

	List<Institution> findByInstitutionName(String InstitutionName);

	@Query(value = "SELECT * FROM institution WHERE fk_user_id = ?1", nativeQuery = true)
	List<Institution> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM institution WHERE fk_user_id is null", nativeQuery = true)
	public List<Institution> findAll();

	List<Institution> findByInstitutionNameAndUserId(String lowerCase, Long userId);

	Institution findByIdAndDefaultInstitution(Long id, boolean defaultInstitution);

	Institution findByUserIdAndDefaultInstitution(Long userId, boolean b);

	List<Institution> findByIdAndUserId(Long id, Long userId);

	List<Institution> findByInstitutionNameAndUserIdAndInstitutionName(String lowerCase, Long userId,
			String institutionName);
}
