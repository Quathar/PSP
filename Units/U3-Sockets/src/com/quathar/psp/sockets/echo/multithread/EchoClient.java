package com.quathar.psp.sockets.echo.multithread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * <h1>Echo Client</h1>
 *
 * @since 2022-11-XX
 * @version 1.0
 * @author Q
 */
public class EchoClient {

    public static void main(String[] args) throws java.io.IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoClient <port number>");
            System.exit(1);
        }

        InetAddress inetAddress = InetAddress.getLocalHost();
        int portNumber = Integer.parseInt(args[0]);
        try (
                Socket         socket    = new Socket(inetAddress, portNumber);
                PrintWriter    socketOut = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader socketIn  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn     = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                socketOut.println(userInput);
                System.out.println("echo: " + socketIn.readLine());
            }
        }
    }

}
