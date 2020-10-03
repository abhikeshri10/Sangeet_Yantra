package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.sql.*;

public class Server {

    public static void main(String args[]) {
        Database.getConnection();
        ServerSocket serverSocket;
        Socket socket;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(5436);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Client Connected");
                Thread t = new Thread(new HandleQuery(socket));//listen message from client
                t.start();

            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }
}
