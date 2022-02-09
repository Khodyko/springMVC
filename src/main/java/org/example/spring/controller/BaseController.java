package org.example.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * The first controller of this application.
 *
 * @author: Igor Khodyko
 */
@RestController
public class BaseController {
    private final static Logger logger = LogManager.getLogger(BaseController.class.getName());

    public BaseController() {
        logger.log(DEBUG, "Created");
    }

    /**
     * @return index.html
     * @Get
     */
    @GetMapping("/home")
    @ResponseStatus(code = HttpStatus.OK)
    public String goHome() {
        logger.log(DEBUG, "started");
        return "index";
    }
}
