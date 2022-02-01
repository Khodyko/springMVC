package org.example.spring.controller;

import org.example.spring.exception.ApplicationException;
import org.example.spring.exception.FacadeException;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private FacadeImpl facade;

    @Autowired
    public UserController(FacadeImpl facade) {
        this.facade = facade;
    }

    public UserController() {
    }
//change camelCase to "-" everywhere
    @GetMapping(params = "userId")
    public ModelAndView getUserById(@RequestParam("userId") long userId) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user",facade.getUserById(userId));
        return modelAndView;
    }

    @GetMapping(params = "email")
    //validate it!!!
    public ModelAndView getUserByEmail(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.getUserByEmail(email));
        return modelAndView;
    }

    @GetMapping(params = {"name", "page-size", "page-num"})
    public ModelAndView getUsersByName(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "page-size") int pageSize,
                                       @RequestParam(value = "page-num") int pageNum) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView("user");
        try {
            modelAndView.addObject("users", facade.getUsersByName(name, pageSize, pageNum));
        } catch (FacadeException e) {
            throw new ApplicationException(e.getMessage(),e);
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createUser(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email) {
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.createUser(user));
        return modelAndView;
    }

    @PutMapping
    public ModelAndView updateUser(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email) {
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.updateUser(user));
        return modelAndView;
    }

    @DeleteMapping
    public ModelAndView deleteUser(@RequestParam("userId") long userId) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView("user");
        if (facade.deleteEvent(userId)) {
            return modelAndView;
        } else {
            throw new ApplicationException("Entitie is not deleted");
        }

    }
}
