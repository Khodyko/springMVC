package org.example.spring.service;

import org.example.spring.exception.ServiceException;
import org.example.spring.model.Event;

import java.util.Date;
import java.util.List;

/**
 * Realise business logic of events and connects to DAO layer.
 *
 * @author Igor Khodyko
 */
public interface EventService {
    /**
     * Gets event by id.
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
     * @throws ServiceException
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws ServiceException;

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     * @throws ServiceException
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws ServiceException;

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event);

    /**
     * Updates event using given data.
     *
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event);

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId);
}
