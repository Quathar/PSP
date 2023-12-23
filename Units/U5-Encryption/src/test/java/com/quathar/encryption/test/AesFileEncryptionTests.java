package com.quathar.encryption.test;

import com.quathar.encryption.AesEncryption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h1>AES File Encryption TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
class AesFileEncryptionTests {

    // <<-CONSTANT->>
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    // <<-FIELDS->>
    private final Path inputFile;
    private final Path encryptedFile;
    private final Path decryptedFile;

    // <<-CONSTRUCTOR->>
    AesFileEncryptionTests() throws IOException, URISyntaxException {
        String resourceName = "The Dunwhich horror by H.P Lovecraft";
        this.inputFile     = Paths.get(getClass().getClassLoader()
                                            .getResource(resourceName)
                                            .toURI());
        this.encryptedFile = Files.createTempFile(resourceName, ".encrypted");
        this.decryptedFile = Files.createTempFile(resourceName, ".decrypted");
    }

    // <<-TESTS->>
    @AfterEach
    void cleanFiles() throws IOException {
        if (Files.exists(this.encryptedFile))
            Files.delete(this.encryptedFile);
        if (Files.exists(this.decryptedFile))
            Files.delete(this.decryptedFile);
    }

    @Test
    void givenFile_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException,
                   IllegalBlockSizeException,
                   InvalidKeyException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException,
                   NoSuchPaddingException,
                   IOException
    {
        SecretKey key = AesEncryption.generateKey(128);
        IvParameterSpec ivParameterSpec = AesEncryption.generateIV();
        AesEncryption.encryptFile(ALGORITHM, key, ivParameterSpec, this.inputFile, this.encryptedFile);
        AesEncryption.decryptFile(ALGORITHM, key, ivParameterSpec, this.encryptedFile, this.decryptedFile);
        assertEquals(Files.mismatch(this.inputFile, this.decryptedFile), -1L);
    }

}
