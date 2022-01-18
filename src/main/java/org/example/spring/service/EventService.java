package org.example.spring.service;

import org.example.spring.model.Event;
import org.example.spring.service.ServiceException.ServiceException;

import java.util.Date;
import java.util.List;

public interface EventService {
    public Event getEventById(long eventId);

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws ServiceException;

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws ServiceException;

    public Event createEvent(Event event);

    public Event updateEvent(Event event);

    public boolean deleteEvent(long eventId);
}
