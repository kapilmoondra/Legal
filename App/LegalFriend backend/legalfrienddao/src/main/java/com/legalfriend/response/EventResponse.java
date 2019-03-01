package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.Event;

public class EventResponse extends LegalFriendResponse {

	private Set<Event> events;

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
