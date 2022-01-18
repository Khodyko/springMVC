package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.ExceptionDao.DaoException;

import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

public class ValidatorDao {

    public ValidatorDao() {

    }

    public  Boolean validateListForPage(Integer listSize, int pageSize, int pageNum) throws DaoException {

        Integer pageMaxNum = (listSize % pageSize > 0 ? 1 : 0) +
                (listSize / pageSize);
        if (pageSize < 0 || pageNum < 0) {
            throw new DaoException("page must be positive number");
        } else if (pageMaxNum < pageNum) {
            throw new DaoException("We have only " + listSize % pageSize +
                    " pages and page № " + pageNum + " is not exist");
        } else {
            return true;
        }
    }
}
