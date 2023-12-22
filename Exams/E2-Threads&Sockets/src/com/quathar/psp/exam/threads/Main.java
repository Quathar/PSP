package com.quathar.psp.exam.threads;

import java.io.BufferedReader;
import java.util.List;

/**
 * <h1>Main</h1>
 * <br>
 * See type1.pdf and type2.pdf [Ejercicio 1] to understand the requirements.
 *
 * @since 2022-11-22
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-METHODS->>
    private static void type1() {
        List<String> urls = List.of(
                "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/InputStream.html",
                "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/OutputStream.html",
                "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Flushable.html",
                "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Closeable.html"
        );

        List<TextFileDownloader> textFileDownloader = urls.stream()
                .map(TextFileDownloader::new)
                .toList();
        textFileDownloader.forEach(Thread::start);

        textFileDownloader.forEach(downloader -> {
            try {
                downloader.join();
            } catch (InterruptedException e) {
                System.err.println("E R R O R: Join");
            }
        });
        System.out.println("TYPE 1 -> D O N E!");
    }

    private static void type2() {
        String urlBase = "https://httpbin.org/image/";
        List<String> urls = List.of("jpeg", "png", "svg", "webp");

        List<BinaryFileDownloader> binaryFileDownloader = urls.stream()
                .map(url -> new BinaryFileDownloader(urlBase + url, String.format("file.%s", url)))
                .toList();
        binaryFileDownloader.forEach(Thread::start);

        binaryFileDownloader.forEach(downloader -> {
            try {
                downloader.join();
            } catch (InterruptedException e) {
                System.err.println("E R R O R: Join");
            }
        });
        System.out.println("TYPE 2 -> D O N E!");
    }

    public static void main(String[] args) throws java.io.IOException {
        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            System.out.println("Which exercise do you want to execute? [ -t1 | TextDownloader ] [ -t2 | BinaryDownloader ]");
            switch (br.readLine()) {
                case "-t1" -> type1();
                case "-t2" -> type2();
                default    -> System.out.println("E R R O R: Incorrect option");
            }
        }
    }

}
