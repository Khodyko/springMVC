package org.example.spring.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * Used for reading EventEntity, TicketEntity, UserEntity
 *
 * @author Igor Khodyko
 */
public class JsonReader implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Logger logger =  LogManager.getLogger(JsonReader.class.getName());

    public JsonReader() {  logger.log(DEBUG,  "created");
    }

    /**
     * Read data from file in JSON format.
     *
     * @param jsonPath path to reading file.
     * @param type Type of entities in file(Event, User, Ticket).
     * @return Map of objects
     */
    public <T> Map<String, T> readFileJson(String jsonPath, final Class<T> type) {
        try {
            logger.log(DEBUG, "started.");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(df);
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(Paths.get(jsonPath).toFile(),
                    typeFactory.constructMapType(Map.class, String.class, type));

        } catch (Exception e) {
            logger.log(WARN, e.getMessage());
        }
        return null;
    }
}
