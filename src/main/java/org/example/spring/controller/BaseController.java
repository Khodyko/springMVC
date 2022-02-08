package org.example.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * The first controller of this application
 *
 * @author: Igor Khodyko
 */
@Controller
public class BaseController {
    private final static Logger logger =  LogManager.getLogger(BaseController.class.getName());

    public BaseController() {
        logger.log(DEBUG,  "Created");
    }

    /**
     * @Get
     * @return index.html
     */
    @GetMapping("/home")
    @ResponseStatus(code = HttpStatus.OK)
    public String goHome() {
        logger.log(DEBUG,  "started");
        System.out.println("go index from controller!");
        return "index";
    }
}
