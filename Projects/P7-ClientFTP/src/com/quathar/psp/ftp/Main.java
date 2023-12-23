package com.quathar.psp.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * <h1>FTP (File Transfer Protocol) Client</h1>
 * <br>
 * [Open attached PDF to view the statement]
 * <br>
 * In general terms:
 * <ul>
 *     <li>A FTP server is required</li>
 *     <li>Modify the user's name and password</li>
 *     <li>The program works with the current directory structure<br>
 *     The remote folder is in "/users/'x'/downloads/ftp/'y'"<br>
 *     where 'x' = your user on real machine<br>
 *     and 'y' = your remote user on FTP server</li>
 * </ul>
 *
 * @since 2022-11-29
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final String USER = "Q";
    private static final String PASSWD = "psp";

    // <<-METHODS->>
    private static void recursiveDelete(FTPClient ftpClient, String directory) throws IOException {
        System.out.printf("Deleting remote folder: %s%n", directory);
        ftpClient.changeWorkingDirectory(directory);
        for (FTPFile file : ftpClient.listFiles())
            if (file.isFile()) {
                System.out.printf("Deleting remote file: %s%n", file.getName());
                ftpClient.deleteFile(file.getName());
            } else recursiveDelete(ftpClient, file.getName());
        ftpClient.changeToParentDirectory();
        ftpClient.removeDirectory(directory);
    }

    public static void main(String[] args) throws IOException {
        // 1. Creates an instance of the FTPClient class
        FTPClient ftpClient = new FTPClient();

        // 2. Connect to localhost
        ftpClient.connect(java.net.InetAddress.getLocalHost());

        // 3. Check if the connection is correct
        int replyCode = ftpClient.getReplyCode();
        if (!org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(replyCode)) {
            System.err.printf("ERROR: Unable to connect to the server [code %s]", replyCode);
            ftpClient.disconnect();
            System.exit(replyCode);
        }

        // 4. Switches to passive mode
        ftpClient.enterLocalPassiveMode();

        // 5. Login with username and password
        ftpClient.login(USER, PASSWD);

        // 6. Prints the name of the current remote directory
        System.out.printf("Current remote folder is %s%n", ftpClient.printWorkingDirectory());

        // 7. Deletes all directories that descend from the current remote directory
        for (FTPFile folder : ftpClient.listDirectories())
            recursiveDelete(ftpClient, folder.getName());

        // 8. Prints on screen the list of all files in the current remote directory
        FTPFile[] files = ftpClient.listFiles();
        if (files.length != 0)
            for (FTPFile file : files)
                System.out.println(file);
        else System.out.println("No files");

        // 9. Create a directory with current date and time format 'YYYYYYMMddHHmmss'
        String newDirectory = java.time.format.DateTimeFormatter.ofPattern("YYYYMMddHHmmss")
                .format(java.time.LocalDateTime.now());
        ftpClient.makeDirectory(newDirectory);

        // 10. For each file in the remote folder, move it to the new one
        for (FTPFile file : ftpClient.listFiles()) {
            System.out.printf("Moving remote file %s to remote folder %s%n", file, newDirectory);
            ftpClient.rename(
                    file.getName(),
                    String.join("/", newDirectory, file.getName())
            );
        }

        // 11. Change remote directory to the directory you have created
        ftpClient.changeWorkingDirectory(newDirectory);
        System.out.printf("Current remote folder is %s%n", ftpClient.printWorkingDirectory());

        // 12. Prints on screen the list of all files in the current remote directory
        for (FTPFile file : ftpClient.listFiles())
            System.out.println(file);

        // 13. Changes from remote directory to parent directory and prints the name of the current remote directory
        ftpClient.changeToParentDirectory();
        System.out.printf("Current remote folder is %s%n", ftpClient.printWorkingDirectory());

        // 14. Upload all files in the local project files folder to the FTP server
        Path path = Path.of(System.getProperty("user.dir"), "files");
        try (java.nio.file.DirectoryStream<Path> ds = java.nio.file.Files.newDirectoryStream(path)) {
            for (Path file : ds) {
                System.out.printf("Uploading local file %s%n", file.getFileName());
                try ( java.io.InputStream is = new java.io.FileInputStream(file.toString())) {
                    ftpClient.storeFile(file.getFileName().toString(), is);
                }
            }
        }

        // 15. Prints on screen the list of all files in the current remote directory
        System.out.printf("Listing files on remote folder %s%n", ftpClient.printWorkingDirectory());
        for (FTPFile file : ftpClient.listFiles())
            System.out.println(file);

        // 16. Logout
        ftpClient.logout();

        ftpClient.disconnect();
    }

}

