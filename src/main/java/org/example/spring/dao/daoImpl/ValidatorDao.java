package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.converter.JsonReader;
import org.example.spring.exception.DaoException;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * Validates Pagesize, PageNum params in Dao layer.
 *
 * @author Igor Khodyko
 */
public class ValidatorDao {
    private final static Logger logger =  LogManager.getLogger(ValidatorDao.class.getName());

    public ValidatorDao() {
        logger.log(DEBUG,  "created");
    }

    /**
     * Validates Pagesize, PageNum to > 0.
     * @param pageSize
     * @param pageNum
     * @return true or throw DaoException.
     * @throws DaoException
     */
    public  Boolean validateListForPage(int pageSize, int pageNum) throws DaoException {
        logger.log(DEBUG, "started.");
        DaoException ex;
        if (pageSize < 0 || pageNum < 0) {
            ex=new DaoException("page must be positive number");
            logger.log(WARN, ex.getMessage());
            throw ex;
        } else {
            return true;
        }
    }
}
