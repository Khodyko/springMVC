package org.example.spring.dao;

import org.example.spring.dao.ExceptionDao.DaoException;
import org.example.spring.model.Event;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface EventDao {

    public Event getEventById(long eventId);

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws DaoException;

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws DaoException;

    public Event saveEvent(Event event);

    public Event updateEvent(Event event);

    public boolean deleteEvent(long eventId);

    public Set<Long> getEventsByTitleAndDay(Event event);

}
