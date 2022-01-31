package org.example.spring.dao.daoImpl;

import org.example.spring.Storage;
import org.example.spring.dao.EventDao;
import org.example.spring.exception.DaoException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class EventDaoImpl implements EventDao {
    private Storage storage;
    private ValidatorDao validatorDao;


    public EventDaoImpl() {
    }


    public ValidatorDao getValidatorDao() {
        return validatorDao;
    }

    public void setValidatorDao(ValidatorDao validatorDao) {
        this.validatorDao = validatorDao;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }


    @Override
    public Event getEventById(long eventId) {

        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        return eventEntityMap.getOrDefault("event:" + eventId, null);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        if (validatorDao.validateListForPage(pageSize, pageNum)) {
            for (Map.Entry<String, EventEntity> entry : eventEntityMap.entrySet()) {
                if (entry.getValue().getTitle().equals(title)) {
                    eventList.add(entry.getValue());
                }
            }
            return getPagedList(eventList, pageSize, pageNum);
        }
        return null;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) throws DaoException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM-HH");
        List<Event> eventList = new ArrayList<>();
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        if (validatorDao.validateListForPage(pageSize, pageNum)) {
            for (Map.Entry<String, EventEntity> entry : eventEntityMap.entrySet()) {
                if (entry.getValue().getDate().compareTo(day) == 0) {
                    eventList.add(entry.getValue());
                }
            }
            System.out.println(getPagedList(eventList, pageSize, pageNum).size());
            return getPagedList(eventList, pageSize, pageNum);
        }
        return null;
    }

    @Override
    public Event saveEvent(Event event) {
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        long eventId = 0;
        for (Map.Entry<String, EventEntity> entry : eventEntityMap.entrySet()) {
            if (entry.getValue().getId() >= eventId) {
                eventId = entry.getValue().getId() + 1;
            }
        }
        event.setId(eventId);
        eventEntityMap.put("event:" + event.getId(), (EventEntity) event);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        if (eventEntityMap.containsKey("event:" + event.getId())) {
            eventEntityMap.put("event:" + event.getId(), (EventEntity) event);
            return event;
        }
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        System.out.println("we find: " + this.getEventById(eventId));
        return eventEntityMap.remove("event:" + eventId, this.getEventById(eventId));
    }

    //used for search ticket by event params,when event doesn't contain id
    @Override
    public Set<Long> getEventsByTitleAndDay(Event event) {
        Set<Long> eventIdSet = new HashSet<>();
        Map<String, EventEntity> eventEntityMap = storage.getEventMap();
        for (Map.Entry<String, EventEntity> entry : eventEntityMap.entrySet()) {

            if (entry.getValue().getTitle().equals(event.getTitle()) &&
                    entry.getValue().getDate().compareTo(event.getDate()) == 0) {
                eventIdSet.add(entry.getValue().getId());
            }
        }
        return eventIdSet;
    }

    private List<Event> getPagedList(List<Event> eventList, Integer pageSize, Integer pageNum) {
        return eventList.stream().
                skip(pageSize * pageNum).
                limit(pageSize * pageNum + pageSize).
                collect(Collectors.toList());
    }
}
