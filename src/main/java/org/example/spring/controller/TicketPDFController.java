package org.example.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.converter.TicketPDFExporter;
import org.example.spring.exception.ApplicationException;
import org.example.spring.exception.FacadeException;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The controller for returning of TicketList in pdf format
 *
 * @authot: Igor Khodyko
 */
@Controller
@RequestMapping("/tickets/pdf")
public class TicketPDFController {
    private FacadeImpl facade;
    private TicketPDFExporter exporter;
    private final static Logger logger = LogManager.getLogger(TicketPDFController.class.getName());

    public TicketPDFController() {
        logger.log(DEBUG, "created");
    }

    public TicketPDFController(FacadeImpl facade, TicketPDFExporter exporter) {
        this.facade = facade;
        this.exporter = exporter;
        logger.log(DEBUG, "created");
    }

    /**
     * This method returns ticket by id from
     * BookingFacade in pdf format uses TicketPDFExporter
     *
     * @param name
     * @param email
     * @param pageSize
     * @param pageNum
     * @param response
     * @return pdf file
     * @throws ApplicationException positive responseStatus HttpStatus.Ok
     *                              in case of ApplicationException redirect to error page
     * @see org.example.spring.facade.BookingFacade
     * @see TicketPDFExporter
     */
    @GetMapping(params = {"name", "email", "page-size", "page-num"})
    @ResponseStatus(code = HttpStatus.OK)
    public void getBookedTickets(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page-size") int pageSize,
            @RequestParam(value = "page-num") int pageNum,
            HttpServletResponse response) throws ApplicationException {
        logger.log(DEBUG, "started.");
        response.setContentType("application/pdf");
        User user = new UserEntity(name, email);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date(System.currentTimeMillis()));
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tickets_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Ticket> ticketList = null;
        try {
            ticketList = facade.getBookedTickets(user, pageSize, pageNum);
            exporter.export(response, ticketList);
        } catch (FacadeException | IOException e) {
            logger.log(WARN, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
