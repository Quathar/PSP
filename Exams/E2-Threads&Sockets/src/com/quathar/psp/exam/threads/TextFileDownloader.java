package com.quathar.psp.exam.threads;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;

public class TextFileDownloader extends Thread {

    // <<-FIELDS->>
    private URL url;
    private Path path;

    // <<-CONSTRUCTOR->>
    public TextFileDownloader(String url) {
        try {
            this.url = new URL(url);
            this.path = Path.of(System.getProperty("user.home"), "downloads", url.substring(url.lastIndexOf("/")));
        } catch (java.net.MalformedURLException e) {
            System.err.println("E R R O R: The URL is unreachable or it doesn't exist");
        }
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        try (
                BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(this.url.openStream()));
                PrintWriter printWriter = new PrintWriter(new java.io.FileWriter(this.path.toString()), true)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                printWriter.println(line);
        } catch (java.io.IOException e) {
            System.err.println("E R R O R: I/O");
        }
    }

}
