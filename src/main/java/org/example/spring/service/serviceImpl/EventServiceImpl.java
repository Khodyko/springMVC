package org.example.spring.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.ExceptionDao.DaoException;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.model.Event;
import org.example.spring.service.EventService;
import org.example.spring.service.ServiceException.ServiceException;

import java.util.Date;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;


public class EventServiceImpl implements EventService {
    private EventDaoImpl eventDaoImpl;


    public EventServiceImpl(EventDaoImpl eventDaoImpl) {
        this.eventDaoImpl = eventDaoImpl;

    }

    @Override
    public Event getEventById(long eventId) {
        return eventDaoImpl.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws ServiceException {
        try {
            return eventDaoImpl.getEventsByTitle(title, pageSize, pageNum);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws ServiceException {
        try {
            return eventDaoImpl.getEventsForDay(day, pageSize, pageNum);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Event createEvent(Event event) {
        return eventDaoImpl.saveEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventDaoImpl.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDaoImpl.deleteEvent(eventId);
    }
}
