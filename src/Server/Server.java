package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashSet;
import java.util.Set;


public class Server implements Runnable{
    protected Set<HandleClient> handleClients = new HashSet<HandleClient>();
    public  ServerSocket serverSocket ;
    StartServer startServer;
    Socket socket;
    public Server(StartServer startServer, int port) {
        this.startServer = startServer;
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();

    }


    @Override
    public void run() {


        while(true)
        {
            try {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                HandleClient handleClient = new HandleClient(startServer,socket);
                startServer.handleClients.add(handleClient);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
