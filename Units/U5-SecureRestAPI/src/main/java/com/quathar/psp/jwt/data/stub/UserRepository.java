package com.quathar.psp.jwt.data.stub;

import com.quathar.psp.jwt.data.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>User Repository</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Repository
public class UserRepository implements com.quathar.psp.jwt.data.repository.UserRepository {

    // <<-FIELD->>
    private Map<String, User> _usersByName = new HashMap<>();

    // <<-CONSTRUCTOR->>
    public UserRepository(PasswordEncoder passwordEncoder) {
        saveUser(new User("elpepe", passwordEncoder.encode("pass1234")));
    }

    // <<-METHODS->>
    @Override
    public Boolean exists(String username) {
        return _usersByName.containsKey(username);
    }

    @Override
    public void saveUser(User user) {
        _usersByName.putIfAbsent(user.getUsername(), user);
    }

    @Override
    public User findByUsername(String username) {
        return _usersByName.get(username);
    }

}
