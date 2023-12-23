package com.quathar.psp.threads;

import java.nio.file.Path;

/**
 * <h1>Main</h1>
 * <br>
 * Read 'contar.palabras.pdf' to see more about this exercise
 *
 * @since 2022-10-20
 * @version 1.0
 * @author Q
 */
public class Main {

    private static final Path FILES_PATH = Path.of(
            System.getProperty("user.dir"), "resources"
    );

    public static void main(String[] args) throws java.io.IOException {
        try (java.nio.file.DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(FILES_PATH)) {
            directoryStream.forEach(path -> new WordCounter(path).start());
        }
    }

}
