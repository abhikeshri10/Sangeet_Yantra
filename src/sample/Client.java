package sample;

import Server.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

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
    public static ClientInfo clientInfo;
    public static SongInfo songInfo;
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

    public ClientInfo loginClient(String username ,String passwd) throws IOException, ClassNotFoundException {
         dataOutputStream.writeUTF(QueryType.login);
         dataOutputStream.writeUTF(username);
         dataOutputStream.writeUTF(passwd);
         clientInfo = (ClientInfo) objectInputStream.readObject();
         return clientInfo;
    }

    /**
     *
     * @param username
     * @throws IOException
     */
    public void isNewUser(String username)
    {   try{
        dataOutputStream.writeUTF(QueryType.isNewUser);
        dataOutputStream.writeUTF(username);}
        catch (IOException e)
        {
            System.out.println("update error");
        }

    }

    /**
     *
     * @param clientInfo
     * @return
     */
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

    /**
     *
     * @return
     */
    public List<String> getArtist()
    {
        try{
            dataOutputStream.writeUTF(QueryType.getArtist);
            List<String> ls = (List<String>) objectInputStream.readObject();
            return ls;
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null;
        }
    }
    public List<String> getGenre()
    {
        try{
            dataOutputStream.writeUTF(QueryType.getGenre);
            List<String> ls = (List<String>) objectInputStream.readObject();
            List<String> newList = ls.stream().distinct().collect(Collectors.toList());

            return newList;
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null;
        }
    }


    public void setFeatures(int user_id, String artist, String genre, String language) {
        try{
            dataOutputStream.writeUTF(QueryType.setFeatures);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(artist);
            dataOutputStream.writeUTF(genre);
            dataOutputStream.writeUTF(language);
        }
        catch(IOException e)
        {
            System.out.println("Features insertion error");
        }
    }
    public boolean playSong(String text)
    {
        try {
            dataOutputStream.writeUTF(QueryType.playSong);
            dataOutputStream.writeUTF(text);
            songInfo=(SongInfo) objectInputStream.readObject();
            return Boolean.parseBoolean(dataInputStream.readUTF());
        }
        catch(IOException| ClassNotFoundException e)
        {
            System.out.println("Error in Playing song");
            return false;
        }

    }
    @Override
    public void run() {

    }

    public List<String> getSongs() {
        try {
            dataOutputStream.writeUTF(QueryType.getSong);
            List<String> list1 =(List<String>) objectInputStream.readObject();
            // System.out.println(list1);
            return list1;
        }
        catch(Exception e)
        {
            return null;

        }


    }
}
