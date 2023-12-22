package com.quathar.psp.sockets.encodings;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <h1>Main</h1>
 *
 * @since 2022-XX-XX
 * @version 1.0
 * @author Q
 */
public class Main {

    public static void main(String[] args) {
        String original = "The string ü@foo-bar";
        String encoded =  java.net.URLEncoder.encode(original, UTF_8);
        System.out.printf("%s URL encoded is: %s%n", original, encoded);
        String decoded =  java.net.URLDecoder.decode(original, UTF_8);
        System.out.printf("%s URL decoded is: %s%n", encoded, decoded);
    }

}
