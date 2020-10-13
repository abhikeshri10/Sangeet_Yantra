package Server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

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
                case 1:
                {
                    sample.ClientInfo ClientInfo = databaseHandler.loginClient(dataInputStream.readUTF(),dataInputStream.readUTF());
                    objectOutputStream.writeObject(ClientInfo);
                    break;
                }
                case 2:
                {
                    ClientInfo clientInfo = (ClientInfo) objectInputStream.readObject();
                    Boolean register = databaseHandler.createUser(clientInfo);
                    dataOutputStream.writeUTF(String.valueOf(register));
                    System.out.println("User Added");
                    break;
                }
                case 3:
                {
                    Boolean is_new = databaseHandler.new_login(dataInputStream.readUTF());

                    break;
                }
                case 4:
                {
                    SongInfo songinfo=databaseHandler.loadSong(dataInputStream.readUTF());
                    objectOutputStream.writeObject(songinfo);
                    if(songinfo!=null)
                        dataOutputStream.writeUTF(String.valueOf(true));
                    else
                        dataOutputStream.writeUTF(String.valueOf(false));
                    break;
                }
                case 5:
                {
                    List<String> test= databaseHandler.getList();
                    objectOutputStream.writeObject(test);
                    break;


                }
                case 6:
                {
                    List<String> ls = databaseHandler.getArtist();
                    objectOutputStream.writeObject(ls);
                    break;
                }
                case 7:
                {
                    List<String> genre = databaseHandler.getGenre();
                    objectOutputStream.writeObject(genre);
                    break;
                }
                case 8:
                {
                    int user_id = dataInputStream.read();
                    String artist = dataInputStream.readUTF();

                    String genre = dataInputStream.readUTF();

                    String language = dataInputStream.readUTF();
                    databaseHandler.setFeatures(user_id,artist,genre,language);
                    break;
                }
            }
        }
            catch (IOException | ClassNotFoundException e)
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
