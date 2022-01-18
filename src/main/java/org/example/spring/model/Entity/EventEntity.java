package org.example.spring.model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.spring.model.Event;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class EventEntity implements Event, Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventEntity() {
    }

    public EventEntity(long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public EventEntity(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEntity)) return false;
        EventEntity that = (EventEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventEntity{");
        sb.append("id=").append(id);
        sb.append(", Title='").append(title).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
