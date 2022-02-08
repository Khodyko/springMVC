package org.example.spring.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.converter.JsonReader;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * Used for handling controller exceptions (ApplicationException)
 *
 * @author Igor Khodyko
 * @see ApplicationException
 */
@ControllerAdvice
public class ApplicationExceptionHandler {
    private final static Logger logger = LogManager.getLogger(ApplicationExceptionHandler.class.getName());

    public ApplicationExceptionHandler() {
        logger.log(DEBUG, "created");

    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleException(ApplicationException e) {
        logger.log(DEBUG, "started.");
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
