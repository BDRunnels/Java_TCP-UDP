package dev.bdr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        
        try (Socket socket = new Socket("localhost", 5001)) { // 127.0.0.1 == localhost
            

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // any print statement will flush the buffer

            Scanner s = new Scanner(System.in);
            String requestString;
            String responseString;

            // always send at least one request (do loop)
            do {
                System.out.println("Enter string to be echoed (sent to server): ");
                requestString = s.nextLine();

                output.println(requestString);
                if (!requestString.equals("exit")) {
                    responseString = input.readLine();
                    System.out.println(responseString);
                }
            } while (!requestString.equals("exit"));
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            System.out.println("Client disconnected");
        }
    }
}
