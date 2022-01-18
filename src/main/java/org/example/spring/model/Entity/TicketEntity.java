package org.example.spring.model.Entity;


import org.example.spring.model.Ticket;

import java.io.Serializable;
import java.util.Objects;

public class TicketEntity implements Ticket, Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public TicketEntity() {
    }

    public TicketEntity(long id, long eventId, long userId,
                        Category category, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public TicketEntity(long eventId, long userId,
                        Category category, int place) {
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketEntity)) return false;
        TicketEntity that = (TicketEntity) o;
        return id == that.id && eventId == that.eventId && userId == that.userId && place == that.place && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, userId, category, place);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TicketEntity{");
        sb.append("id=").append(id);
        sb.append(", eventId=").append(eventId);
        sb.append(", userId=").append(userId);
        sb.append(", category=").append(category);
        sb.append(", place=").append(place);
        sb.append('}');
        return sb.toString();
    }
}
