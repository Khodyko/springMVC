package org.example.spring.controller;


import org.example.spring.facade.FacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

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
    public ModelAndView getEventById(@RequestParam("eventId") long eventId, Model model) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(facade.getEventById(eventId));
        return modelAndView;
    }
    @GetMapping(params = {"title", "page-size", "page-num"} )
    public ModelAndView getEventsByTitle(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(facade.getEventsByTitle(title, pageSize, pageNum));
        return modelAndView;
    }
    @GetMapping(params = {"day", "page-size", "page-num"} )
    public ModelAndView getEventsForDay(@RequestParam @DateTimeFormat(pattern= "yyyy-MM-dd")Date day,
                                        @RequestParam int pageSize,
                                        @RequestParam int pageNum) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(facade.getEventsForDay(day, pageSize, pageNum));
        return modelAndView;
    }
//    @PostMapping
//    public ModelAndView createEvent(Event event) {
//        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.addObject(facade.createEvent(event));
//        return modelAndView;
//    }
//    @PutMapping
//    public ModelAndView updateEvent(Event event) {
//        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.addObject(facade.updateEvent(event));
//        return modelAndView;
//    }
//    @DeleteMapping
//    public String deleteEvent(long eventId) {
//        if(facade.deleteEvent(eventId)){
//            //fixme
//        }
//        else{
//            //fixme
//        }
//        return "index";
//    }
//

}
