package com.legalfriend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.Event;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.EventRepository;
import com.legalfriend.response.EventResponse;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	private SessionFactory sessionFactory;

	private static final String FETCH_USER_ROLE = "select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = :userId";

	private static final String FETCH_EVENTS_BY_USER = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join legal_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) order by start_date";

	private static final String FETCH_FOR_EVENTS_BY_USER = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join for_institutional_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) order by start_date";

	private static final String FETCH_AGAINST_EVENTS_BY_USER = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join against_institutional_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) order by start_date";

	private static final String FETCH_AGAINST_EVENTS_BY_DATE = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join against_institutional_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) order by start_date";

	private static final String FETCH_EVENTS_BY_DATE = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join legal_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) AND e.start_date >= :startDate  AND (e.end_date <= :endDate or e.end_date is null)  order by start_date";

	private static final String FETCH_FOR_EVENTS_BY_DATE = "select event_id, event_name, event_description, start_date, end_date,  reference_number from event e inner join for_institutional_case lc on lc.case_id=e.reference_number WHERE (lc.employee_id = :userId OR lc.manager_id = :userId OR lc.customer_id = :userId) AND e.start_date >= :startDate  AND (e.end_date <= :endDate or e.end_date is null) order by start_date";

	@PostMapping("/addEvent")
	public EventResponse addEvent(@RequestBody Event event1) {
		EventResponse eventResponse = new EventResponse();
		try {
			if (event1.getUserId() != null) {
				Event savedEvent = eventRepository.save(event1);
				eventResponse.setId(savedEvent.getEventId());
				eventResponse.setHttpCode(HttpStatus.OK.value());
				eventResponse.setSuccessMessage("Event created successfully");
			} else {
				eventResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				eventResponse.setSuccessMessage("Invalid userId");
			}
		} catch (DataIntegrityViolationException e) {
			eventResponse.setFailureReason("Event already present");
			eventResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return eventResponse;
	}

	@PutMapping("/updateEvent")
	public EventResponse updateEvent(@RequestBody Event event) {
		EventResponse eventResponse = new EventResponse();
		Event event1 = eventRepository.findByEventId(event.getEventId());
		if (event1.getUserId().equals(event.getUserId())) {
			event1.setEndDate(event.getEndDate());
			event1.setStartDate(event.getStartDate());
			event1.setEventDescription(event.getEventDescription());
			event1.setEventName(event.getEventName());
			event1.setReferenceNumber(event.getReferenceNumber());
			eventRepository.save(event1);
			eventResponse.setHttpCode(HttpStatus.OK.value());
			eventResponse.setSuccessMessage("Event updated successfully");
		} else {
			eventResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			eventResponse.setSuccessMessage("Invalid userId");
		}
		return eventResponse;
	}

	@DeleteMapping("/deleteEvent")
	public EventResponse deleteEvent(@RequestParam Long eventId) {
		EventResponse eventResponse = new EventResponse();
		Event event = eventRepository.findOne(eventId);
		if (event != null) {
			eventRepository.delete(event);
			eventResponse.setHttpCode(HttpStatus.OK.value());
			eventResponse.setSuccessMessage("Event deleted successfully");
		} else {
			eventResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			eventResponse.setSuccessMessage("No event with Id exists");
		}

		return eventResponse;
	}

	@GetMapping("/getEvent")
	public Event getEvent(@RequestParam Long eventId) {
		Event event1 = eventRepository.findOne(eventId);
		return event1;
	}

	@GetMapping("/eventList")
	public List<Event> listEvents(@RequestParam Long userId, @RequestParam String startDate,
			@RequestParam String endDate) throws ParseException {
		List<String> roles = findUserRole(userId);
		List<Event> events = new ArrayList<>();
		if (startDate != null && endDate != null) {
			startDate += " 00:00:00";
			endDate += " 23:00:00";
		}
		if (!roles.contains(UserRole.ADMIN.name())) {
			events = findEventByUser(userId, startDate, startDate);
			events.addAll(eventRepository.findByUserId(userId));
		} else {
			if (startDate != null && endDate != null) {
				events = eventRepository.findByUserIdAndStartDateAndEndDate(userId, startDate, endDate);
				return events;
			}
			events = eventRepository.findByUserId(userId);
		}
		return events;
	}

	private List<Event> findEventByUser(Long userId, String startDate, String endDate) {
		Session session = sessionFactory.openSession();
		String sql = FETCH_EVENTS_BY_USER;
		if (startDate != null && endDate != null) {
			sql = FETCH_EVENTS_BY_DATE;
		}
		Query query = session.createSQLQuery(sql);
		query.setLong("userId", userId);
		if (startDate != null && endDate != null) {
			query.setString("startDate", startDate);
			query.setString("endDate", endDate);
		}

		List<Object[]> rows = (List<Object[]>) query.list();
		List<Event> events = new ArrayList<>();
		for (Object[] row : rows) {
			Event e = new Event();
			e.setEventId(Long.parseLong(row[0].toString()));
			e.setEventName(row[1] != null ? row[1].toString() : null);
			e.setEventDescription(row[2] != null ? row[2].toString() : null);
			e.setStartDate(row[3] != null ? (java.util.Date) row[3] : null);
			e.setEndDate(row[4] != null ? (java.util.Date) row[4] : null);
			e.setReferenceNumber(row[6] != null ? row[6].toString() : null);
			events.add(e);
		}
		session.close();
		return events;
	}

	private List<String> findUserRole(Long userId) {
		Session session = sessionFactory.openSession();
		List<String> list = new ArrayList<>();
		Query query = session.createSQLQuery(FETCH_USER_ROLE);
		query.setLong("userId", userId);
		List<String> rows = (List<String>) query.list();
		for (String role : rows) {
			list.add(role);
		}
		session.close();
		return list;
	}
}
