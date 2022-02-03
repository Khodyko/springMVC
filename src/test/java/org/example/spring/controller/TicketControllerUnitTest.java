package org.example.spring.controller;

import junit.framework.TestCase;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.Ticket;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@WebAppConfiguration
public class TicketControllerUnitTest extends TestCase {

    private MockMvc mockMvc;
    @Autowired
    private FacadeImpl facade;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void bookTicket() throws Exception {
        TicketEntity ticketEntity=new TicketEntity( 0, 0, Ticket.Category.BAR, 0);
        TicketEntity ticketEntityFromMock=new TicketEntity(0, 0, 0, Ticket.Category.BAR, 0);
        when(facade.bookTicket(0,0,0, Ticket.Category.BAR)).thenReturn(ticketEntityFromMock);
        mockMvc.perform(post("/ticket").param("userId", "0")
        .param("eventId","0")
        .param("place", "0")
        .param("category", "BAR"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket"))
                .andExpect(model().attribute("ticket", Matchers.equalTo(ticketEntityFromMock)));
    }
    @Test
    public void getBookedTickets() throws Exception {
        TicketEntity ticketEntity=new TicketEntity(0,0, 0, Ticket.Category.BAR, 0);
        List<Ticket> ticketList = new ArrayList<>();
        UserEntity userEntity = new UserEntity( "sergei", "sergei@mail.ru");
        ticketList.add(ticketEntity);
        when(facade.getBookedTickets(userEntity,100,0)).thenReturn(ticketList);
        mockMvc.perform(get("/ticket").param("name", "sergei")
                .param("email","sergei@mail.ru")
                .param("page-size", "100")
                .param("page-num", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket"))
                .andExpect(model().attribute("tickets", Matchers.equalTo(ticketList)));

    }

    @Test
    public void testGetBookedTickets() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        TicketEntity ticketEntity=new TicketEntity(0,0, 0, Ticket.Category.BAR, 0);
        List<Ticket> ticketList = new ArrayList<>();
        EventEntity eventEntity = new EventEntity( "title", date);
        ticketList.add(ticketEntity);
        when(facade.getBookedTickets(eventEntity,100,0)).thenReturn(ticketList);
        mockMvc.perform(get("/ticket").param("title", "title")
                .param("day","2021-10-10")
                .param("page-size", "100")
                .param("page-num", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket"))
                .andExpect(model().attribute("tickets", Matchers.equalTo(ticketList)));
    }

    @Test
    public void getTicketById() throws Exception {
        TicketEntity ticketEntity=new TicketEntity(0,0, 0, Ticket.Category.BAR, 0);
        when(facade.getTicketById(0)).thenReturn(ticketEntity);
        mockMvc.perform(get("/ticket").param("ticketId", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket"))
                .andExpect(model().attribute("ticket", Matchers.equalTo(ticketEntity)));
    }

    @Test
    public void cancelTicket() throws Exception {
        when(facade.cancelTicket(0)).thenReturn(true);
        mockMvc.perform(delete("/ticket").param("ticketId", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket"));
    }
}