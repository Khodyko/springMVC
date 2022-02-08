package org.example.spring.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.Event;
import org.example.spring.service.EventService;

import java.util.Date;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;


public class EventServiceImpl implements EventService {
    private EventDaoImpl eventDaoImpl;
    private final static Logger logger = LogManager.getLogger(EventServiceImpl.class.getName());


    public EventServiceImpl(EventDaoImpl eventDaoImpl) {
        this.eventDaoImpl = eventDaoImpl;
        logger.log(DEBUG, "created");
    }

    public EventServiceImpl() {
        logger.log(DEBUG, "created");
    }

    @Override
    public Event getEventById(long eventId) {
        return eventDaoImpl.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws ServiceException {
        logger.log(DEBUG, "started.");
        try {
            return eventDaoImpl.getEventsByTitle(title, pageSize, pageNum);
        } catch (DaoException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws ServiceException {
        logger.log(DEBUG, "started.");
        try {
            return eventDaoImpl.getEventsForDay(day, pageSize, pageNum);
        } catch (DaoException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Event createEvent(Event event) {
        logger.log(DEBUG, "started.");
        return eventDaoImpl.saveEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        logger.log(DEBUG, "started.");
        return eventDaoImpl.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        logger.log(DEBUG, "started.");
        return eventDaoImpl.deleteEvent(eventId);
    }
}
