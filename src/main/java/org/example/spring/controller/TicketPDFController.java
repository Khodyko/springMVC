package org.example.spring.controller;

import org.example.spring.converter.TicketPDFExporter;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Ticket;
import org.example.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/ticket1/pdf")
public class TicketPDFController {
    private FacadeImpl facade;
    private TicketPDFExporter exporter;

    public TicketPDFController() {
    }

    public TicketPDFController(FacadeImpl facade, TicketPDFExporter exporter) {
        this.facade = facade;
        this.exporter = exporter;
    }

    @GetMapping(params = {"name", "email", "page-size", "page-num"})
    public void getBookedTickets(
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "page-size") int pageSize,
                                @RequestParam(value = "page-num") int pageNum,
                                HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        User user = new UserEntity(name, email);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date(System.currentTimeMillis()));
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tickets_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Ticket> ticketList = facade.getBookedTickets(user, pageSize, pageNum);
        exporter.export(response, ticketList);
    }
}
