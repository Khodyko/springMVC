package org.example.spring.dao.daoImpl;

import org.example.spring.exception.DaoException;

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
