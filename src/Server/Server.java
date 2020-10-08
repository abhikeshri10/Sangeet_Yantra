package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public  static Connection cnx = null;
    public static ServerSocket serverSocket = null;
    public static Socket socket = null;

    public static void main(String args[]) {
        cnx=Database.getConnection();
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
                Thread t2 = new Thread(new SendResponse(socket));//send response to the client
                t2.start();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}
