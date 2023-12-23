package com.quathar.psp.words;

import java.io.BufferedReader;
import java.nio.file.Path;

/**
 * <h1>Word Counter</h1>
 * <br>
 * <p>
 *     Child class executed by Main (parent class).<br>
 *     <br>
 *     Receive the path where the file is located, count its words and print the final count to the standard exit.
 * </p>
 *
 * @since 2022-10-27
 * @version 1.0
 * @author Q
 */
public class WordCounter {

    //<<-METHOD->>
    public static void main(String[] args) throws java.io.IOException {
        try (BufferedReader streamReader = new BufferedReader(
                new java.io.InputStreamReader(System.in)
        )) {
            while (true) {
                Path file = Path.of(streamReader.readLine());
                try (BufferedReader fileReader = new BufferedReader(
                        new java.io.FileReader(file.toString())
                )) {
                    String str;
                    int counter = 0;
                    while ((str = fileReader.readLine()) != null)
                        counter += str.split(" ").length;
                    System.out.printf("File '%s' has %d words%n", file.getFileName(), counter);
                }
            }
        }
    }

}
