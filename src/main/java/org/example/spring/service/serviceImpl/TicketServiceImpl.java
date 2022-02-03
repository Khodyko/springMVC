package org.example.spring.service.serviceImpl;

import org.example.spring.exception.DaoException;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.dao.daoImpl.UserDaoImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.exception.ServiceException;
import org.example.spring.service.TicketService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class TicketServiceImpl implements TicketService {
    private TicketDaoImpl ticketDaoImpl;
    private UserDaoImpl userDaoImpl;
    private EventDaoImpl eventDaoImpl;
    private SimpleJobLauncher jobLauncher;
    private Job firstBatchJob;

    @Autowired
    public TicketServiceImpl(TicketDaoImpl ticketDaoImpl, UserDaoImpl userDaoImpl, EventDaoImpl eventDaoImpl, SimpleJobLauncher jobLauncher, Job firstBatchJob) {
        this.ticketDaoImpl = ticketDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.eventDaoImpl = eventDaoImpl;
        this.jobLauncher = jobLauncher;
        this.firstBatchJob = firstBatchJob;
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
            return ticketDaoImpl.getBookedTicketsByEvent(eventsIdSet, pageSize, pageNum);
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
//        System.out.println("service!!!!!");
//        try {
//            List<Ticket> ticketList = (List<Ticket>) itemReader.read();
//            ticketDaoImpl.replaceTickets(ticketList);
//        } catch (IOException e) {
//           throw new ServiceException("Can't read xml file");
//        } catch (Exception e) {
//            throw new ServiceException("Can't read xml file");
//        }
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(firstBatchJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Exit status: " + execution.getStatus());
    }
}
