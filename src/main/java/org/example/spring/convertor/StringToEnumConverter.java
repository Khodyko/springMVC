package org.example.spring.convertor;

import org.example.spring.model.Ticket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//realise it in APP Context
public class StringToEnumConverter implements Converter<String, Ticket.Category> {
    @Override
    public Ticket.Category convert(String s) {
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
