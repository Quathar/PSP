package com.quathar.encryption.test;

import com.quathar.encryption.RsaEncryption;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * <h1>RSA Encryption TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class RsaEncryptionTests {

    // <<-TESTS->>
    @Test
    public void givenString_WhenEncryptedWithPublicKey_ThenDecryptedWithPrivateKeySuccess()
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String input = "Service and Process Programming";
        KeyPair keyPair = RsaEncryption.generateKeyPair(2048);
        String cypherText = RsaEncryption.encrypt(input, keyPair.getPublic());
        String plainText = RsaEncryption.decrypt(cypherText, keyPair.getPrivate());
        assertEquals(input, plainText);
    }

    @Test
    public void givenString_WhenEncryptedWithPublicKey_ThenDecryptedWithPublicKeyFail()
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String input = "Service and Process Programming";
        KeyPair keyPair = RsaEncryption.generateKeyPair(2048);
        String cypherText = RsaEncryption.encrypt(input, keyPair.getPublic());
        assertThrowsExactly(BadPaddingException.class, () -> RsaEncryption.decrypt(cypherText, keyPair.getPublic()));
    }

    @Test
    public void givenString_WhenEncryptedWithPrivateKey_ThenDecryptedWithPublicKeySuccess()
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String input = "Service and Process Programming";
        KeyPair keyPair = RsaEncryption.generateKeyPair(2048);
        String cypherText = RsaEncryption.encrypt(input, keyPair.getPrivate());
        String plainText = RsaEncryption.decrypt(cypherText, keyPair.getPublic());
        assertEquals(input, plainText);
    }

    @Test
    public void givenString_WhenEncryptedWithPrivateKey_ThenDecryptedWithPrivateKeyFails()
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String input = "Service and Process Programming";
        KeyPair keyPair = RsaEncryption.generateKeyPair(2048);
        String cypherText = RsaEncryption.encrypt(input, keyPair.getPrivate());
        assertThrowsExactly(BadPaddingException.class, () -> RsaEncryption.decrypt(cypherText, keyPair.getPrivate()));
    }

    @Test
    public void givenString_WhenEncryptWithKey_ThenDecryptedWithSavedKeySuccess()
            throws NoSuchAlgorithmException,
                   IOException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException,
                   InvalidKeySpecException
    {
        String input = "Service and Process Programming";
        Path publicKeyPath = Path.of(".", "id_rsa.pub");
        Path privateKeyPath = Path.of(".", "id_rsa");
        KeyPair keyPair = RsaEncryption.writeKeyPair(publicKeyPath, privateKeyPath, 2048);
        String cypherText = RsaEncryption.encrypt(input, keyPair.getPrivate());
        keyPair = RsaEncryption.readKeyPair(publicKeyPath, privateKeyPath);
        String plainText = RsaEncryption.decrypt(cypherText, keyPair.getPublic());
        assertEquals(input, plainText);
        Files.delete(publicKeyPath);
        Files.delete(privateKeyPath);
    }

}
