package com.quathar.psp.sockets.echo.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * <h1>Echo Server</h1>
 *
 * @since 2022-11-XX
 * @version 1.0
 * @author Q
 */
public class EchoServer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (ServerSocket echoSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = echoSocket.accept();
                new Thread(() -> {
                    try (
                            PrintWriter    socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                            BufferedReader socketIn  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    ) {
                        String inputLine;
                        while ((inputLine = socketIn.readLine()) != null)
                            socketOut.println(inputLine);
                    } catch (SocketException sE) {
                        System.out.println("A Client has leave");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException ignored) {}
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
