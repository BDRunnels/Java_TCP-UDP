package dev.bdr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    // Purpose is to echo code back to client
    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            
            try (Socket socket = serverSocket.accept()) { // returns a socket, waits for client to initiate connection
                System.out.println("Server accepts client connection."); // see when client connects

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // retrieve info from client

                PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // 2nd parameter whether to flush when println, printf, etc statement.

                while (true) {
                    String echoString = input.readLine();
                    System.out.println("Server got request data: " + echoString);
                    if (echoString.equals("exit")) {
                        break;
                    }
                    output.println("Echo from server: " + echoString);
                }
            }             

        } catch (IOException e) {
            System.err.println("Server exception " + e.getMessage());
        }
    }
}
