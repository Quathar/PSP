package com.quathar.psp.image.parent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Main</h1>
 * <br>
 * Parent class to manage the data that its send to the class child.ImageProcessor
 *
 * @since 2022-10-03
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final String JAR_PATH = Path.of(System.getProperty("user.dir"),
            "out", "artifacts", "ImageProcessor_Child_jar", "ImageProcessor.Child.jar").toString();
    private static final Path LOREM_SRC_PATH = Path.of(System.getProperty("user.dir"), "lorem");
    private static final int MAX_PROCESSES = 4;

    // <<-METHODS->>
    private static void launcher() throws IOException, InterruptedException {
        List<Process> processes = new ArrayList<>();

        try (DirectoryStream<Path> imagesPaths = Files.newDirectoryStream(LOREM_SRC_PATH)) {
            for (Path image : imagesPaths)
                processes.add(new ProcessBuilder("java", "-jar", JAR_PATH, "-n", image.toString()).start());
        }

        for (Process process : processes) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                process.waitFor();
                System.out.println(br.readLine());
            }
        }
    }

    private static void advancedLauncher() throws IOException, InterruptedException {
        List<Process> processes = new ArrayList<>(MAX_PROCESSES);
        for (int i = 0; i < MAX_PROCESSES; i++)
            processes.add(new ProcessBuilder("java", "-jar", JAR_PATH, "-a").start());
        List<PrintWriter> writers = processes.stream()
                .map(process -> new PrintWriter(process.getOutputStream(), true))
                .toList();
        List<BufferedReader> readers = processes.stream()
                .map(process -> new BufferedReader(new InputStreamReader(process.getInputStream())))
                .toList();

        // Communicating with the child processes
        try (DirectoryStream<Path> imagesPaths = Files.newDirectoryStream(LOREM_SRC_PATH)) {
            java.util.Iterator<Path> it = imagesPaths.iterator();
            while (it.hasNext()) {
                int counter = 0;
                for (PrintWriter printWriter : writers) {
                    if (it.hasNext()) {
                        printWriter.println( it.next() );
                        counter++;
                    } else break;
                }

                for (int i = 0; i < counter; i++)
                    System.out.println(readers.get(i).readLine());
            }
        }

        // Closing
        for (int i = 0; i < MAX_PROCESSES; i++) {
            readers  .get(i).close();
            writers  .get(i).close();
            processes.get(i).destroy();
            processes.get(i).waitFor();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            boolean exit = false;
            while (!exit) {
                System.out.println("How do you want to launch the processes? [ -n | Normal Mode ] [ -a | Advanced ] ");
                switch (br.readLine().toLowerCase()) {
                    case "-n" -> {
                        Main.launcher();
                        exit = true;
                    }
                    case "-a" -> {
                        Main.advancedLauncher();
                        exit = true;
                    }
                    default -> System.err.println("E R R O R: Incorrect option");
                }
            }
        }
    }

}
