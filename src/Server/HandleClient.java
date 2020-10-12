package Server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import sample.*;
public class HandleClient implements Runnable{
    private StartServer startServer;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private DatabaseHandler databaseHandler;
    /**
    * Creating the Handle client constructor
     * @param startServer to get the start server instance
     * @param socket to get the socket of client
    * */

    public HandleClient(StartServer startServer, Socket socket) {
        this.startServer = startServer;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            databaseHandler = new DatabaseHandler();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * performing various action with the queries from the client
     */
    private void performActions(){
        while (true)
        {   try{

            switch(Integer.parseInt(dataInputStream.readUTF())) {
<<<<<<< HEAD
                case 1: {
                    Boolean isValidClient = databaseHandler.loginClient(dataInputStream.readUTF(), dataInputStream.readUTF());
                    dataOutputStream.writeUTF(String.valueOf(isValidClient));
                    break;
                }
                case 2: {
                    ClientInfo clientInfo = (ClientInfo) objectInputStream.readObject();
                    Boolean register = databaseHandler.createUser(clientInfo);
                    dataOutputStream.writeUTF(String.valueOf(register));
                    System.out.println("User Added");
                    break;
                }



=======
                case 1:
                {
                    Boolean isValidClient = databaseHandler.loginClient(dataInputStream.readUTF(),dataInputStream.readUTF());
                    dataOutputStream.writeUTF(String.valueOf(isValidClient));
                    break;
                }
                case 2:
>>>>>>> 7efa4ce46b8c3d7719ce52011bfd6bf753f37b01

                case 3:
                {
                    Boolean is_new = databaseHandler.new_login(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(String.valueOf(is_new));
                    break;
                }

            }
        }
<<<<<<< HEAD
            catch (IOException | ClassNotFoundException e)
=======
            catch (IOException | SQLException e)
>>>>>>> 7efa4ce46b8c3d7719ce52011bfd6bf753f37b01
            {
                e.printStackTrace();
            }
        }
    }
    /**
     * override run method
     * */
    @Override
    public void run() {

            performActions();

    }
}
