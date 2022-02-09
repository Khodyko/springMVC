package org.example.spring.dao.daoImpl;

import junit.framework.TestCase;
import org.example.spring.Storage;
import org.example.spring.exception.DaoException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoImplTest extends TestCase {
    @Mock
    private ValidatorDao validatorDao;
    @Mock
    private Storage storage;
    @InjectMocks
    private EventDaoImpl eventDaoImpl;

    @Test
    public void testGetEventById() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        EventEntity eventFromMock = (EventEntity) eventDaoImpl.getEventById(12);
        assertEquals(eventFromMock, eventEntity);
    }

    @Test
    public void testGetEventsByTitle() throws DaoException {
        Date day = new Date(System.currentTimeMillis());
        EventEntity eventEntity = new EventEntity(12, "title12", day);
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        List<Event> eventList ;
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        when(validatorDao.validateListForPage(any(Integer.class), any(Integer.class))).thenReturn(true);
        eventList = eventDaoImpl.getEventsByTitle("title12", 100, 0);
        assert (eventList.get(0).equals(eventEntity));
    }


    @Test
    public void testGetEventsForDay() throws DaoException {
        Date day = new Date(System.currentTimeMillis());
        EventEntity eventEntity = new EventEntity(12, "title12", day);
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        List<Event> eventList = new ArrayList<>();
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        when(validatorDao.validateListForPage(any(Integer.class), any(Integer.class))).thenReturn(true);
        eventList = eventDaoImpl.getEventsByTitle("title12", 100, 0);
        assert (eventList.get(0).equals(eventEntity));
    }

    @Test
    public void testSaveEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        EventEntity eventFromMock = (EventEntity) eventDaoImpl.saveEvent(eventEntity);
        assertEquals(eventFromMock, eventEntity);
    }

    @Test
    public void testUpdateEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        EventEntity eventFromMock = (EventEntity) eventDaoImpl.updateEvent(eventEntity);
        assertEquals(eventFromMock, eventEntity);
    }

    @Test
    public void testDeleteEvent() {
        EventEntity eventEntity = new EventEntity(12, "title12",
                new Date(System.currentTimeMillis()));
        Map<String, EventEntity> eventEntityMap = new HashMap<>();
        eventEntityMap.put("event:" + eventEntity.getId(), eventEntity);
        System.out.println("test eventEntityMap" + eventEntityMap);
        when(storage.getEventMap()).thenReturn(eventEntityMap);
        Boolean isEventDeleted = eventDaoImpl.deleteEvent(12);
        assertTrue(isEventDeleted);
    }
}