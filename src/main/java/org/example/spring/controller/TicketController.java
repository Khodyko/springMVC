package org.example.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.exception.ApplicationException;
import org.example.spring.exception.FacadeException;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The controller of tickets
 *
 * @author: Igor Khodyko
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private FacadeImpl facade;
    private final static Logger logger = LogManager.getLogger(TicketController.class.getName());

    public TicketController() {
        logger.log(DEBUG, "created");
    }

    @Autowired
    public TicketController(FacadeImpl facade) {
        this.facade = facade;
    }

    /**
     * This method creates ticket by userId, eventId,
     * place, category uses BookingFacade
     *
     * @param userId
     * @param eventId
     * @param place
     * @param category
     * @return ticket view
     * @return attribute(" ticket ", Ticket)
     * @throws ApplicationException positive responseStatus HttpStatus.Ok
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView bookTicket(@RequestParam("userId") long userId,
                                   @RequestParam("eventId") long eventId,
                                   @RequestParam("place") int place,
                                   @RequestParam("category") Ticket.Category category) throws ApplicationException {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("ticket");
        try {
            modelAndView.addObject("ticket", facade.bookTicket(userId, eventId, place, category));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     * This method units name and email into User and send
     * it to BookingFacade, returns ticketList(paged),
     * in case if exist ticket booked by this user
     *
     * @param name
     * @param email
     * @param pageSize
     * @param pageNum
     * @return ticket page
     * @return attribute(" tickets ", List < Ticket >)
     * @throws ApplicationException positive responseStatus HttpStatus.Ok
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = {"name", "email", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getBookedTickets(@RequestParam(value = "name") String name,
                                         @RequestParam(value = "email") String email,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) throws ApplicationException {
        logger.log(DEBUG, "started.");
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("ticket");
        try {
            modelAndView.addObject("tickets", facade.getBookedTickets(user, pageSize, pageNum));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     * This method units title and day into Event and send
     * it to BookingFacade, returns ticketList(paged),
     * in case if exist ticket booked on this event
     *
     * @param title
     * @param day
     * @param pageSize
     * @param pageNum
     * @return ticket page
     * @return attribute(" tickets ", List < Ticket >)
     * @throws ApplicationException positive responseStatus HttpStatus.Ok
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = {"title", "day", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getBookedTickets(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "day") Date day,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) throws ApplicationException {
        logger.log(DEBUG, "started.");
        Event event = new EventEntity(title, day);
        ModelAndView modelAndView = new ModelAndView("ticket");
        try {
            modelAndView.addObject("tickets", facade.getBookedTickets(event, pageSize, pageNum));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     * This method returns ticket by id from BookingFacade
     *
     * @param ticketId
     * @return ticket page
     * @return attribute(" ticket ", Ticket)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = "ticketId")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getTicketById(@RequestParam("ticketId") long ticketId) {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("ticket");
//        facade.preloadTickets();
        modelAndView.addObject("ticket", facade.getTicketById(ticketId));
        return modelAndView;
    }

    /**
     * This method deletes ticket using BookingFacade
     *
     * @param ticketId
     * @return ticket page
     * @return attribute(" ticket ", Ticket)
     * positive responseStatus HttpStatus.NO_CONTENT
     * @see org.example.spring.facade.BookingFacade
     */
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ModelAndView cancelTicket(@RequestParam long ticketId) {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("ticket");
        if (facade.cancelTicket(ticketId)) {
            return modelAndView;
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            return modelAndView;
        }
    }
}
