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
    Thread t;
    public HandleClient(StartServer startServer, Socket socket) throws IOException {
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
        {   this.socket.close();

            e.printStackTrace();
        }

         t = new Thread(this);
        t.start();
    }

    /**
     * performing various action with the queries from the client
     */
    private void performActions(){
        while (true)
        {   try{

            switch(Integer.parseInt(dataInputStream.readUTF())) {
                case 1: {
                    sample.ClientInfo ClientInfo = databaseHandler.loginClient(dataInputStream.readUTF(), dataInputStream.readUTF());
                    objectOutputStream.writeObject(ClientInfo);
                    break;
                }
                case 2: {
                    ClientInfo clientInfo = (ClientInfo) objectInputStream.readObject();
                    Boolean register = databaseHandler.createUser(clientInfo);
                    dataOutputStream.writeUTF(String.valueOf(register));
                    System.out.println("User Added");
                    break;
                }
                case 3: {
                    Boolean is_new = databaseHandler.new_login(dataInputStream.readUTF());

                    break;
                }
                case 4: {
                    SongInfo songinfo = databaseHandler.loadSong(dataInputStream.readUTF());
                    objectOutputStream.writeObject(songinfo);
                    if (songinfo != null)
                        dataOutputStream.writeUTF(String.valueOf(true));
                    else
                        dataOutputStream.writeUTF(String.valueOf(false));
                    break;
                }
                case 5:
                {
                    int userid = dataInputStream.read();
                    List<String> test= databaseHandler.getList(userid);
                    objectOutputStream.writeObject(test);
                    break;


                }
                case 6: {
                    List<String> ls = databaseHandler.getArtist();
                    objectOutputStream.writeObject(ls);
                    break;
                }
                case 7: {
                    List<String> genre = databaseHandler.getGenre();
                    objectOutputStream.writeObject(genre);
                    break;
                }
                case 8: {
                    int user_id = dataInputStream.read();
                    String artist = dataInputStream.readUTF();

                    String genre = dataInputStream.readUTF();

                    String language = dataInputStream.readUTF();
                    databaseHandler.setFeatures(user_id, artist, genre, language);
                    break;
                }
                case 9: {
                    int userid = dataInputStream.read();
                    List<String> playlist = databaseHandler.getPlaylist(userid);
                    objectOutputStream.writeObject(playlist);
                    break;
                }
                case 10: {
                    int userid = dataInputStream.read();
                    String playlistName = dataInputStream.readUTF();

                    boolean status = databaseHandler.createPlaylist(userid, playlistName);
                    dataOutputStream.writeBoolean(status);
                    break;

                }
                case 11: {
                    String playlist = dataInputStream.readUTF();
                    String song = dataInputStream.readUTF();
                    System.out.println(playlist);
                    System.out.println(song);
                    boolean status = databaseHandler.addSongToPlaylist(playlist, song);
                    dataOutputStream.writeBoolean(status);
                    break;
                }
                case 12: {
                    List<String> album = databaseHandler.getAlbum();
                    objectOutputStream.writeObject(album);
                    break;
                }
                case 13: {
                    int userid = dataInputStream.read();
                    List<String> history = databaseHandler.getHistory(userid);
                    objectOutputStream.writeObject(history);
                    break;
                }
                case 14: {
                    int userid = dataInputStream.read();
                    int songid = dataInputStream.read();
                    databaseHandler.addHistory(userid, songid);
                    break;
                }
                case 15: {
                    String groupName = dataInputStream.readUTF();
                    int userid = dataInputStream.read();
                    boolean status = databaseHandler.createGroup(groupName, userid);
                    dataOutputStream.writeBoolean(status);
                    break;
                }
                case 16: {
                    int userid = dataInputStream.read();
                    List<String> getGroupsCreated = databaseHandler.getGroupsCreated(userid);
                    objectOutputStream.writeObject(getGroupsCreated);
                    break;
                }
                case 17: {
                    List<String> getUsers = databaseHandler.getUsers();
                    objectOutputStream.writeObject(getUsers);
                    break;
                }
                case 18: {
                    String groupName = dataInputStream.readUTF();
                    String userName = dataInputStream.readUTF();
                    boolean status = databaseHandler.addUserToGroup(groupName, userName);
                    dataOutputStream.writeBoolean(status);
                    break;
                }
                case 19:
                {
                    int userid = dataInputStream.read();
                    List<String> getGroupsCreated = databaseHandler.getAllGroups(userid);
                    objectOutputStream.writeObject(getGroupsCreated);
                    break;
                }
                case 20:
                    {
                    int userid = dataInputStream.read();
                    objectOutputStream.writeObject(databaseHandler.getGroupPlaylist(userid));
                    break;
                   }
                case 21:
                {
                    String groupname = dataInputStream.readUTF();
                    String playlistName = dataInputStream.readUTF();
                    dataOutputStream.writeBoolean(databaseHandler.addPlaylisttoGroup(groupname,playlistName));
                    break;
                }
                case 22:
                {

                    int userid=dataInputStream.read();
                    databaseHandler.settoDefault(userid);
                    break;

                }
                case 23:
                {
                    int userid= dataInputStream.read();
                    String albumname=dataInputStream.readUTF();
                    databaseHandler.getalbumSongs(albumname,userid);
                    break;

                }
                case 24:
                {
                    List<String> test= databaseHandler.getAllsongs();
                    objectOutputStream.writeObject(test);
                    break;

                }
                case 25:
                {
                    int userid= dataInputStream.read();
                    String artistname=dataInputStream.readUTF();
                    databaseHandler.getArtistsongs(artistname,userid);
                    break;
                }
                case 26:
                {
                    int userid= dataInputStream.read();
                    String playlistname=dataInputStream.readUTF();
                    databaseHandler.getPlaylistsongs(playlistname,userid);
                    break;

                }
                case 27:
                {
                    List<String> newQueue = (List<String>) objectInputStream.readObject();
                    int user_id = dataInputStream.read();
                    databaseHandler.modifyQueue(newQueue,user_id);
                    break;
                }
                case 28:
                {   int user_id = dataInputStream.read();
                    String playlistname=dataInputStream.readUTF();
                    databaseHandler.getGroupPlaylistsongs(playlistname,user_id);
                    break;
                }
            }

        }
            catch ( ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException ioe)
            {
                t.stop();
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
