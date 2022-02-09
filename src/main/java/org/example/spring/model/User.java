package org.example.spring.model;

/**
 * The User can book the Ticket
 * to Event
 *
 * @author: Igor Khodyko
 * @see User
 * @see Event
 */
public interface User {
    /**
     * User Id. UNIQUE.
     *
     * @return User Id.
     */
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    /**
     * User email. UNIQUE.
     *
     * @return User email.
     */
    String getEmail();

    void setEmail(String email);
}
