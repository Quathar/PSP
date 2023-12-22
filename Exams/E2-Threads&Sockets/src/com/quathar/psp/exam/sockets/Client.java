package com.quathar.psp.exam.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * <h1>Client</h1>
 * <br>
 * See type1.pdf and type2.pdf [Ejercicio 3] to understand the requirements.
 *
 * @since 2022-11-22
 * @author Q
 * @version 1.0
 */
public class Client {

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getLocalHost();
        int portNumber = 8080;

        Socket socket = new Socket(address, portNumber);
        try (
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            Thread stdThread = new Thread(() -> {
                try {
                    String stdInput;
                    while (!"/exit".equalsIgnoreCase(stdInput = stdIn.readLine()))
                        socketOut.println(stdInput);
                } catch (IOException e) {
                    System.err.println("E R R O R: stdThread -> I/O");
                }
            });

            Thread nwkThread = new Thread(() -> {
                try {
                    String nwkInput;
                    while ((nwkInput = socketIn.readLine()) != null)
                        System.out.println(nwkInput);
                } catch (IOException e) {
                    System.out.println("S Y S T E M: bye!");
                }
            });

            stdThread.start();
            nwkThread.start();

            stdThread.join();
            socket.close();
            nwkThread.join();
        }
    }

}
