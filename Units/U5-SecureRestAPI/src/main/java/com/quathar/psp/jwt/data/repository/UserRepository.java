package com.quathar.psp.jwt.data.repository;

import com.quathar.psp.jwt.data.model.User;

/**
 * <h1>User Repository</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
public interface UserRepository {

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to check
     * @return true if a user with the specified username exists, false otherwise
     */
    Boolean exists(String username);

    /**
     * Saves a User object to the repository.
     *
     * @param user the User object to save
     */
    void saveUser(User user);

    /**
     * Retrieves a User object based on the username.
     *
     * @param username the username to search for
     * @return the User object with the specified username, or null if not found
     */
    User findByUsername(String username);

}
