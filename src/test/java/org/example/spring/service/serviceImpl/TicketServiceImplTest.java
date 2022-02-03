package org.example.spring.service.serviceImpl;

import junit.framework.TestCase;

import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.dao.daoImpl.UserDaoImpl;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Ticket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest extends TestCase {
    @Mock
    private TicketDaoImpl ticketDaoImpl;
    @Mock
    private UserDaoImpl userDaoImpl;
    @Mock
    private EventDaoImpl eventDaoImpl;
    @InjectMocks
    private TicketServiceImpl ticketServiceImpl;
    @Test
    public void testBookTicket() {
        TicketEntity ticketEntity = new TicketEntity(12, 12, 12, Ticket.Category.BAR, 12);
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        EventEntity eventEntity=new EventEntity(12,"title12",new Date(System.currentTimeMillis()));
        when(ticketDaoImpl.saveBookedTicket(12, 12, 12, Ticket.Category.BAR)).thenReturn(ticketEntity);
        when(userDaoImpl.getUserById(12)).thenReturn(userEntity);
        when(eventDaoImpl.getEventById(12)).thenReturn(eventEntity);
        try {
            assertNotNull (ticketServiceImpl.bookTicket(12, 12, 12, Ticket.Category.BAR));
        }
        catch (ServiceException e){
            //fixme
            e.printStackTrace();
        }
    }
    @Test
    public void testGetBookedTicketsByEvent() {
        EventEntity eventEntity=new EventEntity("title12",new Date(System.currentTimeMillis()));
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        List<Ticket> ticketList=new ArrayList<>();
        ticketList.add(ticketEntity);
        Set<Long> eventIdSet=new HashSet<>();
        eventIdSet.add(12L);
        when(eventDaoImpl.getEventsByTitleAndDay(eventEntity)).thenReturn(eventIdSet);
        try {
            when(ticketDaoImpl.getBookedTicketsByEvent(eventIdSet, 100, 0)).thenReturn(ticketList);
        } catch (DaoException e) {
            //fixme
            e.printStackTrace();
        }
        try {
            assert (ticketServiceImpl.getBookedTickets(eventEntity, 100, 0).get(0).equals(ticketEntity));
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
    }
    @Test
    public void testGetBookedTicketsByUser() {
        UserEntity userEntity=new UserEntity("Sergei", "sergei@mail.ru");
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        List<Ticket> ticketList=new ArrayList<>();
        ticketList.add(ticketEntity);
        Set<Long> userIdSet=new HashSet<>();
        userIdSet.add(12L);
        when(userDaoImpl.getUsersByNameAndEmail(userEntity)).thenReturn(userIdSet);
        try {
            when(ticketDaoImpl.getBookedTicketsByUser(userIdSet, 100, 0)).thenReturn(ticketList);
        } catch (DaoException e) {
            //fix me
            e.printStackTrace();
        }
        try {
            assert (ticketServiceImpl.getBookedTickets(userEntity, 100, 0).get(0).equals(ticketEntity));
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
    }
    @Test
    public void testCancelTicket() {
        when(ticketDaoImpl.getTicketById(12)).thenReturn(null);
        assert (ticketServiceImpl.getTicketById(12)==null);
    }
    @Test
    public void testGetTicketById() {
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        when(ticketDaoImpl.getTicketById(12)).thenReturn(ticketEntity);
        assert (ticketServiceImpl.getTicketById(12).equals(ticketEntity));
    }
}