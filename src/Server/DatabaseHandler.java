package Server;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import sample.*;
public class DatabaseHandler {
    private String USERNAME;
    private String PASSWORD;
    private String CONNECTIONURL = "jdbc:mysql://localhost:3306/sangeet";
    private Connection dbconnection;
    private PreparedStatement dbstatement;
    SongInfo songinfo;
    Song song;
    DatabaseHandler() {
        USERNAME = "root";
        PASSWORD = "";
    }

    /**
     * Creating a new user //Register
     * @param clientInfo
     * @return
     */
    public boolean createUser(sample.ClientInfo clientInfo)  {
        try {

            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            System.out.println("Database connected");
            String query ="INSERT INTO user_details VALUES (?,?, ?, ?, ?, ?,?)";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1,null);
            dbstatement.setString(2,clientInfo.name);
            dbstatement.setString(3,clientInfo.email);
            dbstatement.setString(4,clientInfo.phone);
            dbstatement.setString(5,clientInfo.username);
            dbstatement.setString(6,clientInfo.password);
            dbstatement.setBoolean(7,true);
            dbstatement.executeUpdate();
            System.out.println("Register success");
            return true;
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.out.println("Database connection failed");
            return  false;
        }
    }
    /**
     * check whether the user exist or not
     * @param userName
     * @param password
     * @return
     */
    public sample.ClientInfo loginClient(String userName,String password)
    {   try {
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        String query = "SELECT * FROM user_details WHERE UserName = '" + userName + "' AND Password = '" + password + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            sample.ClientInfo clientInfo= null;
            while (rs.next())
            {   String name = rs.getString("Name");
                String email = rs.getString("Email");
                String username = rs.getString("UserName");
                String phone  = rs.getString("Phone");
                boolean new_login = rs.getBoolean("new_login");
                int user_id = rs.getInt("id");
                clientInfo = new sample.ClientInfo(name,email,phone,username,new_login,user_id);
                return clientInfo;
            }
            return null;
        }
        catch (SQLException sqle)
        {
                return null;
        }

    }

    /**
     * Checking whether the logged user is a new user or not
     * this is for taking the entries of the features table
     * @param userName
     * @throws SQLException
     * @return
     */
    public boolean new_login(String userName) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        }
        catch (SQLException sqle)
        {
            System.out.println("Connection error");
        }
        try {

            String query = "UPDATE user_details SET new_login=  false  WHERE UserName = '" + userName + "';";
            dbstatement = dbconnection.prepareStatement(query);
            System.out.println(dbstatement);
            int rs = dbstatement.executeUpdate();
            System.out.println(rs);
            return true;
        }
        catch (SQLException sqle)
        {
            System.out.println("Update error");
            return false;
        }


    }

    /**
     * Getting the list of artist present on the server
     * @return
     */
    public List<String> getArtist()
    {   try{
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        String query = "Select * from artist;";
        dbstatement = dbconnection.prepareStatement(query);
        ResultSet rs = dbstatement.executeQuery();
        List<String> ls = new ArrayList<String >();
        while(rs.next())
        {
            String artistName = rs.getString("ArtistName");
            ls.add(artistName);
        }
        return ls;

    }
        catch (SQLException sql)
        {
            System.out.println("Database fetch error");
        }
        return null;
    }

    /**
     * Getting the list of genre that we have
     * @return
     */
    public List<String> getGenre() {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from song;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            List<String> ls = new ArrayList<String >();
            while(rs.next())
            {
                String Genre = rs.getString("Genre");
                ls.add(Genre);
            }
            return ls;

        }
        catch (SQLException sql)
        {
            System.out.println("Database fetch error");
        }
        return null;
    }

    /**
     * Loading the song info like song Name subtitile file
     * @param text
     * @return
     */
    public SongInfo loadSong(String text) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query= "Select * from song WHERE SongName ='"+text+"';";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs= dbstatement.executeQuery();
            while(rs.next())
            {
                int id=rs.getInt("id");
                String SongName=rs.getString("SongName");
                int AlbumId=rs.getInt("AlbumId");
                String Genre=rs.getString("Genre");
                String SongAddress=rs.getString("SongAddress");
                String Date_Created=rs.getString("DateCreated");
                int ImageId=rs.getInt("ImageId");

                SongAddress.replace("mp3","srt");
                String srt = new String(".srt");
                String subtitileAddress = SongAddress.substring(0,SongAddress.length()-4)+srt;

                //System.out.println(subtitileAddress);
                File subtitlefile=new File(subtitileAddress);
                songinfo =new SongInfo(id,SongName,AlbumId,Genre,subtitlefile,Date_Created,ImageId);

                return songinfo;
            }
            return null;
        }
        catch(SQLException e)
        {
            System.out.println("Error in fetching Song");
            return null;
        }

    }

    /**
     * Get songs in the database
     * @return
     */
    /**
     * Get songs in the database
     *
     * @param userid
     * @return
     */
    public List<String> getList(int userid) {

        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
//            System.out.println(userid);
            String query = " select songName from queue where userid=" + userid + ";";
//            System.out.println(query);
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            System.out.println(dbstatement);
            ResultSet rs = dbstatement.executeQuery();

            List<String> test = new ArrayList<>();
            //rs.next();

            if (rs.next() == false) {
                query = "Select songName from song";
                dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet re = dbstatement.executeQuery();
                //System.out.println("false");

                while (re.next()) {
                    String song = re.getString("SongName");
                    test.add(song);
                    //System.out.println(song);
                }
                return test;
            } else {
//                System.out.println("Not empty set");
                String song = rs.getNString("SongName");
                test.add(song);
                while (rs.next()) {
                    song = rs.getNString("SongName");
                    test.add(song);
//                    System.out.println(song);
                }
                return test;
            }


        } catch (Exception e) {
            System.out.println("Exception in fetching songs");
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Setting user features of the user
     * @param user_id
     * @param artist
     * @param genre
     * @param language
     */
    public void setFeatures(int user_id, String artist, String genre, String language) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "INSERT INTO user_features VALUES(?,?,?,?,?)";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1,null);
            dbstatement.setInt(2,user_id);
            dbstatement.setString(3,artist);
            dbstatement.setString(4,genre);
            dbstatement.setString(5,language);
            dbstatement.executeUpdate();

        }
       catch (SQLException sqle)
       {
           System.out.println("Features insert error database");
       }
    }

    /**
     * Get playlist corresponding to the user
     *
     * @param userid
     * @return
     */
   public List<String> getPlaylist(int userid) {
       try{
           dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
           String query = "Select * from playlist WHERE UserId ='"+userid+"';";
           dbstatement = dbconnection.prepareStatement(query);
           ResultSet rs = dbstatement.executeQuery();
           List<String> ls = new ArrayList<String >();
           while(rs.next())
           {
               String Playlist = rs.getString("PlaylistName");
               ls.add(Playlist);
           }

           return ls;
       }
       catch (SQLException sql)
       {
           System.out.println("Database fetch error");
       }
       return null;
    }

    /**
     * Creating a playlist
     * @param userid
     * @param playlistName
     * @return
     */
    public boolean createPlaylist(int userid, String playlistName) {
       try {
           dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
           String query1 = "Select * from playlist Where PlaylistName='"+playlistName+"'And UserId='"+userid+"';";
           dbstatement = dbconnection.prepareStatement(query1);
           ResultSet rs2 = dbstatement.executeQuery();
           if(rs2.next())
           {    System.out.println("Playlist Already exists");
               return false;
           }
           String query = "Insert into playlist Values(?,?,?,?);";
           char[] ch = randomString();
           String str2 = String.valueOf(ch);
           System.out.println(str2);
           dbstatement = dbconnection.prepareStatement(query);
           dbstatement.setString(1,null);
           dbstatement.setString(2,playlistName);
           dbstatement.setInt(3,userid);
           dbstatement.setString(4,str2);
           int rs = dbstatement.executeUpdate();
           return true;
       }
       catch (SQLException sqle)
       {
           System.out.println("Error in creation of playlist");
           return false;
       }
    }

    /**
     * generating a random string for the shareable playlist
     * @return
     */
    public char[] randomString()
    {
        String charsCaps="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Chars="abcdefghijklmnopqrstuvwxyz";
        String nums="0123456789";
        String symbols="!@#$%^&*()_+-=.,/';:?><~*/-+";
        String passSymbols=charsCaps + Chars + nums +symbols;
        Random rnd=new Random();
        char[] password=new char[10];

        for(int i=0; i<10;i++){
            password[i]=passSymbols.charAt(rnd.nextInt(passSymbols.length()));
        }
        return password;

    }

    /**
     * Adding song to the pre-existing playlist
     * @param playlist
     * @param song
     * @return
     */
    public boolean addSongToPlaylist(String playlist, String song) {
       try{
           dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
           int id=0;
           int songid=0;
           try{
               String query = "Select * from playlist WHERE PlaylistName='"+playlist+"';";
               dbstatement = dbconnection.prepareStatement(query);
               ResultSet rs = dbstatement.executeQuery();
               while (rs.next())
               {
                   id=rs.getInt("id");
                   break;
               }
           }
           catch (SQLException sqle)
           {
               System.out.println("Query  error");
               return false;
           }
           try{
               String query = "Select * from song WHERE SongName='"+song+"';";
               dbstatement = dbconnection.prepareStatement(query);
               ResultSet rs = dbstatement.executeQuery();
               while (rs.next())
               {
                   songid = rs.getInt("id");
                   break;
               }
           }
           catch (SQLException sqle)
           {
               System.out.println("Song query error");
               return false;
           }
           try{
               String query = "Insert into playlistsong VALUES (?,?);";
               dbstatement = dbconnection.prepareStatement(query);
               dbstatement.setInt(1,id);
               dbstatement.setInt(2,songid);
               int rs = dbstatement.executeUpdate();
               if(rs==1)
               {
                   System.out.println("Song insertion succcess");
                   return  true;
               }
               else
               {
                   System.out.println("Song insertion error");
                   return false;
               }
           }
           catch (SQLException sqle)
           {
               System.out.println("Insert query error");
               return false;
           }
       }catch (SQLException sqle)
       {
           System.out.println("Database fetch error");
           return false;
       }
    }

    /**
     * getting the list of albums that we have
     * @return
     */
    public List<String> getAlbum() {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from album;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            List<String> ls = new ArrayList<String >();
            while(rs.next())
            {
                String Album = rs.getString("AlbumName");
                ls.add(Album);
            }
            return ls;
        }
        catch (SQLException sql)
        {
            System.out.println("Database fetch error");
        }
        return null;
    }

    /**
     * getting the history of song played by the users
     * @param userid
     * @return
     */
    public List<String> getHistory(int userid) {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from history Where UserId ='"+userid+"'Order by Time DESC ;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            List<Integer> ls = new ArrayList<Integer >();
            while(rs.next())
            {
                int songId = rs.getInt("SongId");
                ls.add(songId);
            }
            List<String> history = new ArrayList<String>();
            for(int i=0;i<ls.size();i++)
            {
                String query2 = "Select * from song Where id='"+ls.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query2);
                ResultSet rs2= dbstatement.executeQuery();
                while (rs2.next())
                {
                    history.add(rs2.getString("SongName"));
                }
            }
            return history;
        }
        catch (SQLException sql)
        {
            System.out.println("Database fetch error");
        }
        return null;
    }

    /**
     * Adding entry to history table
     * @param userid
     * @param songid
     */
    public void addHistory(int userid, int songid) {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Insert into history VALUES (?,?,?,CURRENT_TIMESTAMP )";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.setString(1,null);
            dbstatement.setInt(2,userid);
            dbstatement.setInt(3,songid);
            int rs = dbstatement.executeUpdate();
            if(rs == 1)
            {
                System.out.println("History insertion successfully");
            }
            else
            {
                System.out.println("History insertion failed");

            }
        }
        catch (SQLException sql)
        {
            System.out.println("Database fetch error");
        }
    }

    /**
     * Creating a group where users can add their songs and playlist
     * @param groupName
     * @param userid
     * @return
     */
    public boolean createGroup(String groupName, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query1 = "Select * from groups Where GroupName='"+groupName+"'And CreatorId='"+userid+"';";
            dbstatement = dbconnection.prepareStatement(query1);
            ResultSet rs2 = dbstatement.executeQuery();
            if(rs2.next())
            {   System.out.println("Playlist Already exists");
                return false;
            }
            String query = "Insert into groups Values(?,?,?);";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.setString(1,null);
            dbstatement.setString(2,groupName);
            dbstatement.setInt(3,userid);
            int rs = dbstatement.executeUpdate();

            //getting thr id of the inserted group
            String query3 = "Select * from groups Where GroupName='"+groupName+"'And CreatorId='"+userid+"';";
            dbstatement = dbconnection.prepareStatement(query3);
            ResultSet rs3 = dbstatement.executeQuery();
            int groupId=0;
            if(rs3.next())
            {
                groupId = rs3.getInt("id");
            }

            //inserting the creator to the members of the groups
            String query2 = "Insert into groupmembers Values(?,?);";
            dbstatement = dbconnection.prepareStatement(query2);
            dbstatement.setInt(1,groupId);
            dbstatement.setInt(2,userid);
            int rs4 = dbstatement.executeUpdate();
            return true;
        }
        catch (SQLException sqle)
        {
            System.out.println("Error in creation of group");
            return false;
        }
    }

    /**
     * Getting the groups of the user created by him
     *
     * @param userid
     * @return
     */
    public List<String> getGroupsCreated(int userid) {
        try {
            System.out.println("Group Request from "+userid);
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from groups Where CreatorId='" + userid + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs2 = dbstatement.executeQuery();
            List<String> getgroups = new ArrayList<String>();
           while (rs2.next())
           {
              getgroups.add(rs2.getString("GroupName"));

           }
           if (getgroups == null)
           {    System.out.println("No groups present");
               return null;
           }
           return getgroups;
        }
        catch ( SQLException e)
        {
            return null;
        }
    }

    /**
     * Get the list of users that we have
     * @return
     */
    public List<String> getUsers() {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from user_details;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs2 = dbstatement.executeQuery();
            List<String> getUsers = new ArrayList<String>();
            while (rs2.next())
            {
                getUsers.add(rs2.getString("Name"));
            }
            return getUsers;
        }
        catch ( SQLException e)
        {
            return null;
        }
    }

    /**
     * Admin can add users to the group
     * @param groupName
     * @param userName
     * @return
     */
    public boolean addUserToGroup(String groupName, String userName) {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query3 = "Select * from groups Where GroupName='"+groupName+"';";
            dbstatement = dbconnection.prepareStatement(query3);
            ResultSet rs3 = dbstatement.executeQuery();
            int groupId=0;
            if(rs3.next())
            {
                groupId = rs3.getInt("id");
            }
            //getting userId of the user
            String query4 = "Select * from user_details Where Name='"+userName+"';";
            dbstatement = dbconnection.prepareStatement(query4);
            ResultSet rs4 = dbstatement.executeQuery();
            int UserId=0;
            if(rs4.next())
            {
                UserId = rs4.getInt("id");
            }

            //inserting the creator to the members of the groups
            String query2 = "Insert into groupmembers Values(?,?);";
            dbstatement = dbconnection.prepareStatement(query2);
            dbstatement.setInt(1,groupId);
            dbstatement.setInt(2,UserId);
            int rs5 = dbstatement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    /**
     * Getting a list of allt he group that the user is currently a part of
     * @param userid
     * @return
     */
    public List<String> getAllGroups(int userid) {
        try {
            System.out.println("Group Request from "+userid);
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from groupmembers Where UserId='" + userid + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs2 = dbstatement.executeQuery();
            List<Integer> getgroupsId = new ArrayList<Integer>();
            while (rs2.next())
            {
                getgroupsId.add(rs2.getInt("GroupId"));

            }
            if (getgroupsId == null)
            {    System.out.println("No groups present");
                 return null;
            }
            List<String> groups = new ArrayList<String>();

            for(int i=0;i<getgroupsId.size();i++)
            {
                String query3 = "Select * from groups Where id='"+getgroupsId.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query3);
                ResultSet rs3 = dbstatement.executeQuery();
                if(rs3.next())
                {   System.out.println("There is group");
                   groups.add(rs3.getString("GroupName"));
                }
            }
            return groups;
        }
        catch ( SQLException e)
        {
            return null;
        }
    }

    /**
     * Getting a playlist correspomding to the group
     * @param userid
     * @return
     */
    public List<String> getGroupPlaylist(int userid) {
        try {
            System.out.println("Group playlist call");
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from groupmembers Where UserId='" + userid + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs2 = dbstatement.executeQuery();
            List<Integer> getgroupsId = new ArrayList<Integer>();
            while (rs2.next())
            {
                getgroupsId.add(rs2.getInt("GroupId"));

            }
            if (getgroupsId == null)
            {    System.out.println("No groups present");
                return null;
            }
            List<Integer> playlistId = new ArrayList<Integer>();

            for(int i=0;i<getgroupsId.size();i++)
            {
                String query3 = "Select * from groupplaylist Where GroupId='"+getgroupsId.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query3);
                ResultSet rs3 = dbstatement.executeQuery();
                while(rs3.next())
                {
                    playlistId.add(rs3.getInt("PlaylistId"));
                }
            }
            List<String> playlistName = new ArrayList<String>();

            for(int i=0;i<playlistId.size();i++)
            {
                String query3 = "Select * from playlist Where id='"+playlistId.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query3);
                ResultSet rs3 = dbstatement.executeQuery();
                if(rs3.next())
                {
                    playlistName.add(rs3.getString("PlaylistName"));
                }
            }
            return playlistName;
        }
        catch ( SQLException e)
        {   System.out.println("Group playlist Db error");
            return null;
        }
    }

    /**
     * Adding a playlist to a group of which the user is member
     * @param groupname
     * @param playlistName
     * @return
     */
    public boolean addPlaylisttoGroup(String groupname, String playlistName) {
        try {

            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select * from groups Where GroupName='" + groupname + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs2 = dbstatement.executeQuery();
            int groupId = 0;
            if(rs2.next())
            {
                groupId = rs2.getInt("id");
            }
         int playlistid = 0;
            String query3 = "Select * from playlist Where PlaylistName='"+playlistName+"';";
            dbstatement = dbconnection.prepareStatement(query3);
            ResultSet rs3 = dbstatement.executeQuery();
            if(rs3.next())
            {
                playlistid = rs3.getInt("id");
            }
            String query4 = "Insert into groupplaylist VALUES(?,?);";
            dbstatement = dbconnection.prepareStatement(query4);
            dbstatement.setInt(1,groupId);
            dbstatement.setInt(2,playlistid);
            int rs = dbstatement.executeUpdate();
            return true;
        }
        catch ( SQLException e)
        {
            return false;
        }
    }

    /**
     * Setting the queue to deafult like all songs
     * @param userid
     */
    public void settoDefault(int userid) {
        try {
            String query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Getting songs of a particular album
     * @param albumname
     * @param userid
     */
    public void getalbumSongs(String albumname, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select id from album where AlbumName = '" + albumname + "';";
            dbstatement = dbconnection.prepareStatement(query);
//            System.out.println(query);
            ResultSet rs = dbstatement.executeQuery();
            rs.next();
            int albumid = rs.getInt("id");
            query = "select songName from song where AlbumId = " + albumid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet re = dbstatement.executeQuery();
            List<String> test = null;
            query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            while (re.next()) {
                String song = re.getString("SongName");
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, userid);
                dbstatement.setString(2, song);
                dbstatement.executeUpdate();


                //System.out.println(song);
            }
            System.out.println("Database album executed");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Getting all the songs present on the server
     * @return
     */
    public List<String> getAllsongs() {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select songName from song";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet re = dbstatement.executeQuery();
            //System.out.println("false");
            List<String>  test = new ArrayList<>();

            while (re.next()) {
                String song = re.getString("SongName");
                test.add(song);
                //System.out.println(song);
            }
            return test;
        } catch (Exception e) {
            System.out.println("error in getAllsongs in database handler");
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Getting songs corresponding to a specified artist and updating the queue simultaneously
     * @param artistname
     * @param userid
     */
    public void getArtistsongs(String artistname, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select id from artist where ArtistName = '" + artistname + "';";
            dbstatement = dbconnection.prepareStatement(query);
//            System.out.println(query);
            ResultSet rs = dbstatement.executeQuery();
            rs.next();
            int artistid = rs.getInt("id");
            query = "select SongId from songartist where ArtistId = " + artistid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet re = dbstatement.executeQuery();
            query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            while(re.next())
            {
                int songid=re.getInt("SongId");
                String query1="select songName from song where id = " + songid + ";";
                dbstatement = dbconnection.prepareStatement(query1);
                ResultSet getsongname = dbstatement.executeQuery();
                getsongname.next();
                String song = getsongname.getString("SongName");
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, userid);
                dbstatement.setString(2, song);
                dbstatement.executeUpdate();
            }


            System.out.println("Database album executed");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Getting songs of a particular playlist
     * @param playlistname
     * @param userid
     */
    public void getPlaylistsongs(String playlistname, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select id from playlist where playlistName = '" + playlistname + "' AND UserId = '"+userid + "';";
            dbstatement=dbconnection.prepareStatement(query);
            ResultSet rs=dbstatement.executeQuery();
            rs.next();
            int playlistid=rs.getInt("id");
            query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            query="Select SongId from playlistsong where PlaylistId ='"+playlistid+"';";
            dbstatement = dbconnection.prepareStatement(query);
            rs=dbstatement.executeQuery();
            while(rs.next())
            {
                int songid=rs.getInt("SongId");
                String query1="select songName from song where id = " + songid + ";";
                dbstatement = dbconnection.prepareStatement(query1);
                ResultSet getsongname = dbstatement.executeQuery();
                getsongname.next();
                String song = getsongname.getString("SongName");
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, userid);
                dbstatement.setString(2, song);
                dbstatement.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * modifying the current queue
     * @param newQueue
     * @param user_id
     */
    public void modifyQueue(List<String> newQueue, int user_id) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
           String query = "delete from queue where UserId = " + user_id + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            for (int i=0;i<newQueue.size();i++)
            {
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, user_id);
                dbstatement.setString(2, newQueue.get(i));
                dbstatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Get groupplaylistsongs function
     * @param playlistname
     * @param userid
     */
    public void getGroupPlaylistsongs(String playlistname, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select id from playlist where playlistName = '" + playlistname + "';";
            dbstatement=dbconnection.prepareStatement(query);
            ResultSet rs=dbstatement.executeQuery();
            rs.next();
            int playlistid=rs.getInt("id");
            query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            query="Select SongId from playlistsong where PlaylistId ='"+playlistid+"';";
            dbstatement = dbconnection.prepareStatement(query);
            rs=dbstatement.executeQuery();
            while(rs.next())
            {
                int songid=rs.getInt("SongId");
                String query1="select songName from song where id = " + songid + ";";
                dbstatement = dbconnection.prepareStatement(query1);
                ResultSet getsongname = dbstatement.executeQuery();
                getsongname.next();
                String song = getsongname.getString("SongName");
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, userid);
                dbstatement.setString(2, song);
                dbstatement.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * set new songs to the server queue
     * @param userid
     */
    public void setNewSongs(int userid) {
        try
        {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "delete from queue where UserId = " + userid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            String query2 = "select * from song;";
            dbstatement = dbconnection.prepareStatement(query2);
            ResultSet rs  = dbstatement.executeQuery();
            List<String> s = new ArrayList<>();
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            System.out.println(ts);
            while (rs.next())
            {
                Timestamp ts2 = rs.getTimestamp("DateCreated");
                int TimeDifference = ts.getDate() - ts2.getDate();
                if(TimeDifference<= 20)
                {
                    s.add(rs.getString("SongName"));
                }
            }
            for(int i=0;i<s.size();i++) {
                String query3 ="Insert into queue values(?,?);";
                dbstatement = dbconnection.prepareStatement(query3);
                dbstatement.setInt(1,userid);
                dbstatement.setString(2,s.get(i));
                dbstatement.executeUpdate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * set likes and dislike table
     * @param songid
     * @param user_id
     * @param value
     */
    public void setlikes(int songid, int user_id, int value) {
        try
        {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query="delete from songlikes where userId = " + user_id + " and songId = "+songid + ";";
            dbstatement = dbconnection.prepareStatement(query);
            System.out.println(dbstatement);
            dbstatement.executeUpdate();
            query="INSERT INTO songlikes VALUES(?,?,?)";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.setInt(1,user_id);
            dbstatement.setInt(2,songid);
            dbstatement.setInt(3,value);
            dbstatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set play count for recommendation and trending page
     * @param songid
     */
    public void setPlayCount(int songid) {
        try
        {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query ="Select * from songplaycount where SongId ='"+songid+"';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            int PlayCount = 0;
            if(rs.next())
            {
                PlayCount = rs.getInt("count");

            }
            if(PlayCount==0)
            { query = "INSERT  into songplaycount values(?,?);";
                PlayCount+=1;
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1,songid);
                dbstatement.setInt(2,PlayCount);
                dbstatement.executeUpdate();
                System.out.println("Play count added successfully!!");
                return;
            }
            PlayCount+=1;
            query="UPDATE songplaycount set count = '"+PlayCount+ "'Where SongId = '"+songid+"';";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            System.out.println("Play count added successfully!!");
        }
        catch(Exception e)
        {   System.out.println("Play count addition failed");
            e.printStackTrace();
        }
    }

    /**
     * Gettting a list of trending songs on the server
     * @return
     */
    public List<String> getTrending() {
        try
        {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query ="Select * from songplaycount Order by count DESC ;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            List<Integer > trendingID = new ArrayList<>();

            while (rs.next())
            {
                int count = rs.getInt("count");
                if(count>=5)
                {
                    trendingID.add(rs.getInt("SongId"));
                }
            }
            int flag= 0;
            List<String> trending = new ArrayList<>();
            for(int i=0;i<trendingID.size()&&flag<10;i++)
            {
                query = "SELECT * from song where id = '"+trendingID.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query);
                ResultSet rs2 = dbstatement.executeQuery();
                while (rs2.next())
                {
                    trending.add(rs2.getString("SongName"));
                }
            }
            return trending;

        }
        catch(Exception e)
        {
            System.out.println("Trending failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * generate playlist code
     * @param playlistName
     * @param user_id
     * @return
     */
    public String generatePlaylistCode(String playlistName, int user_id) {
        try
        {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query ="Select * from playlist where PlaylistName = '"+playlistName+"'And UserId ='"+user_id+"';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            while (rs.next())
            {
                String code = rs.getString("shareCode");
                return code;
            }

        }
        catch(Exception e)
        {
            System.out.println("Trending failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * playPlaylistFromCode
     * @param code
     * @param user_id
     */
    public void playPlaylistFromCode(String code, int user_id) {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query ="Select * from playlist where shareCode = '"+code+"';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            int playlistId=0;
            if (rs.next())
            {
               playlistId = rs.getInt("id");
            }
            query = "delete from queue where UserId = " + user_id + ";";
            dbstatement = dbconnection.prepareStatement(query);
            dbstatement.executeUpdate();
            query="Select SongId from playlistsong where PlaylistId ='"+playlistId+"';";
            dbstatement = dbconnection.prepareStatement(query);
            rs=dbstatement.executeQuery();
            while(rs.next())
            {
                int songid=rs.getInt("SongId");
                String query1="select songName from song where id = " + songid + ";";
                dbstatement = dbconnection.prepareStatement(query1);
                ResultSet getsongname = dbstatement.executeQuery();
                getsongname.next();
                String song = getsongname.getString("SongName");
                query = "INSERT INTO queue VALUES(?,?)";
                dbstatement = dbconnection.prepareStatement(query);
                dbstatement.setInt(1, user_id);
                dbstatement.setString(2, song);
                dbstatement.executeUpdate();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Getting recommendation bbased on previous day
     * @param userid
     * @return
     */
    public List<String> getpastRecommendation(int userid) {
        try{
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "SELECT * from history where UserId = '"+userid+"';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            List<Integer> songId = new ArrayList<>();
            while (rs.next())
            {
                Timestamp time = rs.getTimestamp("Time");
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//                System.out.println(time.getHours());
//                System.out.println(currentTime.getHours());
                if(time.getHours()==currentTime.getHours()&&time.getDay()!=currentTime.getDay())
                {
                    songId.add(rs.getInt("SongId"));
                }
            }
            List<Integer> newSongId =songId.stream().distinct().collect(Collectors.toList());
            List<String> pastRecommends = new ArrayList<>();
            for(int i=0;i<newSongId.size();i++)
            {   //System.out.println(newSongId.get(i));
                query = "SELECT * from song where id = '"+newSongId.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query);
                ResultSet rs2 = dbstatement.executeQuery();
                while (rs2.next())
                {
                    pastRecommends.add(rs2.getString("SongName"));
                   // System.out.println(pastRecommends.get(i));
                }
            }
            return pastRecommends;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * setting a song file to be sent through  stream 2
     * @param text
     * @return
     */
    public Song setsongfile(String text) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query= "Select * from song WHERE SongName ='"+text+"';";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs= dbstatement.executeQuery();
            while(rs.next())
            {
                int id=rs.getInt("id");
                String SongName=rs.getString("SongName");
                int AlbumId=rs.getInt("AlbumId");
                String Genre=rs.getString("Genre");
                String SongAddress=rs.getString("SongAddress");
                String Date_Created=rs.getString("DateCreated");
                int ImageId=rs.getInt("ImageId");
                //songinfo =new SongInfo(id,SongName,AlbumId,Genre,SongAddress,Date_Created,ImageId);
                File file=new File(SongAddress);
                song=new Song(SongAddress);
                System.out.println(song.file);
//                System.out.println(dbstatement);
//                System.out.println(SongAddress);
                return song;
            }
            return null;
        }
        catch(SQLException e)
        {
            System.out.println("Error in fetching Song");
            return null;
        }

    }

    /**
     * get recommends from the user features his likes his group likes
     * @param user_id
     * @return
     */

    public List<String> getRecommends(int user_id) {

        try{
            List<String> recommends = new ArrayList<String>();
            List<Integer> temprecommends = new ArrayList<>();
            List<String> artist = new ArrayList<>();
            List<String> genre = new ArrayList<>();

            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            // based on user likes
            String query = "Select * from songlikes where userId = '"+user_id+"'And fav = 1;";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            while (rs.next())
            {
                temprecommends.add(rs.getInt("songId"));

            }
            //based on user features
            //artists genre
            query = "Select * from user_features where id = '"+user_id+"';";
            dbstatement = dbconnection.prepareStatement(query);
            rs = dbstatement.executeQuery();
            while (rs.next())
            {
                artist.add(rs.getString("ArtistName"));
                genre.add(rs.getString("Genre"));

            }
          //continue with artist and genre for artist search for artist id and for genre directly search from the
            //genre query
            for(int i=0;i<genre.size();i++) {
                query = "select  * from song where genre = '" + genre.get(i) + "';";
                dbstatement = dbconnection.prepareStatement(query);
                rs = dbstatement.executeQuery();
                while (rs.next())
                {
                    temprecommends.add(rs.getInt("id"));
                }
            }

            List<Integer> artistId = new ArrayList<>();
            for(int i=0;i<artist.size();i++)
            {
                query = "Select * from artist where ArtistName = '"+artist.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query);
                rs = dbstatement.executeQuery();
                while (rs.next())
                {
                    artistId.add(rs.getInt("id"));
                }

            }
            // songId from artist Id
            for(int i=0;i<artistId.size();i++)
            {
                query = "select * from songartist where ArtistId = '"+artistId.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query);
                rs = dbstatement.executeQuery();
                while (rs.next())
                {
                    temprecommends.add(rs.getInt("SongId"));
                }
            }
            List<Integer> temp = temprecommends.stream().collect(Collectors.toList());
            for(int i=0;i<temp.size();i++)
            {
                query = "select * from song where id = '"+temp.get(i)+"';";
                dbstatement = dbconnection.prepareStatement(query);
                rs = dbstatement.executeQuery();
                while (rs.next())
                {
                    recommends.add(rs.getString("SongName"));
                }
            }
            return recommends;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }
}
//happy ending :)