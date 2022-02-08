package org.example.spring.exception;

/**
 * This exception used in Controller layers to wrap Exceptions for handling.
 *
 * @author Igor Khodyko
 */
public class ApplicationException extends Exception {
    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
