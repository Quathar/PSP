package com.quathar.psp.sockets.downloads;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileDownloader {

    // <<-FIELDS->>
    protected final URL url;
    protected final Path path;

    // <<-CONSTRUCTOR->>
    public FileDownloader(String url, String first, String... more) throws java.net.MalformedURLException {
        Path testPath = Paths.get(first, more);
        if (!testPath.isAbsolute())
             testPath = Paths.get(System.getProperty("user.home"), "downloads", testPath.toString());
        this.path = testPath;
        this.url = new URL(url);
    }

    public abstract void download() throws java.io.IOException;

}
