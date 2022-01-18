package org.example.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * Used for reading EventEntity, TicketEntity, UserEntity
 */
public class JsonReader implements Serializable {
    private static final long serialVersionUID = 1L;


    public JsonReader() {

    }

    public <T> Map<String, T> readFileJson(String jsonPath, final Class<T> type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(df);
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(Paths.get(jsonPath).toFile(),
                    typeFactory.constructMapType(Map.class, String.class, type));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
