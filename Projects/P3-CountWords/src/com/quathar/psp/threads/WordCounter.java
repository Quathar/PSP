package com.quathar.psp.threads;

import java.io.BufferedReader;
import java.nio.file.Path;

/**
 * <h1>WordCounter</h1>
 * <br>
 * Receive a file and count its words.
 *
 * @since 2022-10-20
 * @version 1.0
 * @author Q
 */
public class WordCounter extends Thread {

    // <<-FIELD->>
    private final Path path;

    // <<-CONSTRUCTOR->>
    public WordCounter(Path path) {
        this.path = path;
    }

    // <<-METHOD->>
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(this.path.toString()))) {
            int counter = 0;
            String str;
            while ((str = br.readLine()) != null)
                counter += str.split(" ").length;
            System.out.printf("File '%s' has %d words%n", this.path.getFileName(), counter);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

}
