package org.example.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleException(ApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
