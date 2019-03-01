package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;

@Repository
public interface StageRepository extends CrudRepository<Stage, Long> {

	Stage findById(Long id);

	List<Stage> findByStageName(String stageName);

	List<Stage> findByStageCode(String stageCode);

	@Query(value = "SELECT * FROM stage WHERE fk_user_id is null", nativeQuery = true)
	List<Stage> findAll();

	List<Stage> findByIdAndRecourse(Long id, Recourse recourse);

	List<Stage> findByStageCodeAndRecourse(String stageId, Recourse recourse);

}
