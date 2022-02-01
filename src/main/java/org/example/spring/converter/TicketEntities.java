package org.example.spring.converter;

import org.example.spring.model.Entity.TicketEntity;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * this entity used for xml (un)marshaling lists of tickets by JAXB
 */
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketEntities {

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
