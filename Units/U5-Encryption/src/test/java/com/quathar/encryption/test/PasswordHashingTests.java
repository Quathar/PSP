package com.quathar.encryption.test;

import com.quathar.encryption.PasswordHashing;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <h1>Password Hashing TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
class PasswordHashingTests {

    // <<-TESTS->>
    @Test
    void givenRawPassword_whenEncoded_thenMatchesSuccess() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String rawPassword = "password";
        String hashed = PasswordHashing.hash(rawPassword);
        assertTrue(PasswordHashing.matches(rawPassword, hashed));
    }

    @Test
    void givenRawPassword_whenEncodedTwice_thenHashesAreDifferent() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String rawPassword = "password";
        String hashed1 = PasswordHashing.hash(rawPassword);
        String hashed2 = PasswordHashing.hash(rawPassword);
        assertNotEquals(hashed1, hashed2);
    }

}
