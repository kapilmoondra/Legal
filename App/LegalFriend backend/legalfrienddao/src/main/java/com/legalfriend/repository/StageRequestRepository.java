package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.RecourseRequest;
import com.legalfriend.entities.StageRequest;

@Repository
public interface StageRequestRepository extends CrudRepository<StageRequest, Long> {

	@Query(value="select * from stage_request order by status asc",nativeQuery=true)
	List<StageRequest> getStageRequests();
}
