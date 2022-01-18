package org.example.spring.dao;

import org.example.spring.dao.ExceptionDao.DaoException;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;

import java.util.List;

public interface TicketDao {
    public Ticket saveBookedTicket(long userId, long eventId, int place, Ticket.Category category);

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws DaoException;

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws DaoException;

    public boolean cancelTicket(long ticketId);

    public Ticket getTicketById(long id);
}
