package org.example.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.exception.ApplicationException;
import org.example.spring.exception.FacadeException;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The controller of users
 *
 * @author: Igor Khodyko
 */
@Controller
@RequestMapping("/users")
public class UserController {
    private FacadeImpl facade;
    private final static Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    public UserController(FacadeImpl facade) {
        this.facade = facade;
        logger.log(DEBUG, "created");
    }

    public UserController() {
        logger.log(DEBUG, "created");
    }


    /**
     * This method returns user by id from BookingFacade
     *
     * @param userId
     * @return user page
     * @return attribute(" user ", User)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = "userId")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getUserById(@RequestParam("userId") long userId) {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.getUserById(userId));
        return modelAndView;
    }

    /**
     * This method returns user by email from BookingFacade
     *
     * @param email
     * @return email page
     * @return attribute(" user ", User)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = "email")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getUserByEmail(@RequestParam("email") String email) {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.getUserByEmail(email));
        return modelAndView;
    }

    /**
     * This method returns userList by name(paged) from BookingFacade
     *
     * @param name
     * @param pageSize
     * @param pageNum
     * @return event page
     * @return attribute(" users ", List < User >)
     * @throws ApplicationException positive responseStatus HttpStatus.Ok
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @GetMapping(params = {"name", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView getUsersByName(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "page-size") int pageSize,
                                       @RequestParam(value = "page-num") int pageNum)
                                       throws ApplicationException {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("user");
        try {
            modelAndView.addObject("users", facade.getUsersByName(name, pageSize, pageNum));
        } catch (FacadeException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return modelAndView;
    }

    /**
     * This method creates user by  name, email uses BookingFacade
     *
     * @param name
     * @param email
     * @return user page
     * @return attribute(" user ", user)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView createUser(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email) {
        logger.log(DEBUG, "started.");
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.createUser(user));
        return modelAndView;
    }

    /**
     * This method updates user by  name, email uses BookingFacade
     *
     * @param name
     * @param email
     * @return user page
     * @return attribute(" user ", user)
     * positive responseStatus HttpStatus.Ok
     * @see org.example.spring.facade.BookingFacade
     */
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView updateUser(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email) {
        logger.log(DEBUG, "started.");
        User user = new UserEntity(name, email);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", facade.updateUser(user));
        return modelAndView;
    }

    /**
     * This method deletes user by id uses BookingFacade
     *
     * @param userId
     * @return user page
     * @return attribute(" user ", user)
     * @throws ApplicationException positive responseStatus HttpStatus.NO_CONTENT
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     */
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ModelAndView deleteUser(@RequestParam("userId") long userId) throws ApplicationException {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("user");
        if (facade.deleteUser(userId)) {
            return modelAndView;
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            return modelAndView;
        }
    }
}
