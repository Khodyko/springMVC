package org.example.spring.facade;

import junit.framework.TestCase;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.FacadeException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.service.serviceImpl.EventServiceImpl;
import org.example.spring.service.serviceImpl.TicketServiceImpl;
import org.example.spring.service.serviceImpl.UserServiceImpl;
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
public class FacadeImplTest extends TestCase {

    @Mock
    private EventServiceImpl eventService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private TicketServiceImpl ticketService;
    @InjectMocks
    private FacadeImpl facade;

    @Test
    public void getEventById() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventService.getEventById(12)).thenReturn(eventEntity);
        assert (facade.getEventById(12).equals(eventEntity));
    }

    @Test
    public void getEventsByTitle() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        List<Event> eventList=new ArrayList<>();
        eventList.add(eventEntity);
        try {
            when(eventService.getEventsByTitle("title12", 100,0)).thenReturn(eventList);
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
        try {
            assert (facade.getEventsByTitle("title12", 100,0).get(0).equals(eventEntity));
        }
        catch (FacadeException e) {
            //fix me
            e.printStackTrace();
        }
    }

    @Test
    public void getEventsForDay() {
        Date day = new Date(System.currentTimeMillis());
        EventEntity eventEntity = new EventEntity(12, "title12", day);

        List<Event> eventEntityList = new ArrayList<>();
        eventEntityList.add(eventEntity);
        try {
            when(eventService.getEventsForDay(day, 100, 0)).thenReturn(eventEntityList);
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
        List<Event> eventsFromMock= null;
        try {
            eventsFromMock = facade.getEventsForDay(day, 100,0);
        } catch (FacadeException e) {
            //fix me
            e.printStackTrace();
        }
        assert(eventsFromMock.get(0).getDate().equals(day));

    }

    @Test
    public void createEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventService.createEvent(eventEntity)).thenReturn(eventEntity);
        EventEntity eventEntityFromMock = (EventEntity) facade.createEvent(eventEntity);
        assertNotNull(eventEntityFromMock);

    }

    @Test
    public void updateEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventService.updateEvent(eventEntity)).thenReturn(eventEntity);
        EventEntity eventEntityFromMock = (EventEntity) facade.updateEvent(eventEntity);
        assertNotNull(eventEntityFromMock);

    }

    @Test
    public void deleteEvent() {
        when(eventService.deleteEvent(12)).thenReturn(true);
        when(eventService.deleteEvent(1)).thenReturn(false);
        assert(facade.deleteEvent(12));
        assert(!facade.deleteEvent(1));
    }

    @Test
    public void getUserById() {
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        when(userService.getUserById(12)).thenReturn(userEntity);
        assert(facade.getUserById(12).getId()==12);

    }

    @Test
    public void getUserByEmail() {
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        when(userService.getUserByEmail("sergei@mail.ru")).thenReturn(userEntity);
        assert(facade.getUserByEmail("sergei@mail.ru").getEmail().equals("sergei@mail.ru"));


    }

    @Test
    public void getUsersByName() {
        List<User> userList=new ArrayList<>();
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        userList.add(userEntity);
        try {
            when(userService.getUsersByName("Sergei", 100, 0)).thenReturn(userList);
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
        List<User> usersFromMock= null;
        try {
            usersFromMock = facade.getUsersByName("Sergei", 100, 0);
        } catch (FacadeException e) {
            //fix me
            e.printStackTrace();
        }
        assertNotNull(usersFromMock.get(0));
        assert(usersFromMock.size()>0);
    }

    @Test
    public void createUser() {
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        when(userService.createUser(userEntity)).thenReturn(userEntity);
        assert(facade.createUser(userEntity).equals(userEntity));

    }

    @Test
    public void updateUser() {
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        when(userService.updateUser(userEntity)).thenReturn(userEntity);
        assert(facade.updateUser(userEntity).equals(userEntity));

    }

    @Test
    public void deleteUser() {
        UserEntity userEntity=new UserEntity(12,"Sergei", "sergei@mail.ru");
        when(userService.updateUser(userEntity)).thenReturn(userEntity);
        assert(facade.updateUser(userEntity).equals(userEntity));
    }

    @Test
    public void bookTicket() throws ServiceException {
        TicketEntity ticketEntity = new TicketEntity( 12, 12, Ticket.Category.BAR, 12);
        UserEntity userEntity=new UserEntity("Sergei", "sergei@mail.ru");
        EventEntity eventEntity=new EventEntity("title12",new Date(System.currentTimeMillis()));
        when(ticketService.bookTicket(12, 12, 12, Ticket.Category.BAR)).thenReturn(ticketEntity);
        try {
            assertNotNull (facade.bookTicket(12, 12, 12, Ticket.Category.BAR));
        }
        catch (FacadeException e){
            //fixme
            e.printStackTrace();
        }
    }

    @Test
    public void getBookedTicketsByEvent() {
        EventEntity eventEntity=new EventEntity("title12",new Date(System.currentTimeMillis()));
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        List<Ticket> ticketList=new ArrayList<>();
        ticketList.add(ticketEntity);

        try {
            when(ticketService.getBookedTickets(eventEntity, 100, 0)).thenReturn(ticketList);
        } catch (ServiceException e) {
            //fixme
            e.printStackTrace();
        }
        try {
            assert (facade.getBookedTickets(eventEntity, 100, 0).get(0).equals(ticketEntity));
        } catch (FacadeException e) {
            //fix me
            e.printStackTrace();
        }
    }

    @Test
    public void getBookedTicketsByUsers() {
        UserEntity userEntity=new UserEntity("Sergei", "sergei@mail.ru");
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        List<Ticket> ticketList=new ArrayList<>();
        ticketList.add(ticketEntity);
        try {
            when(ticketService.getBookedTickets(userEntity, 100, 0)).thenReturn(ticketList);
        } catch (ServiceException e) {
            //fix me
            e.printStackTrace();
        }
        try {
            assert (facade.getBookedTickets(userEntity, 100, 0).get(0).equals(ticketEntity));
        } catch (FacadeException e) {
            //fix me
            e.printStackTrace();
        }
    }

    @Test
    public void getTicketById() {
        TicketEntity ticketEntity=new TicketEntity(12,12,12, Ticket.Category.BAR, 12);
        when(ticketService.getTicketById(12)).thenReturn(ticketEntity);
        assert (facade.getTicketById(12).equals(ticketEntity));

    }

    @Test
    public void cancelTicket() {
        when(ticketService.getTicketById(12)).thenReturn(null);
        assert (facade.getTicketById(12)==null);

    }

}