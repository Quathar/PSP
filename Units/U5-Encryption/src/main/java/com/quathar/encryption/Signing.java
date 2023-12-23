package com.quathar.encryption;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import java.util.Base64;

/**
 * <h1>Signing</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class Signing {

    // <<-METHODS->>
    public static String sign(
            Path file,
            PrivateKey privateKey
    ) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        byte[] messageBytes = Files.readAllBytes(file);
        signature.update(messageBytes);
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }

    public static boolean verify(
            Path file,
            String digitalSignature,
            PublicKey publicKey
    ) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        byte[] receivedSignature = Base64.getDecoder().decode(digitalSignature);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        byte[] messageBytes = Files.readAllBytes(file);
        signature.update(messageBytes);
        return signature.verify(receivedSignature);
    }

}
