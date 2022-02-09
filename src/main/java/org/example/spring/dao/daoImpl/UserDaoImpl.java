package org.example.spring.dao.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.Storage;
import org.example.spring.dao.UserDao;
import org.example.spring.exception.DaoException;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.User;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.Level.DEBUG;

public class UserDaoImpl implements UserDao {
    private final static Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());
    private Storage storage;
    private ValidatorDao validatorDao;


    public UserDaoImpl() {
        logger.log(DEBUG, "created");
    }

    public UserDaoImpl(Storage storage, ValidatorDao validatorDao) {
        logger.log(DEBUG, "created");
        this.storage = storage;
        this.validatorDao = validatorDao;
    }

    public ValidatorDao getValidatorDao() {
        return validatorDao;
    }

    public void setValidatorDao(ValidatorDao validatorDao) {
        this.validatorDao = validatorDao;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }


    @Override
    public User getUserById(long userId) {
        logger.log(DEBUG, "started.");
        UserEntity user = null;
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        for (Map.Entry<String, UserEntity> entry : userEntityMap.entrySet()) {
            if (entry.getValue().getId() == userId) {
                user = entry.getValue();
                break;
            }
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        logger.log(DEBUG, "started.");
        UserEntity user = null;
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        for (Map.Entry<String, UserEntity> entry : userEntityMap.entrySet()) {
            if (entry.getValue().getEmail().equals(email)) {
                user = entry.getValue();
                break;
            }
        }
        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) throws DaoException {
        logger.log(DEBUG, "started.");
        List<User> userList = new ArrayList<>();
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        if (validatorDao.validateListForPage(pageSize, pageNum)) {
            for (Map.Entry<String, UserEntity> entry : userEntityMap.entrySet()) {
                if (entry.getValue().getName().equals(name)) {
                    userList.add(entry.getValue());
                }
            }
            return getPagedList(userList, pageSize, pageNum);
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        logger.log(DEBUG, "started.");
        long userId = 0;
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        for (Map.Entry<String, UserEntity> entry : userEntityMap.entrySet()) {
            if (entry.getValue().getId() >= userId) {
                userId = entry.getValue().getId() + 1;
            }
        }
        user.setId(userId);
        userEntityMap.put("user:" + user.getId(), (UserEntity) user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        logger.log(DEBUG, "started.");
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        if (userEntityMap.containsKey("user:" + user.getId())) {
            userEntityMap.put("user:" + user.getId(), (UserEntity) user);
            List<?> list = new ArrayList<>();
            list.add(null);
            return user;
        }
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        logger.log(DEBUG, "started.");
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        return userEntityMap.remove("user:" + userId, this.getUserById(userId));
    }

    @Override
    public Set<Long> getUsersByNameAndEmail(User user) {
        logger.log(DEBUG, "started.");
        Set<Long> userIdSet = new HashSet<>();
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        for (Map.Entry<String, UserEntity> entry : userEntityMap.entrySet()) {
            if (entry.getValue().getName().equals(user.getName()) &&
                    entry.getValue().getEmail().equals(user.getEmail())) {
                userIdSet.add(entry.getValue().getId());
            }
        }
        return userIdSet;
    }

    private List<User> getPagedList(List<User> userList, Integer pageSize, Integer pageNum) {
        logger.log(DEBUG, "started.");
        List<User> pagedList;
        return userList.stream().
                skip(pageSize * pageNum).
                limit(pageSize * pageNum + pageSize).
                collect(Collectors.toList());
    }
}
