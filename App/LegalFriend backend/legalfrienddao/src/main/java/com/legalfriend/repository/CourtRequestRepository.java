package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.legalfriend.entities.CourtRequest;
import com.legalfriend.entities.StageRequest;

public interface CourtRequestRepository extends CrudRepository<CourtRequest, Long>{

	@Query(value="select * from court_request order by status asc",nativeQuery=true)
	List<CourtRequest> getCourtRequests();
}
