package org.example.spring.converter;


import org.example.spring.model.Entity.TicketEntities;
import org.example.spring.model.Entity.TicketEntity;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TicketXmlConverter implements ItemReader {
    List<TicketEntity> list = null;
    private Boolean batchJobState = false;

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    @Value("${ticket.preload.xml.path}")
    private String filePath;

    public TicketXmlConverter() {
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }


    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        //need clean code!!!
        System.out.println("converter");
        if (!batchJobState) {
            try (FileInputStream is = new FileInputStream(filePath)) {
                list = ((TicketEntities) this.unmarshaller.unmarshal(new StreamSource(is))).getTicketEntitiesList();
                batchJobState = true;
                System.out.println("list succesfully created!: " + list);
            }
        }
        if (!list.isEmpty()) {
            return list.remove(0);
        }
        return null;
    }
}
