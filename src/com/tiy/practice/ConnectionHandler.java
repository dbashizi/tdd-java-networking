package com.tiy.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dbashizi on 12/15/16.
 */
public class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    // this is how we read from the client
    private BufferedReader inputFromClient;
    private PrintWriter outputToClient;

    public ConnectionHandler(Socket clientSocket,
                             BufferedReader inputFromClient,
                             PrintWriter outputToClient) {
        this.clientSocket = clientSocket;
        this.inputFromClient = inputFromClient;
        this.outputToClient = outputToClient;
    }

    public void run() {
        try {
            // display information about who just connected to our server
            System.out.println("Ready for messages from " + clientSocket.getInetAddress().getHostAddress());

            // read from the input until the client disconnects
            String inputLine;
            while ((inputLine = inputFromClient.readLine()) != null) {
                System.out.println("Received message: " + inputLine + " from " + clientSocket.toString());
                if (inputLine.equals(SimpleServer.CONNECTION_END)) {
                    System.out.println("**** Closing this connection");
                    outputToClient.println("!!! Closing this connection !!!");
                    break;
                } else {
                    // this is where I do actual work with the message from the client
                    outputToClient.println("ECHO::" + inputLine);
                }
            }
            System.out.println("**** Connection Handler closing!");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
