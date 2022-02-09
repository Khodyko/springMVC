package org.example.spring.dao;

import org.example.spring.exception.DaoException;
import org.example.spring.model.Event;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Realise connection to database for work with Event objects.
 *
 * @author Igor Khodyko
 */
public interface EventDao {
    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param eventId Id of event.
     * @return Event.
     */
    public Event getEventById(long eventId);

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     * @throws DaoException
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws DaoException;

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     * @throws DaoException
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws DaoException;

    /**
     * Generates new id for event. Save new event.
     *
     * @param event
     * @return Created Event object.
     */
    public Event saveEvent(Event event);

    /**
     * Updates event using given data.
     *
     * @param event
     * @return Updated Event object.
     */
    public Event updateEvent(Event event);

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Updated Event object.
     */
    public boolean deleteEvent(long eventId);

    /**
     * Returns set of ids of events, that have equal title and date.
     *
     * @param event (contains title and date)
     * @return Set of ids of events from database.
     */
    public Set<Long> getEventsByTitleAndDay(Event event);

}
