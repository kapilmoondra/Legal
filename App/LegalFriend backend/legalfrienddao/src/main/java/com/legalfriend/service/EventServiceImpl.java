package com.legalfriend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.legalfriend.entities.Event;
import com.legalfriend.repository.EventRepository;


public class EventServiceImpl implements EventService{
	
	@Autowired
    private EventRepository eventRepository;
	
	@Override
	public void addEvent(Event event1)
	{
    	eventRepository.save(event1);
    	System.out.println("***successfully added**");
    	
	}
	
	@Override
	public void updateEvent(Event event)
	{
//		Event event1 = eventRepository.findOne(event.getEventId());
		eventRepository.save(event);
		System.out.println("***successfully updated**");
		
		
	}
	
	@Override
	public void DeleteEvent(Long eventId)
	{
		Event event = eventRepository.findOne(eventId);
		if (event != null )
		eventRepository.delete(event);
		System.out.println("***successfully deleted**");
		
	}
	
	@Override
    public Event getEvent( Long eventId)
	{
		Event event1 = eventRepository.findOne(eventId);
		System.out.println("date ===="+ event1.getStartDate());
		return event1;
		
	}

}
