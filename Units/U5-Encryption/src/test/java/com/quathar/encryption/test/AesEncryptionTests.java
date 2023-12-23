package com.quathar.encryption.test;

import com.quathar.encryption.AesEncryption;

import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h1>AES Encryption TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
class AesEncryptionTests {

    // <<-CONSTANT->>
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    // <<-TESTS->>
    @Test
    void givenString_WhenEncrypt_ThenDecryptSucceeds()
            throws NoSuchAlgorithmException,
                   InvalidAlgorithmParameterException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String input = "Service and Process Programming";
        SecretKey secretKey = AesEncryption.generateKey(128);
        IvParameterSpec iv  = AesEncryption.generateIV();
        String cipherText   = AesEncryption.encrypt(ALGORITHM, input, secretKey, iv);
        String plainText    = AesEncryption.decrypt(ALGORITHM, cipherText, secretKey, iv);
        assertEquals(input, plainText);
    }

    @Test
    void givenString_WhenEncryptWithPassword_ThenDecryptSucceeds()
            throws NoSuchAlgorithmException,
                   InvalidAlgorithmParameterException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException,
                   InvalidKeySpecException
    {
        String input = "Service and Process Programming";
        SecretKey secretKey = AesEncryption.fromPassword(
                "Multiplatform Application Development",
                "0490",
                128);
        IvParameterSpec iv = AesEncryption.generateIV();
        String cipherText  = AesEncryption.encrypt(ALGORITHM, input, secretKey, iv);
        String plainText   = AesEncryption.decrypt(ALGORITHM, cipherText, secretKey, iv);
        assertEquals(input, plainText);
    }

}
