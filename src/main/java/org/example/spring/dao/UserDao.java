package org.example.spring.dao;

import org.example.spring.exception.DaoException;
import org.example.spring.model.User;

import java.util.List;
import java.util.Set;

/**
 * Realise connection to database for work with User objects.
 *
 * @author Igor Khodyko
 */
public interface UserDao {
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
     * @throws DaoException
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) throws DaoException;

    /**
     * Generates new id for user. Save new user.
     *
     * @param user User.
     * @return Created User object.
     */
    public User saveUser(User user);

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

    /**
     * Returns set of ids of users, that have equal name and email.
     *
     * @param user (contains name and email)
     * @return Set of ids of user from database.
     */
    public Set<Long> getUsersByNameAndEmail(User user);
}
