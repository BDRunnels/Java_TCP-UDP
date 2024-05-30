package dev.bdr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleServer {
    // Purpose is to echo code back to client
    public static void main(String[] args) {
        
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            
            while (true) {
                Socket socket = serverSocket.accept(); // returns a socket, waits for client to initiate connection
                System.out.println("Server accepts client connection."); // see when client connects
                socket.setSoTimeout(900_000); // time client socket out for 20 seconds (input.readLine())
                executorService.submit(() -> handleClientRequest(socket));
            }
        } catch (IOException e) {
            System.err.println("Server exception " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket socket) {

        try (
            socket;
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // retrieve info from client
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // 2nd parameter whether to flush when println, printf, etc statement.
        ) {
            while (true) {
                String echoString = input.readLine();
                System.out.println("Server got request data: " + echoString);
                if (echoString.equals("exit")) {
                    break;
                }
                output.println("Echo from server: " + echoString);
            }
        } catch (Exception e) {
            System.out.println("Client socket shutdown");
        }
        
    }
}
