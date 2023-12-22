package com.quathar.psp.exam.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * <h1>Server</h1>
 * <br>
 * See type1.pdf and type2.pdf [Ejercicio 3] to understand the requirements.
 *
 * @since 2022-11-22
 * @author Q
 * @version 1.0
 */
public class Server {

    // <<-CONSTANTS->>
    private static final String MSG_1 = "type t for time, type d for date";
    private static final String MSG_2 = "please write a number";

    // <<-METHODS->>
    private static void type1(BufferedReader socketIn, PrintWriter socketOut) throws IOException {
        String input;
        socketOut.println(MSG_1);
        while ((input = socketIn.readLine()) != null)
            if (input.startsWith("t"))
                 socketOut.println(LocalTime.now());
            else if (input.startsWith("d"))
                 socketOut.println(LocalDate.now());
            else socketOut.println(MSG_1);
    }

    private static void type2(BufferedReader socketIn, PrintWriter socketOut) throws IOException {
        String input;
        Random random = new Random();
        socketOut.println(MSG_2);
        while ((input = socketIn.readLine()) != null)
            try {
                socketOut.println(random.nextInt(Integer.parseInt(input) + 1));
            } catch (NumberFormatException e) {
                socketOut.println(MSG_2);
            }

    }

    // <<-MAIN METHOD->>
    public static void main(String[] args) {
        int portNumber = 8080;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try (
                            BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(), true)
                    ) {
                        socketOut.println("Which exercise do you want to execute? [ Time and Date = -t1 | Random number = -t2 ]");
                        switch (socketIn.readLine()) {
                            case "-t1" -> type1(socketIn, socketOut);
                            case "-t2" -> type2(socketIn, socketOut);
                            default    -> socketOut.println("E R R O R: Incorrect option");
                        }
                    } catch (IOException e) {
                        System.err.println("E R R O R: I/O");
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("E R R O R: The connection couldn't be made");
        }
    }

}
