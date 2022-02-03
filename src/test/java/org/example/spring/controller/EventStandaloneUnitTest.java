package org.example.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventStandaloneUnitTest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController()).build();
    }

    @Test
    public void getEventById() throws Exception {
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
