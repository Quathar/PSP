package com.quathar.psp.jwt.application.model;

import lombok.Data;

/**
 * <h1>Authentication Request</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Data
public class AuthenticationRequest {

    // <<-FIELDS->>
    private String username;
    private String password;

}
