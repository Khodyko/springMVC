package org.example.spring.controller;

import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Event;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    private FacadeImpl facade;

    public TicketController() {
    }

    @Autowired
    public TicketController(FacadeImpl facade) {
        this.facade = facade;
    }

    @PostMapping
    public ModelAndView bookTicket(@RequestParam("userId") long userId,
                                   @RequestParam("eventId") long eventId,
                                   @RequestParam("place") int place,
                                   @RequestParam("category") Ticket.Category category) {
        ModelAndView modelAndView = new ModelAndView("ticket");
        modelAndView.addObject("ticket", facade.bookTicket(userId, eventId, place, category));
        return modelAndView;
    }

    @GetMapping(params = {"name", "email", "page-size", "page-num"})
    public ModelAndView getBookedTickets(@RequestParam(value = "name") String name,
                                         @RequestParam(value = "email") String email,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) {
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("ticket");
        modelAndView.addObject("tickets", facade.getBookedTickets(user, pageSize, pageNum));
        return modelAndView;
    }

    @GetMapping(params = {"title", "day", "page-size", "page-num"})
    public ModelAndView getBookedTickets(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "day") Date day,
                                         @RequestParam(value = "page-size") int pageSize,
                                         @RequestParam(value = "page-num") int pageNum) {
        Event event = new EventEntity(title, day);
        ModelAndView modelAndView = new ModelAndView("ticket");
        modelAndView.addObject("tickets", facade.getBookedTickets(event, pageSize, pageNum));
        return modelAndView;
    }

    @GetMapping(params = "ticketId")
    public ModelAndView getTicketById(@RequestParam("ticketId") long ticketId) {
        ModelAndView modelAndView = new ModelAndView("ticket");
        facade.preloadTickets();
        modelAndView.addObject("ticket", facade.getTicketById(ticketId));
        return modelAndView;
    }

    @DeleteMapping
    public ModelAndView cancelTicket(@RequestParam long ticketId) {
        ModelAndView modelAndView = new ModelAndView("ticket");
        if (facade.cancelTicket(ticketId)) {
            //fixme
        } else {
            // FIXME
        }
        return modelAndView;

    }
}
