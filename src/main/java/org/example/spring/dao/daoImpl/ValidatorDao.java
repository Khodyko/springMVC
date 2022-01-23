package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.ExceptionDao.DaoException;

import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

public class ValidatorDao {

    public ValidatorDao() {

    }

    public  Boolean validateListForPage(int pageSize, int pageNum) throws DaoException {
        if (pageSize < 0 || pageNum < 0) {
            throw new DaoException("page must be positive number");
        } else {
            return true;
        }
    }
}
