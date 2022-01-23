package org.example.spring;


import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Storage implements  Serializable {

    private static final long serialVersionUID = 1L;
    private JsonReader jsonReader;
    private Map<String, TicketEntity> ticketMap = new HashMap();
    private Map<String, UserEntity> userMap = new HashMap();
    private Map<String, EventEntity> eventMap = new HashMap();
    @Value("${event.file.path}")
    private String eventFilePath;
    @Value("${ticket.file.path}")
    private String ticketFilePath;
    @Value("${user.file.path}")
    private String userFilePath;

    public Storage() {

    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public Map<String, TicketEntity> getTicketMap() {
        return ticketMap;
    }

    public void setTicketMap(Map<String, TicketEntity> ticketMap) {
        this.ticketMap = ticketMap;
    }

    public Map<String, UserEntity> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, UserEntity> userMap) {
        this.userMap = userMap;
    }

    public Map<String, EventEntity> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, EventEntity> eventMap) {
        this.eventMap = eventMap;
    }


    public void initMethod(){
            eventMap = jsonReader.readFileJson(eventFilePath, EventEntity.class);
            userMap = jsonReader.readFileJson(userFilePath, UserEntity.class);
            ticketMap = jsonReader.readFileJson(ticketFilePath, TicketEntity.class);
    }


}
