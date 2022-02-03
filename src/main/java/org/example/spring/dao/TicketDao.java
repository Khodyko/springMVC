package org.example.spring.dao;

import org.example.spring.exception.DaoException;
import org.example.spring.model.Ticket;

import java.util.List;
import java.util.Set;

public interface TicketDao {
    public Ticket saveBookedTicket(long userId, long eventId, int place, Ticket.Category category);

    public List<Ticket> getBookedTicketsByUser(Set<Long> userIdSet , int pageSize, int pageNum) throws DaoException;

    public List<Ticket> getBookedTicketsByEvent(Set<Long> eventIdSet, int pageSize, int pageNum) throws DaoException;

    public boolean cancelTicket(long ticketId);

    public Ticket getTicketById(long id);
}
