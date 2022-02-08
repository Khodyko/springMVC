package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.example.spring.converter.JsonReader;
import org.example.spring.exception.DaoException;
import org.example.spring.dao.TicketDao;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Ticket;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

public class TicketDaoImpl implements TicketDao {
    private Storage storage;
    private ValidatorDao validatorDao;
    private final static Logger logger = LogManager.getLogger(TicketDaoImpl.class.getName());


    public TicketDaoImpl(Storage storage, ValidatorDao validatorDao) {
        this.storage = storage;
        this.validatorDao = validatorDao;
        logger.log(DEBUG, "created");
    }

    public TicketDaoImpl() {
        logger.log(DEBUG, "created");
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
    public Ticket saveBookedTicket(long userId, long eventId, int place, Ticket.Category category) {
        logger.log(DEBUG, "started.");
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        TicketEntity ticket;
        long ticketId = 0;

        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getId() >= ticketId) {
                ticketId = entry.getValue().getId() + 1;
            }
        }
        ticket = new TicketEntity(ticketId, eventId, userId, category, place);
        ticketEntityMap.put("ticket:" + ticketId, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(Set<Long> userIdSet, int pageSize, int pageNum) throws DaoException {
        logger.log(DEBUG, "started.");
        List<Ticket> ticketList = new ArrayList<>();
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        System.out.println("storageSize=" + ticketEntityMap.size());
        if (validatorDao.validateListForPage(pageSize, pageNum)) {
            for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
                System.out.println("entry= " + entry.getValue().getId());
                if (userIdSet.contains(entry.getValue().getUserId())) {
                    ticketList.add(entry.getValue());
                }
            }
            return getPagedList(ticketList, pageSize, pageNum);
        }
        return null;
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(Set<Long> eventsIdSet, int pageSize, int pageNum) throws DaoException {
        logger.log(DEBUG, "started.");
        List<Ticket> ticketList = new ArrayList<>();
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        if (validatorDao.validateListForPage(pageSize, pageNum)) {
            for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
                if (eventsIdSet.contains(entry.getValue().getEventId())) {
                    ticketList.add(entry.getValue());

                }
            }
            return getPagedList(ticketList, pageSize, pageNum);
        }
        return null;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.log(DEBUG, "started.");
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getId() == ticketId) {
                ticketEntityMap.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    @Override
    public Ticket getTicketById(long id) {
        logger.log(DEBUG, "started.");
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }

    private List<Ticket> getPagedList(List<Ticket> ticketList, Integer pageSize, Integer pageNum) {
        logger.log(DEBUG, "started.");
        return ticketList.stream().
                skip(pageSize * pageNum).
                limit(pageSize * pageNum + pageSize).
                collect(Collectors.toList());
    }

    public void replaceTickets(List<TicketEntity> ticketList) {
        logger.log(DEBUG, "started.");
        Map<String, TicketEntity> ticketMapForReplace = new HashMap<>();
        System.out.println("size " + ticketList.size());
        for (int i = 0; i < ticketList.size(); i++) {
            //set id, because ticket haven't id
            ticketList.get(i).setId(i);
            ticketMapForReplace.put("ticket:" + i, ticketList.get(i));
        }
        Map<String, TicketEntity> ticketMapFromStorage = storage.getTicketMap();
        ticketMapFromStorage.clear();
        ticketMapFromStorage.putAll(ticketMapForReplace);
        logger.log(DEBUG, "Ticket map in storage replaced");
    }
}
