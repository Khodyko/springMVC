package org.example.spring.exception;

/**
 * Used for wrapping Exceptions in Facade
 * layer and sending it to Controller layer.
 *
 * @author Igor Khodyko
 */
public class FacadeException extends Exception {
    public FacadeException() {
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }
}
