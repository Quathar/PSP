package com.quathar.encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * <h1>RSA (Rivest-Shamir-Adleman) Encryption</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class RsaEncryption {

    // <<-CONSTANTS->>
    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----";
    public static final String END_RSA_PUBLIC_KEY = "-----END RSA PUBLIC KEY-----";

    // <<-METHODS->>
    public static KeyPair generateKeyPair(int bits) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(bits);
        return keyPairGenerator.generateKeyPair();
    }

    public static KeyPair writeKeyPair(
            Path publicKeyPath,
            Path privateKeyPath,
            int bits
    ) throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = generateKeyPair(bits);
        Base64.Encoder pemEncoder = Base64.getMimeEncoder(64, System.lineSeparator().getBytes());
        try (PrintWriter pubKeyWriter = new PrintWriter(new FileOutputStream(publicKeyPath.toFile()))) {
            pubKeyWriter.println(BEGIN_RSA_PUBLIC_KEY);
            pubKeyWriter.println(pemEncoder.encodeToString(keyPair.getPublic().getEncoded()));
            pubKeyWriter.println(END_RSA_PUBLIC_KEY);
        }
        try (PrintWriter privKeyWriter = new PrintWriter(new FileOutputStream(privateKeyPath.toFile()))) {
            privKeyWriter.println(BEGIN_PRIVATE_KEY);
            privKeyWriter.println(pemEncoder.encodeToString(keyPair.getPrivate().getEncoded()));
            privKeyWriter.println(END_PRIVATE_KEY);
        }
        return keyPair;
    }

    public static KeyPair readKeyPair(
            Path publicKeyPath,
            Path privateKeyPath
    ) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey pub;
        try (FileInputStream pubKeyInputStream = new FileInputStream(publicKeyPath.toFile())) {
            String fileContents = new String(pubKeyInputStream.readAllBytes());
            String publicKeyPEM = fileContents
                    .replace(BEGIN_RSA_PUBLIC_KEY, "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace(END_RSA_PUBLIC_KEY, "");
            byte[] pubKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyBytes, "RSA");
            KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
            pub = keyFacPub.generatePublic(pubKeySpec);
        }
        PrivateKey priv;
        try (FileInputStream privKeyInputStream = new FileInputStream(privateKeyPath.toFile())) {
            String fileContents = new String(privKeyInputStream.readAllBytes());
            String privateKeyPEM = fileContents
                    .replace(BEGIN_PRIVATE_KEY, "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace(END_PRIVATE_KEY, "");
            byte[] privKeyBytes = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyBytes, "RSA");
            KeyFactory keyFacpriv = KeyFactory.getInstance("RSA");
            priv = keyFacpriv.generatePrivate(privKeySpec);
        }

        KeyPair keyPair = new KeyPair(pub, priv);
        return keyPair;
    }

    public static String encrypt(String plainText, Key key)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] cypherBytes = cipher.doFinal(plainTextBytes);
        return Base64.getEncoder().encodeToString(cypherBytes);
    }

    public static String decrypt(String cypherText, Key key)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cypherTextBytes = Base64.getDecoder().decode(cypherText);
        byte[] plainTextBytes  = cipher.doFinal(cypherTextBytes);
        return new String(plainTextBytes);
    }

}
