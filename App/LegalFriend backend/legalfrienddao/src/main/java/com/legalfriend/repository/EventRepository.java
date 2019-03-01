package com.legalfriend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.legalfriend.entities.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findAll();

	List<Event> findByUserId(Long userId);

	Event findByReferenceNumberAndEndDate(String caseId, Date endDate);

	Event findByEventId(Long eventId);

	@Query(value = "SELECT * FROM event WHERE fk_user_id = ?1 and start_date >= ?2 and (end_date <= ?3 or end_date is null) order by start_date", nativeQuery = true)
	List<Event> findByUserIdAndStartDateAndEndDate(Long userId, String startDate, String endDate);

	Event findByReferenceNumber(String caseId);

}
