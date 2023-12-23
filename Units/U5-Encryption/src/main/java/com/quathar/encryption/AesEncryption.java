package com.quathar.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <h1>AES (Advanced Encryption Standard) Encryption</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class AesEncryption {

    // <<-CONSTANTS->>
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final byte LENGTH = 64;

    // <<-METHODS->>
    public static SecretKey generateKey(int bits) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(bits);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static SecretKey fromPassword(
            String password,
            String salt,
            int bits
    ) throws NoSuchAlgorithmException,InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        KeySpec keySpec = new PBEKeySpec(
                password.toCharArray(),
                salt.getBytes(),
                65536,
                bits
        );
        SecretKey secretKey = new SecretKeySpec(
                secretKeyFactory.generateSecret(keySpec).getEncoded(),
                "AES"
        );
        return secretKey;
    }

    public static IvParameterSpec generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(
            String algorithm,
            String plainText,
            SecretKey secretKey,
            IvParameterSpec iv
    ) throws NoSuchPaddingException,
             NoSuchAlgorithmException,
             InvalidAlgorithmParameterException,
             InvalidKeyException,
             IllegalBlockSizeException,
             BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(
            String algorithm,
            String cypherText,
            SecretKey secretKey,
            IvParameterSpec iv
    ) throws NoSuchPaddingException,
             NoSuchAlgorithmException,
             InvalidAlgorithmParameterException,
             InvalidKeyException,
             IllegalBlockSizeException,
             BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cypherText));
        return new String(decrypted);
    }

    public static void encryptFile(
            String algorithm,
            SecretKey key,
            IvParameterSpec iv,
            Path inputFile,
            Path outputFile
    ) throws NoSuchPaddingException,
             NoSuchAlgorithmException,
             InvalidAlgorithmParameterException,
             InvalidKeyException,
             IOException,
             IllegalBlockSizeException,
             BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        try (
                InputStream inputStream   = Files.newInputStream(inputFile);
                OutputStream outputStream = Files.newOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[LENGTH];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
    }

    public static void decryptFile(
            String algorithm,
            SecretKey key,
            IvParameterSpec iv,
            Path inputFile,
            Path outputFile
    ) throws NoSuchPaddingException,
             NoSuchAlgorithmException,
             InvalidAlgorithmParameterException,
             InvalidKeyException,
             IOException,
             IllegalBlockSizeException,
             BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        try (
                InputStream inputStream   = Files.newInputStream(inputFile);
                OutputStream outputStream = Files.newOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[LENGTH];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
    }

}
