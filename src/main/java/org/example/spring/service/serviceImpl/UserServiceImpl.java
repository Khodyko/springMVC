package org.example.spring.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.daoImpl.UserDaoImpl;
import org.example.spring.exception.DaoException;
import org.example.spring.exception.ServiceException;
import org.example.spring.model.User;
import org.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

public class UserServiceImpl implements UserService {
    private UserDaoImpl userDaoImpl;
    private final static Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

    @Autowired
    public UserServiceImpl(UserDaoImpl userDaoImpl) {
        logger.log(DEBUG, "created");
        this.userDaoImpl = userDaoImpl;
    }

    public UserServiceImpl() {
        logger.log(DEBUG, "created");
    }

    @Override
    public User getUserById(long userId) {
        logger.log(DEBUG, "started.");
        return userDaoImpl.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        logger.log(DEBUG, "started.");
        return userDaoImpl.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) throws ServiceException {
        logger.log(DEBUG, "started.");
        try {
            return userDaoImpl.getUsersByName(name, pageSize, pageNum);
        } catch (DaoException e) {
            logger.log(WARN, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User createUser(User user) {
        logger.log(DEBUG, "started.");
        return userDaoImpl.saveUser(user);
    }

    @Override
    public User updateUser(User user) {
        logger.log(DEBUG, "started.");
        return userDaoImpl.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        logger.log(DEBUG, "started.");
        return userDaoImpl.deleteUser(userId);
    }
}
