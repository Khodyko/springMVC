package org.example.spring.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.model.Entity.TicketEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * this entity used for xml (un)marshaling lists of tickets by JAXB.
 *
 * @author Igor Khodyko
 */
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketEntities {
    private final static Logger logger = LogManager.getLogger(JsonReader.class.getName());

    public TicketEntities() {
    }

    @XmlElement(name = "ticket")
    private List<TicketEntity> ticketEntitiesList = null;

    public TicketEntities(List<TicketEntity> ticketEntitiesList) {
        this.ticketEntitiesList = ticketEntitiesList;
    }

    public List<TicketEntity> getTicketEntitiesList() {
        return ticketEntitiesList;
    }

    public void setTicketEntitiesList(List<TicketEntity> ticketEntitiesList) {
        this.ticketEntitiesList = ticketEntitiesList;
    }
}
