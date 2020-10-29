package sample;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Client implements Runnable {
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
    public static String Downloadpath;
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

    public ClientInfo loginClient(String username, String passwd) throws IOException, ClassNotFoundException {
        dataOutputStream.writeUTF(QueryType.login);
        dataOutputStream.writeUTF(username);
        dataOutputStream.writeUTF(passwd);
        clientInfo = (ClientInfo) objectInputStream.readObject();
        return clientInfo;
    }

    /**
     * @param username
     * @throws IOException
     */
    public void isNewUser(String username) {
        try {
            dataOutputStream.writeUTF(QueryType.isNewUser);
            dataOutputStream.writeUTF(username);
        } catch (IOException e) {
            System.out.println("update error");
        }

    }

    /**
     * @param clientInfo
     * @return
     */
    public boolean createClient(ClientInfo clientInfo) {
        try {
            dataOutputStream.writeUTF(QueryType.register);
            objectOutputStream.writeObject(clientInfo);

            return Boolean.parseBoolean(dataInputStream.readUTF());
        } catch (IOException e) {

            return false;
        }
    }

    /**
     * @return
     */
    public List<String> getArtist() {
        try {
            dataOutputStream.writeUTF(QueryType.getArtist);
            List<String> ls = (List<String>) objectInputStream.readObject();
            return ls;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public List<String> getGenre() {
        try {
            dataOutputStream.writeUTF(QueryType.getGenre);
            List<String> ls = (List<String>) objectInputStream.readObject();
            List<String> newList = ls.stream().distinct().collect(Collectors.toList());

            return newList;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }


    public void setFeatures(int user_id, String artist, String genre, String language) {
        try {
            dataOutputStream.writeUTF(QueryType.setFeatures);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(artist);
            dataOutputStream.writeUTF(genre);
            dataOutputStream.writeUTF(language);
        } catch (IOException e) {
            System.out.println("Features insertion error");
        }
    }

    public boolean playSong(String text) {
        try {
            dataOutputStream.writeUTF(QueryType.playSong);
            dataOutputStream.writeUTF(text);
            songInfo = (SongInfo) objectInputStream.readObject();
            this.addHistory(clientInfo.user_id, songInfo.id);
            this.addPlayCount(songInfo.id);
            return Boolean.parseBoolean(dataInputStream.readUTF());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in Playing song");
            return false;
        }

    }

    private void addPlayCount(int songid) {
        try{
            dataOutputStream.writeUTF(QueryType.addPlayCount);

            dataOutputStream.write(songid);

        }
        catch (IOException e)
        {
            System.out.println("Playcount insertion failed");
        }

    }

    private void addHistory(int user_id, int songid) {
        try {
            dataOutputStream.writeUTF(QueryType.addHistory);
            dataOutputStream.write(user_id);
            dataOutputStream.write(songid);
        } catch (IOException e) {
            System.out.println("History addition failed");
        }
    }

    @Override
    public void run() {

    }

    public List<String> getSongs(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getSong);
            dataOutputStream.write(user_id);
            clientInfo.setQueue((List<String>) objectInputStream.readObject());
            // System.out.println(list1);
            return clientInfo.getQueue();
        } catch (Exception e) {
            return null;

        }
    }

    public List<String> getPlaylist(int userid) {
        try {
            dataOutputStream.writeUTF(QueryType.getPlaylist);
            dataOutputStream.write(userid);
            List<String> list1 = (List<String>) objectInputStream.readObject();
            // System.out.println(list1);
            return list1;
        } catch (Exception e) {
            return null;

        }
    }

    public boolean createPlaylist(int user_id, String playlistName) {
        try {
            dataOutputStream.writeUTF(QueryType.createPlaylist);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(playlistName);

            return dataInputStream.readBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addSongToPlaylist(String playlist, String song) {

        try {
            dataOutputStream.writeUTF(QueryType.addSongToPlaylist);
            dataOutputStream.writeUTF(playlist);
            dataOutputStream.writeUTF(song);
            return dataInputStream.readBoolean();
        } catch (Exception e) {
            return false;
        }

    }

    public List<String> getAlbum() {
        try {
            dataOutputStream.writeUTF(QueryType.getAlbum);

            List<String> list1 = (List<String>) objectInputStream.readObject();
            // System.out.println(list1);
            return list1;
        } catch (Exception e) {
            return null;

        }
    }

    public List<String> getHistory(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getHistory);
            dataOutputStream.write(user_id);
            List<String> history = (List<String>) objectInputStream.readObject();
            return history;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean createGroup(String groupName, int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.createGroup);
            dataOutputStream.writeUTF(groupName);
            dataOutputStream.write(user_id);
            return dataInputStream.readBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getGroupsCreated(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getGroupCreated);
            dataOutputStream.write(user_id);
            List<String> getGroupCreated = (List<String>) objectInputStream.readObject();
            return getGroupCreated;

        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getusers() {
        try {
            dataOutputStream.writeUTF(QueryType.getUsers);
            List<String> getUsers = (List<String>) objectInputStream.readObject();
            return getUsers;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean addUserToGroup(String groupName, String userName) {
        try {
            dataOutputStream.writeUTF(QueryType.addUserToGroup);
            dataOutputStream.writeUTF(groupName);
            dataOutputStream.writeUTF(userName);
            return dataInputStream.readBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAllGroups(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getAllGroups);
            dataOutputStream.write(user_id);
            List<String> getGroupCreated = (List<String>) objectInputStream.readObject();
            return getGroupCreated;

        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getGroupPlaylist(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getGroupPlaylist);
            dataOutputStream.write(user_id);
            List<String> getGroupPlaylist = (List<String>) objectInputStream.readObject();
            return getGroupPlaylist;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addPlaylistToGroup(String groupName, String addPlaylist) {
        try {

            dataOutputStream.writeUTF(QueryType.addPlaylistToGroup);
            dataOutputStream.writeUTF(groupName);
            dataOutputStream.writeUTF(addPlaylist);
            return dataInputStream.readBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public void settoDeafault(int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.settoDeafult);
            dataOutputStream.write(user_id);
        } catch (Exception e) {

        }

    }

    public void setAlbumtoqueue(String selectionModel, int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getAlbumSongs);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(selectionModel);

        } catch (IOException e) {
            System.out.println("Album in Client");

        }

    }

    public List<String> getAllSongs() {
        try {
            dataOutputStream.writeUTF(QueryType.getAllsong);
            List<String> list1 = (List<String>) objectInputStream.readObject();
            // System.out.println(list1);
            return list1;
        } catch (Exception e) {
            System.out.println("Error in frtching all songs");
            return null;

        }
    }

    public void setArtisttoqueue(String selectionModel, int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getArtistSongs);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(selectionModel);

        } catch (IOException e) {
            System.out.println("Artist in Client");


        }
    }

    public void setPlaylisttoqueue(String selectionModel, int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getPlaylistSongs);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(selectionModel);

        } catch (IOException e) {
            System.out.println("Playlist in Client");


        }
    }

    public void modifyQueue(List<String> newQueue, int user_id) {
        try{
            dataOutputStream.writeUTF(QueryType.modifyQueue);
            objectOutputStream.writeObject(newQueue);
            dataOutputStream.write(user_id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGroupPlaylisttoqueue(String groupPlaylistName, int user_id) {
        try {
            dataOutputStream.writeUTF(QueryType.getGroupPlaylistSongs);
            dataOutputStream.write(user_id);
            dataOutputStream.writeUTF(groupPlaylistName);

        } catch (IOException e) {
            System.out.println("Playlist in Client");


        }

    }

    public void setNewSongs(int user_id) {
        try{
            dataOutputStream.writeUTF(QueryType.setNewSongs);
            dataOutputStream.write(user_id);
        }
        catch (IOException e)
        {
            System.out.println("Error in sending new songs query");
        }
    }
    public void songlike(int songid, int user_id, int i) {
        try
        {
            dataOutputStream.writeUTF(QueryType.setlikes);
            dataOutputStream.write(songid);
            dataOutputStream.write(user_id);
            dataOutputStream.write(i);
        }
        catch(Exception e)
        {
            System.out.println("Song like dislikes in Client");
        }
    }

    public List<String> getTrending() {
        try
        {
            dataOutputStream.writeUTF(QueryType.getTrending);
            List<String> trending = (List<String>) objectInputStream.readObject();
            return trending;
        }
        catch (Exception e)
        {
            System.out.println("Get trending error");
        }
        return null;
    }

    /**
     * generate code for playlist
     * @param PlaylistName
     * @return
     */
    public String generateCode(String PlaylistName,int user_id) {
        try
        {
            dataOutputStream.writeUTF(QueryType.generatePlaylistCode);
            dataOutputStream.writeUTF(PlaylistName);
            dataOutputStream.write(user_id);
            String code = (String) objectInputStream.readObject();
            return code;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    public void playPlaylistFromCode(String text, int user_id) {
        try
        {
            dataOutputStream.writeUTF(QueryType.playPlaylistFromCode);
            objectOutputStream.writeObject(text);
            dataOutputStream.write(user_id);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
