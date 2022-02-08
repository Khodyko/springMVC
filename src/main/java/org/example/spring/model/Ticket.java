package org.example.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The Ticket can be booked by User
 * to Event
 *
 * @author: Igor Khodyko
 * @see Event
 * @see Ticket
 */
public interface Ticket {
    @JsonSerialize
    public enum Category {
        @JsonProperty("STANDARD")
        STANDARD,
        @JsonProperty("PREMIUM")
        PREMIUM,
        @JsonProperty("BAR")
        BAR

    }

    /**
     * Ticket Id. UNIQUE.
     *
     * @return Ticket Id.
     */
    long getId();

    void setId(long id);

    long getEventId();

    void setEventId(long eventId);

    long getUserId();

    void setUserId(long userId);

    Category getCategory();

    void setCategory(Category category);

    int getPlace();

    void setPlace(int place);

}
