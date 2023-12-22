package com.quathar.psp.sockets.echo.monothread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <h1>Echo Server</h1>
 *
 * @since 2022-11-XX
 * @version 1.0
 * @author Q
 */
public class EchoServer {

    public static void main(String[] args) throws java.io.IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (
            ServerSocket   echoSocket   = new ServerSocket(portNumber);
            Socket         clientSocket = echoSocket.accept();
            PrintWriter    socketOut    = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketIn     = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = socketIn.readLine()) != null)
                socketOut.println(inputLine);
        }
    }

}
