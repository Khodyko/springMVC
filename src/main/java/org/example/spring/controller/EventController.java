package org.example.spring.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.example.spring.exception.ApplicationException;
import org.example.spring.exception.FacadeException;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The controller of events
 *
 * @author: Igor Khodyko
 */
@Controller
@RequestMapping("/event")
public class EventController {
    private final static Logger logger =  LogManager.getLogger(EventController.class.getName());
    private FacadeImpl facade;

    public EventController() {
        logger.log(DEBUG,  "Created");
    }

    @Autowired
    public EventController(FacadeImpl facade) {
        this.facade = facade;
        logger.log(DEBUG,  "Created");
    }

    /**
     * This method returns event by id from BookingFacade
     * @param eventId
     * @return event page
     * @return attribute("event", Event)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = "eventId")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getEventById(@RequestParam("eventId") long eventId) {
        logger.log(DEBUG,  "started");
        ModelAndView modelAndView = new ModelAndView("event");
        Event event = facade.getEventById(eventId);
        modelAndView.addObject("event", event);
        return modelAndView;
    }

    /**
     *
     * This method returns eventList by title(paged) from BookingFacade
     * @param title
     * @param pageSize
     * @param pageNum
     * @return event page
     * @return attribute("events", List<Event>)
     * @throws ApplicationException
     * positive responseStatus HttpStatus.Ok
     * in case of ApplicationException redirect to error page
     */
    @GetMapping(params = {"title", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getEventsByTitle(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) throws ApplicationException {
        logger.log(DEBUG,  "started");
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println("title=" + title + " " + pageSize + " " + pageNum);
        try {
            modelAndView.addObject("events", facade.getEventsByTitle(title, pageSize, pageNum));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     *
     * This method returns event by day (paged) from BookingFacade
     * @param day
     * @param pageSize
     * @param pageNum
     * @param model
     * @return event page
     * @return attribute("event", Event)
     * @throws ApplicationException
     * in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = {"day", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getEventsForDay(@RequestParam Date day, @RequestParam(value = "page-size") int pageSize,
                                        @RequestParam(value = "page-num") int pageNum, Model model) throws ApplicationException {
        logger.log(DEBUG,  "started");
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println(day);
        try {
            modelAndView.addObject("events", facade.getEventsForDay(day, pageSize, pageNum));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     *
     * This method creates event by title and by day uses BookingFacade
     * @param title
     * @param day
     * @return event page
     * @return attribute("event", Event)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView createEvent(@RequestParam String title, @RequestParam Date day) {
        logger.log(DEBUG,  "started");
        Event event = new EventEntity(title, day);
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", facade.createEvent(event));
        return modelAndView;
    }

    /**
     *
     * This method updates event by Id, title, day uses BookingFacade
     * @param eventId
     * @param title
     * @param day
     * @return event page
     * @return attribute("event", Event)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView updateEvent(@RequestParam long eventId, @RequestParam String title, @RequestParam Date day) {
        logger.log(DEBUG, "started.");
        Event event = new EventEntity(eventId, title, day);
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", facade.updateEvent(event));
        return modelAndView;
    }

    /**
     *
     * This method deletes event by Id uses BookingFacade
     * @param eventId
     * @return event page
     * @return atribute("event", Event)
     * @throws ApplicationException
     * positive responseStatus HttpStatus.Ok
     * in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ModelAndView deleteEvent(@RequestParam long eventId) throws ApplicationException {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("event");
        if (facade.deleteEvent(eventId)) {
            return modelAndView;
        } else {
            return modelAndView;
        }
    }
}
