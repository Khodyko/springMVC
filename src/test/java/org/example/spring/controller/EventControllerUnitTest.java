package org.example.spring.controller;

import junit.framework.TestCase;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Event;
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
public class EventControllerUnitTest extends TestCase {
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
    public void getEventById() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        EventEntity eventEntity = new EventEntity(0, "title", date);
        when(facade.getEventById(0)).thenReturn(eventEntity);
        EventEntity eventEntityFromMock = new EventEntity(0, "title", date);
        mockMvc.perform(get("/events").param("eventId", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("event"))
                .andExpect(model().attribute("event", Matchers.equalTo(eventEntity)));
    }

    @Test
    public void getEventsByTitle() throws Exception {
        List<Event> eventEntityList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        EventEntity eventEntity = new EventEntity(0, "title", date);
        eventEntityList.add(eventEntity);
        when(facade.getEventsByTitle("title", 100, 0)).thenReturn(eventEntityList);
        mockMvc.perform(get("/events").param("title", "title")
                        .param("page-size", "100")
                        .param("page-num", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("event"))
                .andExpect(model().attribute("events", Matchers.equalTo(eventEntityList)));

    }

    @Test
    public void getEventsForDay() throws Exception {
        List<Event> eventEntityList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        EventEntity eventEntity = new EventEntity(0, "title", date);
        eventEntityList.add(eventEntity);
        when(facade.getEventsForDay(date, 100, 0)).thenReturn(eventEntityList);
        mockMvc.perform(get("/events").param("day", "2021-10-10")
                        .param("page-size", "100")
                        .param("page-num", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("event"))
                .andExpect(model().attribute("events", Matchers.equalTo(eventEntityList)));
    }

    @Test
    public void createEvent() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        EventEntity eventEntity = new EventEntity("title", date);
        EventEntity eventEntityFromMock = new EventEntity(0, "title", date);
        when(facade.createEvent(eventEntity)).thenReturn(eventEntityFromMock);
        mockMvc.perform(post("/events").param("title", "title")
                        .param("day", "2021-10-10"))
                .andExpect(status().isCreated())
                .andExpect(view().name("event"))
                .andExpect(model().attribute("event", Matchers.equalTo(eventEntityFromMock)));
    }

    @Test
    public void updateEvent() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-10");
        EventEntity eventEntity = new EventEntity(0, "title", date);
        EventEntity eventEntityFromMock = new EventEntity(0, "title", date);
        when(facade.updateEvent(eventEntity)).thenReturn(eventEntityFromMock);
        mockMvc.perform(put("/events").param("eventId", "0")
                        .param("title", "title")
                        .param("day", "2021-10-10"))
                .andExpect(status().isOk())
                .andExpect(view().name("event"))
                .andExpect(model().attribute("event", Matchers.equalTo(eventEntityFromMock)));
    }

    @Test
    public void deleteEvent() throws Exception {
        when(facade.deleteEvent(0)).thenReturn(true);
        mockMvc.perform(delete("/events").param("eventId", "0"))
                .andExpect(status().isNoContent())
                .andExpect(view().name("event"));
    }
}