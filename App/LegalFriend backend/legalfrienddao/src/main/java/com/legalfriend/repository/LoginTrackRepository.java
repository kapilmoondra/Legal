package com.legalfriend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.legalfriend.entities.LoginTrack;
import com.legalfriend.util.CustomQuery;

public interface LoginTrackRepository extends CrudRepository<LoginTrack, Long>{

	@Query(value = CustomQuery.TOTAL_LOGIN , nativeQuery = true)
	Long findTotalLogin(Long userId);
}
