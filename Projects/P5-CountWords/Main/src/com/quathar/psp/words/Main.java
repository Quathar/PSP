package com.quathar.psp.words;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <h1>Main</h1>
 * <br>
 * <p>
 *     Parent class that execute WordCounter (child class).
 * </p><br>
 * <p>
 *     Manage the paths of the files and the .jar program.
 *     Pass data to the child class and receive the output.
 * </p><br>
 * <p>
 *     You have to generate the .jar (with IDE or any other tool)
 *     from the JAR_PATH constant for this class to work.
 * </p>
 *
 * @since 2022-10-27
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final Path JAR_PATH = Path.of(
            System.getProperty("user.dir"),
            "out", "artifacts",
            "WordCounter_jar", "WordCounter.jar");
    private static final Path FILES_PATH = Path.of(System.getProperty("user.dir"), "files");
    private static final int MAX_PROCESSES = 3;

    // <<-METHOD->>
    public static void main(String[] args) throws IOException {
        List<Process> processes = IntStream.range(0, MAX_PROCESSES)
                .mapToObj(n -> {
                    try {
                        return new ProcessBuilder("java", "-jar", JAR_PATH.toString()).start();
                    } catch (IOException e) {
                        throw new RuntimeException("I/O Exception while building processes", e);
                    }
                }).toList();
        List<PrintWriter> writers = processes.stream()
                .map(process -> new PrintWriter(process.getOutputStream(), true))
                .toList();
        List<BufferedReader> readers = processes.stream()
                .map(process -> new BufferedReader(new java.io.InputStreamReader(process.getInputStream())))
                .toList();

        // Communicating with the child processes
        try (java.nio.file.DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(FILES_PATH)) {
            java.util.Iterator<Path> it = directoryStream.iterator();
            while (it.hasNext()) {
                int counter = 0;
                for (PrintWriter printWriter : writers) {
                    if (!it.hasNext())
                        break;
                    printWriter.println(it.next());
                    counter++;
                }

                IntStream.range(0, counter)
                        .mapToObj(readers::get)
                        .map(reader -> {
                            try {
                                return reader.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException("I/O Exception while reading child stream", e);
                            }
                        }).forEach(System.out::println);
            }
        }

        // Close section
        readers.forEach(reader -> {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException("I/O Exception while closing readers", e);
            }
        });
        writers.forEach(PrintWriter::close);
        // There is no need of 'process.waitFor()'
        // as the processes will have terminated at this point.
        processes.forEach(Process::destroy);
    }

}

