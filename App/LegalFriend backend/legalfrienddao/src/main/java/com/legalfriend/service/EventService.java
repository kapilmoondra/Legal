package com.legalfriend.service;

import com.legalfriend.entities.Event;

public interface EventService {

	void addEvent(Event event1);

	void updateEvent(Event event);

	void DeleteEvent(Long eventId);

	Event getEvent(Long eventId);

}
