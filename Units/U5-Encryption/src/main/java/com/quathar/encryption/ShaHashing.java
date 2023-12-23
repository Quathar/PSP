package com.quathar.encryption;

import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <h1>SHA (Secure Hashing Algorithm)</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class ShaHashing {

    // <<-CONSTANT->>
    public static final String ALGORITHM = "SHA-256";

    // <<-METHOD->>
    public static String hash(String source) throws java.security.NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        byte[] hashBytes = digest.digest(source.getBytes(UTF_8));
        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte aByte : hashBytes) {
            hashStringBuilder.append(String.format("%02x", aByte));
        }
        return hashStringBuilder.toString();
    }

}
