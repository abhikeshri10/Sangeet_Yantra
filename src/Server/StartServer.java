package Server;
import java.util.HashSet;
import java.util.Set;
public class StartServer {
    protected Set<HandleClient> handleClients = new HashSet<HandleClient>();
    public Server server;
    StartServer()
    {

    }

    public void beginServer(int port) {
        server = new Server(this, port);

    }

    public static void main(String[] args)
    {
        System.out.println("Server starting on port 6005......");

        StartServer startServer = new StartServer();
        //start server on port 5436
        startServer.beginServer(6005);
        System.out.println("Server started");

    }
}
