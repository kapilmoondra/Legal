package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Compliance;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;

@Repository
public interface ComplianceRepository extends CrudRepository<Compliance, Long> {

	Compliance findById(Long id);

	List<Compliance> findByComplianceName(String complianceName);

	@Query(value = "SELECT * FROM compliance WHERE fk_user_id = ?1", nativeQuery = true)
	List<Compliance> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM compliance WHERE fk_user_id is null", nativeQuery = true)
	List<Compliance> findAll();

	List<Compliance> findByStageAndRecourse(Stage stage, Recourse recourse);

	List<Compliance> findByComplianceNameAndUserId(String lowerCase, Long userId);

	List<Compliance> findByStageAndRecourseAndUserId(Stage stage, Recourse recourse, Long userId);

}
