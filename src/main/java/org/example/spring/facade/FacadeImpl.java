package org.example.spring.facade;


import org.example.spring.exception.FacadeException;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.exception.ServiceException;
import org.example.spring.service.serviceImpl.EventServiceImpl;
import org.example.spring.service.serviceImpl.TicketServiceImpl;
import org.example.spring.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;


public class FacadeImpl implements BookingFacade {

    private EventServiceImpl eventServiceImpl;
    private TicketServiceImpl ticketServiceImpl;
    private UserServiceImpl userServiceImpl;
    @Value("${ticket.preload.xml.path}")
    private String ticketPreloadXMLPath;

    public FacadeImpl() {
    }

    @Autowired
    public FacadeImpl(EventServiceImpl eventServiceImpl, TicketServiceImpl ticketServiceImpl,
                      UserServiceImpl userServiceImpl) {
        this.eventServiceImpl = eventServiceImpl;
        this.ticketServiceImpl = ticketServiceImpl;
        this.userServiceImpl = userServiceImpl;

    }

    @Override
    public Event getEventById(long eventId) {
        return eventServiceImpl.getEventById(eventId);
    }


    @Override
    public List<Event> getEventsByTitle(String title, int pageSize,
                                        int pageNum) throws FacadeException {
        try {
            return eventServiceImpl.getEventsByTitle(title, pageSize, pageNum);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws FacadeException {
        try {
            return eventServiceImpl.getEventsForDay(day, pageSize, pageNum);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }


    }

    @Override
    public Event createEvent(Event event) {
        return eventServiceImpl.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventServiceImpl.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventServiceImpl.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId) {

        return userServiceImpl.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userServiceImpl.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize,
                                     int pageNum) throws FacadeException {
        try {
            return userServiceImpl.getUsersByName(name, pageSize, pageNum);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }

    }

    @Override
    public User createUser(User user) {
        return userServiceImpl.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userServiceImpl.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {

        return userServiceImpl.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place,
                             Ticket.Category category) throws FacadeException {
        try {
            return ticketServiceImpl.bookTicket(userId, eventId, place, category);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }

    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws FacadeException {
        try {
            return ticketServiceImpl.getBookedTickets(user, pageSize, pageNum);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }

    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws FacadeException {
        try {
            return ticketServiceImpl.getBookedTickets(event, pageSize, pageNum);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }
    }

    public Ticket getTicketById(long id) {

        return ticketServiceImpl.getTicketById(id);
    }

    @Override
    public boolean cancelTicket(long ticketId) {

        return ticketServiceImpl.cancelTicket(ticketId);
    }

    //add to interfaces this methods?
    public void preloadTickets() throws FacadeException {
        System.out.println("facade!!!!!");
        try {
            ticketServiceImpl.preloadTickets(ticketPreloadXMLPath);
        } catch (ServiceException e) {
            throw new FacadeException(e.getMessage(), e );
        }
    }
}
