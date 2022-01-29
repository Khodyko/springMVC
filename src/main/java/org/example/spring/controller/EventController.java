package org.example.spring.controller;


import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
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
    public ModelAndView getEventById(@RequestParam("eventId") long eventId) {
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", facade.getEventById(eventId));
        return modelAndView;
    }

    @GetMapping(params = {"title", "page-size", "page-num"})
    public ModelAndView getEventsByTitle(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println("title="+title+" "+pageSize+" "+pageNum);
        modelAndView.addObject("events", facade.getEventsByTitle(title, pageSize, pageNum));
        return modelAndView;
    }

    @GetMapping(params = {"day", "page-size", "page-num"})
    public ModelAndView getEventsForDay(@RequestParam Date day, @RequestParam(value = "page-size") int pageSize,
                                        @RequestParam(value = "page-num") int pageNum, Model model) {
        ModelAndView modelAndView = new ModelAndView("event");
        System.out.println(day);
        modelAndView.addObject("events", facade.getEventsForDay(day, pageSize, pageNum));
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createEvent(@RequestParam String title, @RequestParam Date day) {
        Event event = new EventEntity(title, day);
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event",facade.createEvent(event));
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
    public ModelAndView deleteEvent(@RequestParam long eventId) {

        ModelAndView modelAndView = new ModelAndView("event");
        if (facade.deleteEvent(eventId)) {
            //fixme
        } else {
            // FIXME
        }
        return modelAndView;
    }


}
