package org.example.spring;


import org.example.spring.model.Entity.TicketEntities;
import org.example.spring.model.Entity.TicketEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TicketXmlConverter {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
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

    public void convertFromObjectToXML(Object object, String filePath)
            throws IOException {
        try (FileOutputStream os = new FileOutputStream(filePath)) {
            this.marshaller.marshal(object, new StreamResult(os));
        }
    }

    public Object convertFromXMLToObject(String filePath) throws IOException {
        System.out.println("converter");
        try (FileInputStream is = new FileInputStream(filePath)) {
            List<TicketEntity> list=  ((TicketEntities)this.unmarshaller.unmarshal(new StreamSource(is))).getTicketEntitiesList();
            return list;
        }
    }
}
