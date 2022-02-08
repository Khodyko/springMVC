package org.example.spring.dao;

import org.example.spring.exception.DaoException;
import org.example.spring.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Realise connection to database for work with Ticket objects.
 *
 * @author Igor Khodyko
 */
public interface TicketDao {
    /**
     * Save into database ticket for a specified event on behalf of specified user.
     *
     * @param userId
     * @param eventId
     * @param place
     * @param category
     * @return Saved ticket object.
     */
    public Ticket saveBookedTicket(long userId, long eventId, int place, Ticket.Category category);

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order
     *
     * @param userIdSet Set of user Id.
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     * @throws DaoException
     */
    public List<Ticket> getBookedTicketsByUser(Set<Long> userIdSet , int pageSize, int pageNum) throws DaoException;

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param eventIdSet Set of event Id.
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     * @throws DaoException
     */
    public List<Ticket> getBookedTicketsByEvent(Set<Long> eventIdSet, int pageSize, int pageNum) throws DaoException;

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
     * @param id
     * @return ticket entity.
     */
    public Ticket getTicketById(long id);
}
