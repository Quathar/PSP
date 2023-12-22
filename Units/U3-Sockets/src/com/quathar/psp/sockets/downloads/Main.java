package com.quathar.psp.sockets.downloads;

import java.io.IOException;

/**
 * <h1>Main</h1>
 *
 * @since 2022-11-XX
 * @version 1.0
 * @author Q
 */
public class Main {

    public static void main(String[] args) {
        try {
            FileDownloader fileDownloader;

            // Downloads a file to the user's download folder since the destination is not absolute
            fileDownloader = new TextFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", "url.html");
            fileDownloader.download();

            // Downloads a file to a specific path
            fileDownloader = new TextFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", System.getProperty("user.home"), "downloads", "url1.html");
            fileDownloader.download();

            // Downloads a binary file which can't be opened
            fileDownloader = new TextFileDownloader("https://github-media-downloads.s3.amazonaws.com/Octocats.zip", "Octocats_wrong.zip");
            fileDownloader.download();

            // Downloads a binary file with the right downloader
            fileDownloader = new BinaryFileDownloader("https://github-media-downloads.s3.amazonaws.com/Octocats.zip", "Octocats_right.zip");
            fileDownloader.download();

            // Works for text too, but with the wrong line endings
            fileDownloader = new BinaryFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", System.getProperty("user.home"), "downloads", "url2.html");
            fileDownloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
