package org.example.spring.service;

import org.example.spring.exception.FacadeException;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.exception.ServiceException;

import java.util.List;

/**
 * Realise business logic of tickets and connects to DAO layer.
 *
 * @author Igor Khodyko
 * @see org.example.spring.exception.ApplicationExceptionHandler
 */
public interface TicketService {

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws ServiceException
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) throws ServiceException;

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     * @throws ServiceException
     */
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws ServiceException;

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     * @throws ServiceException
     */
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws ServiceException;

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId);

    /**
     * Return ticket, that have equal id.
     *
     * @return ticket entity.
     */
    public Ticket getTicketById(long id);
}
