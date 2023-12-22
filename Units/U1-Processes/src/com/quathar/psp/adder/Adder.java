package com.quathar.psp.adder;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *  <h1>Adder</h1>
 *
 * @since 2022-09-15
 * @version 1.0
 * @author Q
 */
public class Adder {

    // <<-METHODS->>
    private static void adder(int startNumber, int finalNumber) {
        int result = 0;
        for (int i = startNumber; i < finalNumber; i++)
            result += i;
        System.out.println("The result is: " + result);
    }

    private static void processesArgs(String[] args) {
        int startNumber = Integer.parseInt(args[1]);
        int finalNumber = Integer.parseInt(args[2]);
        adder(startNumber, finalNumber);
    }

    private static void processesInOut(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            while (true) {
                int startNumber = Integer.parseInt(reader.readLine());
                int finalNumber = Integer.parseInt(reader.readLine());
                adder(startNumber, finalNumber);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        switch (args[0]) {
            case "-m1" -> processesArgs(args);
            case "-m2" -> processesInOut(args);
        }
    }

}
