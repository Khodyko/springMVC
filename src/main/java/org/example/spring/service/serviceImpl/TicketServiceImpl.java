package org.example.spring.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.TicketXmlConverter;
import org.example.spring.dao.ExceptionDao.DaoException;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.dao.daoImpl.UserDaoImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.service.ServiceException.ServiceException;
import org.example.spring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.logging.log4j.Level.DEBUG;

public class TicketServiceImpl implements TicketService {
    private TicketDaoImpl ticketDaoImpl;
    private UserDaoImpl userDaoImpl;
    private EventDaoImpl eventDaoImpl;
    private TicketXmlConverter ticketXmlConverter;

    @Autowired
    public TicketServiceImpl(TicketDaoImpl ticketDaoImpl, UserDaoImpl userDaoImpl,
                             EventDaoImpl eventDaoImpl, TicketXmlConverter ticketXmlConverter) {
        this.ticketDaoImpl = ticketDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.eventDaoImpl = eventDaoImpl;
        this.ticketXmlConverter = ticketXmlConverter;

    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) throws ServiceException {
        UserEntity userEntity = (UserEntity) userDaoImpl.getUserById(userId);
        EventEntity eventEntity = (EventEntity) eventDaoImpl.getEventById(eventId);
        if (userEntity == null) {
            throw new ServiceException("there are no user with id=" + userId);
        }
        if (eventEntity == null) {
            throw new ServiceException("there are no event with id=" + eventId);
        }
        List<Ticket> bookedTickets = getBookedTickets(eventEntity, Integer.MAX_VALUE, 0); // pageNum 0? or 1?
        for (int i = 0; i < bookedTickets.size(); i++) {
            if (bookedTickets.get(i).getPlace() == place) {
                throw new ServiceException("place №" + place + " is already booked");
            }
        }
        return ticketDaoImpl.saveBookedTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws ServiceException {
        //user doesn't contain id
        Set<Long> usersIdSet = userDaoImpl.getUsersByNameAndEmail(user);
        try {
            return ticketDaoImpl.getBookedTicketsByUser(usersIdSet, pageSize, pageNum);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws ServiceException {
        //event doesn't contain id
        Set<Long> eventsIdSet = eventDaoImpl.getEventsByTitleAndDay(event);

        try {
            return ticketDaoImpl.getBookedTickets(eventsIdSet, pageSize, pageNum);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketDaoImpl.cancelTicket(ticketId);
    }

    @Override
    public Ticket getTicketById(long id) {
        return ticketDaoImpl.getTicketById(id);
    }

    public void preloadTickets(String ticketPreloadXMLPath) throws ServiceException {
        //Batch it!
        System.out.println("service!!!!!");
        try {
            List<Ticket> ticketList = (List<Ticket>) ticketXmlConverter.convertFromXMLToObject(ticketPreloadXMLPath);
            System.out.println("service ticketList= "+ticketList);
        } catch (IOException e) {
           throw new ServiceException("Can't read xml file");
        }

    }
}
