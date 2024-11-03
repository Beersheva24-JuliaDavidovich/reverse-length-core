package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Reverse-Length Server started on port " + PORT);
        
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
             
            String type;
            while ((type = reader.readLine()) != null) {
                String input = reader.readLine();
                String response;

                if ("1".equals(type)) {
                    response = new StringBuilder(input).reverse().toString();
                } else if ("2".equals(type)) {
                    response = String.valueOf(input.length());
                } else {
                    response = "Unknown request type";
                }
                
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection unexpectedly: " + e.getMessage());
        }
    }
}