package org.example.spring.model.Entity;

import javax.xml.bind.annotation.*;
import java.util.List;

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
