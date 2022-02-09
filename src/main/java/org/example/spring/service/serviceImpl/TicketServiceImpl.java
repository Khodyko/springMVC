package org.example.spring.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.daoImpl.EventDaoImpl;
import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.dao.daoImpl.UserDaoImpl;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.example.spring.service.TicketService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

public class TicketServiceImpl implements TicketService {

    private TicketDaoImpl ticketDaoImpl;
    private UserDaoImpl userDaoImpl;
    private EventDaoImpl eventDaoImpl;
    private SimpleJobLauncher jobLauncher;
    private Job firstBatchJob;
    private final static Logger logger = LogManager.getLogger(TicketServiceImpl.class.getName());

    @Autowired
    public TicketServiceImpl(TicketDaoImpl ticketDaoImpl, UserDaoImpl userDaoImpl,
                             EventDaoImpl eventDaoImpl, SimpleJobLauncher jobLauncher,
                             Job firstBatchJob) {
        this.ticketDaoImpl = ticketDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.eventDaoImpl = eventDaoImpl;
        this.jobLauncher = jobLauncher;
        this.firstBatchJob = firstBatchJob;
        logger.log(DEBUG, "created");

    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category)
                             throws ServiceException {
        logger.log(DEBUG, "started.");
        UserEntity userEntity = (UserEntity) userDaoImpl.getUserById(userId);
        EventEntity eventEntity = (EventEntity) eventDaoImpl.getEventById(eventId);
        ServiceException ex;
        if (userEntity == null) {
            ex = new ServiceException("there are no user with id=" + userId);
            logger.log(WARN, ex.getMessage());
            throw ex;
        }
        if (eventEntity == null) {
            ex = new ServiceException("there are no user with id=" + eventId);
            logger.log(WARN, ex.getMessage());
            throw ex;
        }
        List<Ticket> bookedTickets = getBookedTickets(eventEntity, Integer.MAX_VALUE, 0);
        for (int i = 0; i < bookedTickets.size(); i++) {
            if (bookedTickets.get(i).getPlace() == place) {
                ex = new ServiceException("place №" + place + " is already booked");
                logger.log(WARN, ex.getMessage());
                throw ex;
            }
        }
        return ticketDaoImpl.saveBookedTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws ServiceException {
        logger.log(DEBUG, "started.");
        Set<Long> usersIdSet = userDaoImpl.getUsersByNameAndEmail(user);
        try {
            return ticketDaoImpl.getBookedTicketsByUser(usersIdSet, pageSize, pageNum);
        } catch (DaoException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws ServiceException {
        logger.log(DEBUG, "started.");
        Set<Long> eventsIdSet = eventDaoImpl.getEventsByTitleAndDay(event);

        try {
            return ticketDaoImpl.getBookedTicketsByEvent(eventsIdSet, pageSize, pageNum);
        } catch (DaoException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.log(DEBUG, "started.");
        return ticketDaoImpl.cancelTicket(ticketId);
    }

    @Override
    public Ticket getTicketById(long id) {
        logger.log(DEBUG, "started.");
        return ticketDaoImpl.getTicketById(id);
    }


    public void preloadTickets() throws ServiceException {
        logger.log(DEBUG, "started.");
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(firstBatchJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } catch (JobParametersInvalidException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } catch (JobRestartException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
