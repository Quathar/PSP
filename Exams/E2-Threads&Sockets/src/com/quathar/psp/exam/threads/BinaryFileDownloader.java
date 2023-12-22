package com.quathar.psp.exam.threads;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;

public class BinaryFileDownloader extends Thread {

    // <<-CONSTANT->>
    private static final int BUFFER_SIZE = 2048;

    // <<-FIELDS->>
    private URL url;
    private Path path;

    // <<-CONSTRUCTOR->>
    public BinaryFileDownloader(String url, String fileName) {
        try {
            this.url = new URL(url);
            this.path = Path.of(System.getProperty("user.home"), "downloads", fileName);
        } catch (java.net.MalformedURLException e) {
            System.err.println("E R R O R: The URL is unreachable or it doesn't exist");
        }
    }

    // <<-METHOD->>
    @Override
    public void run() {
        try (
                java.io.InputStream inputStream = this.url.openStream();
                FileOutputStream fileOutputStream = new FileOutputStream(this.path.toString())
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytes;
            while ((bytes = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1)
                fileOutputStream.write(buffer, 0, bytes);
        } catch ( java.io.IOException e) {
            System.err.println("E R R O R: I/O");
        }
    }

}
