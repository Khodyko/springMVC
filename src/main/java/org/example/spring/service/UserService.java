package org.example.spring.service;

import org.example.spring.model.User;
import org.example.spring.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Realise business logic of users and connects to DAO layer.
 *
 * @author Igor Khodyko
 */
@Service
public interface UserService {

    /**
     * Gets user by its id.
     *
     * @param userId
     * @return User.
     */
    public User getUserById(long userId);

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @param email
     * @return User
     */
    public User getUserByEmail(String email);

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     * @throws ServiceException
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) throws ServiceException;

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User.
     * @return Created User object.
     */
    public User createUser(User user);

    /**
     * Updates user using given data.
     *
     * @param user
     * @return Updated User object.
     */
    public User updateUser(User user);

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId);
}
