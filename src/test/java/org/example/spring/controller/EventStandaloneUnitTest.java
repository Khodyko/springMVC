package org.example.spring.controller;

import org.example.spring.facade.FacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventStandaloneUnitTest {
    private MockMvc mockMvc;


    @Before
    public void setup() {
//        Mockito.reset(facade);
        System.out.println("test!!!!!!!!!!!!!2");

        mockMvc = MockMvcBuilders.standaloneSetup(new EventController()).build();
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
}
