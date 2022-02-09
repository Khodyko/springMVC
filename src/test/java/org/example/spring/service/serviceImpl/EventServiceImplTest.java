package org.example.spring.service.serviceImpl;

import junit.framework.TestCase;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest extends TestCase {
    @Mock
    private EventDaoImpl eventDaoImpl;
    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Test
    public void testGetEventById() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventDaoImpl.getEventById(12)).thenReturn(eventEntity);
        assert (eventServiceImpl.getEventById(12).equals(eventEntity));
    }

    @Test
    public void testGetEventsByTitle() throws DaoException, ServiceException {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        List<Event> eventList = new ArrayList<>();
        eventList.add(eventEntity);
        when(eventDaoImpl.getEventsByTitle("title12", 100, 0)).thenReturn(eventList);
        assert (eventServiceImpl.getEventsByTitle("title12", 100, 0).get(0).equals(eventEntity));
    }

    @Test
    public void testGetEventsForDay() throws DaoException, ServiceException {
        Date day = new Date(System.currentTimeMillis());
        EventEntity eventEntity = new EventEntity(12, "title12", day);

        List<Event> eventEntityList = new ArrayList<>();
        eventEntityList.add(eventEntity);
        when(eventDaoImpl.getEventsForDay(day, 100, 0)).thenReturn(eventEntityList);
        List<Event> eventsFromMock = null;
        eventsFromMock = eventServiceImpl.getEventsForDay(day, 100, 0);
        assert (eventsFromMock.get(0).getDate().equals(day));
    }

    @Test
    public void testCreateEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventDaoImpl.saveEvent(eventEntity)).thenReturn(eventEntity);
        EventEntity eventEntityFromMock = (EventEntity) eventServiceImpl.createEvent(eventEntity);
        assertNotNull(eventEntityFromMock);
    }

    @Test
    public void testUpdateEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        when(eventDaoImpl.updateEvent(eventEntity)).thenReturn(eventEntity);
        EventEntity eventEntityFromMock = (EventEntity) eventServiceImpl.updateEvent(eventEntity);
        assertNotNull(eventEntityFromMock);
    }

    @Test
    public void testDeleteEvent() {
        when(eventDaoImpl.deleteEvent(12)).thenReturn(true);
        when(eventDaoImpl.deleteEvent(1)).thenReturn(false);
        assert (eventServiceImpl.deleteEvent(12));
        assert (!eventServiceImpl.deleteEvent(1));
    }
}