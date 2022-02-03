package org.example.spring.controller;

import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.EventEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@WebAppConfiguration
public class EventControllerUnitTest
//        extends TestCase
{
    private MockMvc mockMvc;
    @Autowired
    private FacadeImpl facade;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
//        Mockito.reset(facade);
        System.out.println("test!!!!!!!!!!!!!2");
        System.out.println("WAC = " + webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        System.out.println("test!!!!!!!!!!!!!2");
    }

    @Test
    public void getEventById() throws Exception {
        System.out.println("test!!!!!!!!!!!!!1");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
//
//        Date date = null;
//        try {
//            date = simpleDateFormat.parse("2021-10-10-00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//            //fixme
//        }
//        EventEntity eventEntity = new EventEntity(0, "title", date);
//        when(facade.getEventById(0)).thenReturn(eventEntity);
//        EventEntity eventEntityFromMock = new EventEntity(0, "title", date);


        mockMvc.perform(get("/event").param("eventId", "0"))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(view().name("event"));

    }

//    @Test
//    void getEventsByTitle() {
//    }
//
//    @Test
//    void getEventsForDay() {
//    }
//
//    @Test
//    void createEvent() {
//    }
//
//    @Test
//    void updateEvent() {
//    }
//
//    @Test
//    void deleteEvent() {
//    }
}