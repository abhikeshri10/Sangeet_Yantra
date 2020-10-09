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
                HandleQuery handleQuery = new HandleQuery(socket);//creating an instance of the recieve end
                SendResponse sendResponse = new SendResponse(socket);//creating an instance of the send end

                Thread t = new Thread(handleQuery);//listen message from client
                t.start();
                Thread t2 = new Thread(sendResponse);//send response to the client
                t2.start();

                handleQuery.setSendResponse(sendResponse);
                sendResponse.setHandleQuery(handleQuery);
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
