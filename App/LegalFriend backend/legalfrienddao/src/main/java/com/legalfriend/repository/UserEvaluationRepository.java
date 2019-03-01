package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserEvaluation;

@Repository
public interface UserEvaluationRepository extends CrudRepository<UserEvaluation, Long> {

	List<UserEvaluation> findByUserId(Long userId);

}
