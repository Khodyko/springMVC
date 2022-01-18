package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.example.spring.dao.ExceptionDao.DaoException;
import org.example.spring.dao.TicketDao;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.Level.DEBUG;

public class TicketDaoImpl implements TicketDao {
    private Storage storage;
    private ValidatorDao validatorDao;

    public TicketDaoImpl() {

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
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws DaoException {
        List<Ticket> ticketList = new ArrayList<>();
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getUserId() == user.getId()) {
                ticketList.add(entry.getValue());
            }
            if (validatorDao.validateListForPage(ticketList.size(), pageSize, pageNum)) {
                return getPagedList(ticketList, pageSize, pageNum);
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws DaoException {
        List<Ticket> ticketList = new ArrayList<>();
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getEventId() == event.getId()) {
                ticketList.add(entry.getValue());
            }
            if (validatorDao.validateListForPage(ticketList.size(), pageSize, pageNum)) {
                return getPagedList(ticketList, pageSize, pageNum);
            }
        }
        return null;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
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
        Map<String, TicketEntity> ticketEntityMap = storage.getTicketMap();
        for (Map.Entry<String, TicketEntity> entry : ticketEntityMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }

    private List<Ticket> getPagedList(List<Ticket> ticketList, Integer pageSize, Integer pageNum) {
        return ticketList.stream()
                .skip(pageSize * pageNum)
                .limit(pageNum + 1)
                .collect(Collectors.toList());
    }

}
