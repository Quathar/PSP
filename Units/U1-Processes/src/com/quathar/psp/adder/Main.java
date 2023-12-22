package com.quathar.psp.adder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <h1>Main</h1>
 *
 * @since 2022-09-15
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANT->>
    private static final String CLASS_PATH = java.nio.file.Path.of(
            System.getProperty("user.dir"),
            "out", "production", "U1-Processes"
    ).toString();

    // <<-METHODS->>
    private static void mode1() throws IOException {
        new ProcessBuilder("java", "-cp", CLASS_PATH, Adder.class.getName(), "-m1", "0", "32")
                .inheritIO()
                .start();
        new ProcessBuilder("java", "-cp", CLASS_PATH, Adder.class.getName(), "-m1", "3", "23")
                .inheritIO()
                .start();
    }

    private static void mode2() throws IOException, InterruptedException {
        Process ps1 = new ProcessBuilder("java", "-cp", CLASS_PATH, Adder.class.getName(), "-m2").start();
        Process ps2 = new ProcessBuilder("java", "-cp", CLASS_PATH, Adder.class.getName(), "-m2").start();

        try (
                PrintWriter    pw1 = new PrintWriter(ps1.getOutputStream(), true);
                PrintWriter    pw2 = new PrintWriter(ps2.getOutputStream(), true);
                BufferedReader br1 = new BufferedReader(new InputStreamReader(ps1.getInputStream()));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(ps2.getInputStream()))
        ) {
            pw1.println("0");
            pw1.println("32");

            pw2.println("3");
            pw2.println("23");

            System.out.printf("Output of process 1 is: %s%n", br1.readLine());
            System.out.printf("Output of process 2 is: %s%n", br2.readLine());

            pw1.println("4");
            pw1.println("38");

            pw2.println("9");
            pw2.println("36");

            System.out.printf("Output of process 1 is: %s%n", br1.readLine());
            System.out.printf("Output of process 2 is: %s%n", br2.readLine());
        }

        ps1.destroy();
        ps2.destroy();

        ps1.waitFor();
        ps2.waitFor();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            boolean exit = false;
            while (!exit) {
                System.out.println("How do you want to access the 'Adder' process? [ -m1 | Mode 1 ] [ -m2 | Mode 2 ]");
                switch (br.readLine()) {
                    case "-m1" -> {
                        mode1();
                        exit = true;
                    }
                    case "-m2" -> {
                        mode2();
                        exit = true;
                    }
                    default    -> System.err.println("E R R O R: Incorrect option");
                }
            }
        }
    }

}