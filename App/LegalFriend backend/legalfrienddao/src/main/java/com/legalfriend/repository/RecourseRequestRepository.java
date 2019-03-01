package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.RecourseRequest;

/**
 * Created by deepak.j
 */
@Repository
public interface RecourseRequestRepository extends CrudRepository<RecourseRequest, Long> {

	@Query(value="select * from recourse_request order by status asc",nativeQuery=true)
	List<RecourseRequest> getRecourseRequests();
}
