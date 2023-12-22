package com.quathar.psp.sockets.downloads;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class TextFileDownloader extends FileDownloader {

    // <<-CONSTRUCTOR->>
    public TextFileDownloader(String url, String first, String... more) throws java.net.MalformedURLException {
        super(url, first, more);
    }

    // <<-OVERRIDE->>
    @Override
    public void download() throws java.io.IOException {
        try (
                BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(this.url.openStream()));
                PrintWriter    printWriter    = new PrintWriter(new java.io.FileWriter(this.path.toString()), true)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                printWriter.println(line);
        }
    }
}
