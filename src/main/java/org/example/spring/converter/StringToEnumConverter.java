package org.example.spring.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.model.Ticket;
import org.springframework.core.convert.converter.Converter;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * Used for converting enum Category to string in Ticket entity.
 *
 * @author Igor Khodyko
 * @see Ticket
 */
public class StringToEnumConverter implements Converter<String, Ticket.Category> {
    private final static Logger logger =  LogManager.getLogger(JsonReader.class.getName());

    public StringToEnumConverter() {logger.log(DEBUG,  "created");
    }

    @Override
    public Ticket.Category convert(String s) {
        logger.log(DEBUG, "started.");
        if(s.equals("STANDARD")){
            return Ticket.Category.STANDARD;
        }
        else if(s.equals("PREMIUM")){
            return Ticket.Category.PREMIUM;
        }
        else if(s.equals("BAR")){
            return Ticket.Category.BAR;
        }
        else{
        return null;}
    }
}
