package org.example.spring.controller;


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

@RestController
@RequestMapping("/event")
public class EventController {

    private FacadeImpl facade;

    public EventController() {
    }

    @Autowired
    public EventController(FacadeImpl facade) {
        this.facade = facade;
    }

    @GetMapping(params = "eventId")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getEventById(@RequestParam("eventId") long eventId) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView("event");
//        Event event = facade.getEventById(eventId);
//        if (event == null) {
//            throw new ApplicationException("event not found");
//        }
//        modelAndView.addObject("event", event);
        return modelAndView;
    }

    @GetMapping(params = {"title", "page-size", "page-num"})
    public ModelAndView getEventsByTitle(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println("title=" + title + " " + pageSize + " " + pageNum);
        try {
            modelAndView.addObject("events", facade.getEventsByTitle(title, pageSize, pageNum));
        } catch (FacadeException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    @GetMapping(params = {"day", "page-size", "page-num"})
    public ModelAndView getEventsForDay(@RequestParam Date day, @RequestParam(value = "page-size") int pageSize,
                                        @RequestParam(value = "page-num") int pageNum, Model model) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println(day);
        try {
            modelAndView.addObject("events", facade.getEventsForDay(day, pageSize, pageNum));
        } catch (FacadeException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createEvent(@RequestParam String title, @RequestParam Date day) {
        Event event = new EventEntity(title, day);
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", facade.createEvent(event));
        return modelAndView;
    }

    @PutMapping
    public ModelAndView updateEvent(@RequestParam long id, @RequestParam String title, @RequestParam Date day) {
        Event event = new EventEntity(id, title, day);
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", facade.updateEvent(event));
        return modelAndView;
    }

    @DeleteMapping
    public ModelAndView deleteEvent(@RequestParam long eventId) throws ApplicationException {

        ModelAndView modelAndView = new ModelAndView("event");
        if (facade.deleteEvent(eventId)) {
            return modelAndView;
        } else {
            throw new ApplicationException("Entitie is not deleted");
        }
    }
}
