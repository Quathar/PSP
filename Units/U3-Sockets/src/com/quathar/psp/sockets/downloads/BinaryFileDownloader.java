package com.quathar.psp.sockets.downloads;

import java.io.FileOutputStream;

public class BinaryFileDownloader extends FileDownloader {

    // <<-CONSTANT->>
    private static final int NUM_BUFFER = 2048;

    // <<-CONSTRUCTOR->>
    public BinaryFileDownloader(String url, String first, String... more) throws java.net.MalformedURLException {
        super(url, first, more);
    }

    // <<-OVERRIDE->>
    @Override
    public void download() throws java.io.IOException {
        byte[] buffer = new byte[NUM_BUFFER];
        int bytes;

        try (
                java.io.InputStream inputStream = this.url.openStream();
                // To write binary files
                FileOutputStream fileOutputStream = new FileOutputStream(this.path.toString())
        ) {
            while ((bytes = inputStream.read(buffer, 0, NUM_BUFFER)) != -1)
                fileOutputStream.write(buffer, 0, bytes);
        }
    }

}
