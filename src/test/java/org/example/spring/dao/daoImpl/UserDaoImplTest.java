package org.example.spring.dao.daoImpl;

import junit.framework.TestCase;
import org.example.spring.Storage;

import org.example.spring.exception.DaoException;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest extends TestCase {
    @Mock
    private ValidatorDao validatorDao;
    @Mock
    private Storage storage;
    @InjectMocks
    private UserDaoImpl userDaoImpl;

    @Test
    public void testGetUserById() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        Map<String, UserEntity> userEntityMap = new HashMap<>();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        UserEntity userFromMock = (UserEntity) userDaoImpl.getUserById(12);
        assertEquals(userFromMock, userEntity);

    }

    @Test
    public void testGetUserByEmail() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        Map<String, UserEntity> userEntityMap = new HashMap<>();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        UserEntity userFromMock = (UserEntity) userDaoImpl.getUserByEmail("sergei@mail.ru");
        assertEquals(userFromMock, userEntity);
    }

    @Test
    public void testGetUsersByName() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        List<User> userList = new ArrayList<>();
        Map<String, UserEntity> userEntityMap = storage.getUserMap();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        try {
            when(validatorDao.validateListForPage(any(Integer.class), any(Integer.class))).thenReturn(true);
        } catch (DaoException e) {
            //fix me
            e.printStackTrace();
        }
        try {
            userList = userDaoImpl.getUsersByName("sergei", 100, 0);
        } catch (DaoException e) {
            //fix me
            e.printStackTrace();
        }
        assertEquals(userList.get(0), userEntity);
    }

    @Test
    public void testSaveUser() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        Map<String, UserEntity> userEntityMap = new HashMap<>();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        UserEntity userFromMock = (UserEntity) userDaoImpl.saveUser(userEntity);
        assertEquals(userFromMock, userEntity);
    }

    @Test
    public void testUpdateUser() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        Map<String, UserEntity> userEntityMap = new HashMap<>();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        UserEntity userFromMock = (UserEntity) userDaoImpl.updateUser(userEntity);
        assertEquals(userFromMock, userEntity);
    }

    @Test
    public void testDeleteUser() {
        UserEntity userEntity = new UserEntity(12, "sergei", "sergei@mail.ru");
        Map<String, UserEntity> userEntityMap = new HashMap<>();
        userEntityMap.put("user:" + userEntity.getId(), userEntity);
        when(storage.getUserMap()).thenReturn(userEntityMap);
        Boolean isUserEntityDeleted = userDaoImpl.deleteUser(12);
        assertTrue(isUserEntityDeleted);
    }
}