package org.example.spring.service;

import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.service.ServiceException.ServiceException;

import java.util.List;


public interface TicketService {


    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) throws ServiceException;

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws ServiceException;

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws ServiceException;

    public boolean cancelTicket(long ticketId);

    public Ticket getTicketById(long id);
}
