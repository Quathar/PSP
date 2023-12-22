package com.quathar.psp;

import java.nio.file.Path;

/**
 * <h1>Processes</h1>
 *
 * @author Q
 * @version 1.0
 * @since 2022-09-13
 */
public class Processes {

    // <<-CONSTANTS->>
    private static final String PROGRAM_PATH = Path.of(
            System.getProperty("user.home"),
            "AppData",
            "Local", "BraveSoftware", "Brave-Browser", "Application", "brave.exe"
    ).toString();
    private static final String FILE_PATH = Path.of(
            System.getProperty("user.dir"),
            "resources", "Tutor_Ficha_individual_alumno_v4.pdf"
    ).toString();

    // <<-METHOD->>
    public static void main(String[] args) {
        try {
            ProcessBuilder builder = new ProcessBuilder(PROGRAM_PATH, FILE_PATH);
            Process process = builder.start();
            System.out.printf("The PID of the process is '%d'%n", process.pid());
            process.waitFor();
            System.out.printf("The output code of the process is %d", process.exitValue());
        } catch (java.io.IOException | InterruptedException e) {
            System.err.println("E R R O R: I/O or Interrupted");
        }
    }

}
