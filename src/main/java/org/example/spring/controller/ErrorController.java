package org.example.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for redirecting to error page.
 *
 * @author Igor Khodyko
 */
@RestController
public class ErrorController {

    /**
     * Redirect to error page.
     *
     * @param request
     * @return error page.
     */
    @RequestMapping(path = "/error")
    public ModelAndView handle(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }
}
