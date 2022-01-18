package org.example.spring.dao.ExceptionDao;

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
