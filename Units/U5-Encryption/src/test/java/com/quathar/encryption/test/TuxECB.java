package com.quathar.encryption.test;

import com.quathar.encryption.AesEncryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

/**
 * <h1>TuxECB TEST</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author juanagui, Q
 */
public class TuxECB {

    // <<-METHOD->>
    public static void main(String[] args)
            throws URISyntaxException,
                   NoSuchAlgorithmException,
                   IOException,
                   InvalidAlgorithmParameterException,
                   NoSuchPaddingException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidKeyException
    {
        String resourceName = "Tux.ppm";

        Path ppmInputPath = Paths.get(TuxECB.class.getClassLoader()
                                                  .getResource(resourceName)
                                                  .toURI());
        Path ppmOutputPath = Path.of(".", "tux.ecb.ppm");
        Path pixelMapPath = Path.of(".", "tux.bin");
        Path encryptedPixelMapPath = Path.of(".", "tux.enc.bin");

        FileInputStream ppmReader = new FileInputStream(ppmInputPath.toFile());
        FileOutputStream ppmWriter = new FileOutputStream(ppmOutputPath.toFile());

        // Copy header from original PPM file to destination file
        byte[] ppmHeader = ppmReader.readNBytes(15);
        ppmWriter.write(ppmHeader);

        // Copy pixel map from original file to binary file.
        // There's 1 byte per color and there's 3 colors,
        // hence we use a multiple of 3 for the buffer
        FileOutputStream binWriter = new FileOutputStream(pixelMapPath.toFile());
        byte[] buffer = new byte[96];
        int read;
        while ((read = ppmReader.read(buffer)) > 0) {
            binWriter.write(buffer, 0, read);
        }

        // Done reading the source PPM file, close readers.
        binWriter.close();
        ppmReader.close();

        // Encrypt the pixelMapPath to encryptedPixelMapPath
        SecretKey secretKey = AesEncryption.generateKey(128);
        AesEncryption.encryptFile("AES/ECB/PKCS5Padding ", secretKey, null, pixelMapPath, encryptedPixelMapPath);

        // Assemble the final PPM with the encrypted pixel map
        FileInputStream encryptedBinReader = new FileInputStream(encryptedPixelMapPath.toFile());
        while ((read = encryptedBinReader.read(buffer)) > 0) {
            ppmWriter.write(buffer, 0, read);
        }
        encryptedBinReader.close();
        ppmWriter.close();

        // Delete temp files
        Files.delete(pixelMapPath);
        Files.delete(encryptedPixelMapPath);
    }

}
