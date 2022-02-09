package org.example.spring.model;

import java.util.Date;

/**
 * The Event class represents an object of event that User
 * can book by Ticket
 *
 * @author: Igor Khodyko
 * @see User
 * @see Ticket
 */
public interface Event {
    /**
     * Event id. UNIQUE.
     *
     * @return Event Id
     */
    long getId();

    void setId(long id);

    String getTitle();

    void setTitle(String title);

    Date getDate();

    void setDate(Date date);
}
