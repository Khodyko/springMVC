package org.example.spring.exception;
/**
 * This exception used in Service layers to wrap
 * Exceptions to send it to Facade layer.
 *
 * @author Igor Khodyko
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
