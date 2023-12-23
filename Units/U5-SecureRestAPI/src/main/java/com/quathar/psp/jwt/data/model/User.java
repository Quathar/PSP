package com.quathar.psp.jwt.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>User</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@AllArgsConstructor
@Getter
public class User {

    // <<-FIELDS->>
    private final String username;
    private final String encryptedPassword;

}
