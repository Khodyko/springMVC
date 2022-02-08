package org.example.spring.exception;

/**
 * Used for wrapping Exception in Dao layer to send it into Service Layer.
 *
 * @author Igor Khodyko
 */
public class DaoException extends Exception {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }
}
