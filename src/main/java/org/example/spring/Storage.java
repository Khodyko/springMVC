package org.example.spring;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.converter.JsonReader;
import org.example.spring.model.Entity.EventEntity;
import org.example.spring.model.Entity.TicketEntity;
import org.example.spring.model.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.Level.DEBUG;


/**
 * This class is in-memory-data-base.
 * Storage uses ticketMap, userMap, eventMap as a storage of data.
 *
 * @author Igor Khodyko
 */

public class Storage implements  Serializable {

    private static final long serialVersionUID = 1L;
    private JsonReader jsonReader;
    private Map<String, TicketEntity> ticketMap = new HashMap();
    private Map<String, UserEntity> userMap = new HashMap();
    private Map<String, EventEntity> eventMap = new HashMap();
    private final static Logger logger =  LogManager.getLogger(Storage.class.getName());
    @Value("${event.file.path}")
    private String eventFilePath;
    @Value("${ticket.file.path}")
    private String ticketFilePath;
    @Value("${user.file.path}")
    private String userFilePath;

    public Storage() {
        logger.log(DEBUG,  "created");
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

    /**
     * Init Bean of this class. Get data from json files by json Reader.
     *
     * @see JsonReader
     */
    public void initMethod(){
        logger.log(DEBUG, "started. Get data from json files by json Reader");
            eventMap = jsonReader.readFileJson(eventFilePath, EventEntity.class);
            userMap = jsonReader.readFileJson(userFilePath, UserEntity.class);
            ticketMap = jsonReader.readFileJson(ticketFilePath, TicketEntity.class);
    }


}
