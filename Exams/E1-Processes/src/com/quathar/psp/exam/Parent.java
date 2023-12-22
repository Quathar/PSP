package com.quathar.psp.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <h1>Parent</h1>
 * <br>
 * <p>
 *     This class launch the Child process that:
 *     <ol>
 *         <li>Exam Type 1: Reverses the strings (The first 3 Strings are from -t1)</li>
 *         <li>Exam Type 2: Converts to uppercase the strings (The last 3 Strings are from -t2)</li>
 *     </ol>
 * </p>
 *
 * @see Child
 * @since 2022-10-06
 * @version 1.0
 * @author Q
 */
public class Parent {

    // <<-CONSTANTS->>
    private static final String[] PHRASES = {
            "Sed eu cursus sem, id vehicula odio.",
            "Mauris sagittis non elit sed bibendum.",
            "Fusce finibus sollicitudin vestibulum.",
            "Proin felis turpis, convallis a vehicula a, placerat non sem.",
            "Maecenas non ipsum nisl.",
            "Ut tincidunt nisl a efficitur faucibus." };
    private static final String CLASS_PATH = java.nio.file.Path.of(
            System.getProperty("user.dir"),
            "out", "production", "E1-Processes").toString();

    // <<-METHODS->>
    public static void launcher(String option) throws IOException, InterruptedException {
        List<Process> processes = new java.util.ArrayList<>();
        for (String phrase : PHRASES)
            processes.add(new ProcessBuilder("java", "-cp", CLASS_PATH, Child.class.getName(), option, phrase).start());

        for (int i = 0; i < processes.size(); i++) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(processes.get(i).getInputStream()))) {
                processes.get(i).waitFor();
                System.out.println(br.readLine());
                System.out.printf("El proceso %s terminó con estado %d%n", i + 1, processes.get(i).exitValue());
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            boolean selected = false;
            while (!selected) {
                System.out.println("Which exam do you want to perform? [ -t1 | Type 1 ] [ -t2 | Type 2 ]");
                switch (br.readLine()) {
                    case "-t1" -> {
                        launcher("-t1");
                        selected = true;
                    }
                    case "-t2" -> {
                        launcher("-t2");
                        selected = true;
                    }
                    default    -> System.err.println("E R R O R: Incorrect option");
                }
            }
        }
    }

}
