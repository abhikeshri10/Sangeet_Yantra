package Server;

import sample.ClientMain;
import sample.Song;

import java.io.*;
import java.net.Socket;

public class HandleClientSong implements Runnable {
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
    Thread t2;

    public HandleClientSong(StartServer startServer, Socket socket) throws IOException {
        this.startServer = startServer;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            databaseHandler=new DatabaseHandler();

        }
        catch (IOException e)
        {   this.socket.close();

            e.printStackTrace();
        }

        t2 = new Thread(this);
        t2.start();
    }


    private void performActions() {
        while (true)
        {
            try {
            switch(Integer.parseInt(dataInputStream.readUTF())) {
                case 36:
                {

                    String songname =dataInputStream.readUTF();
                   objectOutputStream.writeObject(databaseHandler.setsongfile(songname));
                   break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error in sending songs");
            e.printStackTrace();
            t2.stop();
        }
        }
    }
    @Override
    public void run() {

        performActions();

    }


}
