package sample;

import Server.*;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public Socket s;
    public int serverPort;
    public String serverIP;
    public boolean isConnected;
    public String username;
    public boolean connectToServer(String serverIP, int serverPort) {
        try {
            s = new Socket(serverIP, serverPort);

            dataOutputStream = new DataOutputStream(s.getOutputStream());
            objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            dataInputStream = new DataInputStream(s.getInputStream());
            objectInputStream = new ObjectInputStream(s.getInputStream());
            isConnected = true;
            System.out.println("Server Connected");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect to server.");
        }
        return false;
    }

    public boolean loginClient(String username ,String passwd) throws IOException {
         dataOutputStream.writeUTF(QueryType.login);
         dataOutputStream.writeUTF(username);
         dataOutputStream.writeUTF(passwd);
       if(Boolean.parseBoolean(dataInputStream.readUTF()))
       {
           this.username = username;
        return true;
       }
        return false;
    }
    public boolean isNewUser(String username) throws IOException
    {
        dataOutputStream.writeUTF(QueryType.isNewUser);
        dataOutputStream.writeUTF(username);
        return Boolean.parseBoolean(dataInputStream.readUTF());
    }
    public boolean createClient(ClientInfo clientInfo)
    {
        try {
            dataOutputStream.writeUTF(QueryType.register);
            objectOutputStream.writeObject(clientInfo);

            return Boolean.parseBoolean(dataInputStream.readUTF());
        }
        catch(IOException e)
        {

            return false;
        }
    }
    @Override
    public void run() {

    }
}
