package com.quathar.encryption.test;

import com.quathar.encryption.ShaHashing;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h1>SHA TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
class ShaHashingTests {

    // <<-CONSTANT->>
    public static final String HASH = "7195596f6e3d4cff200f94cc4daee945bd39d1c460822abfbb3fa16378d20e00";

    // <<-TESTS->>
    @Test
    void givenString_WhenHashingWithSHA256_ThenSuccess() throws NoSuchAlgorithmException {
        String input = "Service and Process Programming";
        assertEquals(ShaHashing.hash(input), HASH);
    }

}
