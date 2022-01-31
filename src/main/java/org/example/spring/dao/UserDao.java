package org.example.spring.dao;

import org.example.spring.exception.DaoException;
import org.example.spring.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    public User getUserById(long userId);

    public User getUserByEmail(String email);

    public List<User> getUsersByName(String name, int pageSize, int pageNum) throws DaoException;

    public User saveUser(User user);

    public User updateUser(User user);

    public boolean deleteUser(long userId);

    public Set<Long> getUsersByNameAndEmail(User user);
}
