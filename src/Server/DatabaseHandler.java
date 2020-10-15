package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import sample.*;
public class DatabaseHandler {
    private String USERNAME;
    private String PASSWORD;
    private String CONNECTIONURL = "jdbc:mysql://localhost:3306/sangeet";
    private Connection dbconnection;
    private PreparedStatement dbstatement;
    SongInfo songinfo;
    DatabaseHandler() {
        USERNAME = "root";
        PASSWORD = "";
    }

    /**
     *
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
                songinfo =new SongInfo(id,SongName,AlbumId,Genre,SongAddress,Date_Created,ImageId);
//                System.out.println(dbstatement);
//                System.out.println(SongAddress);
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
        System.out.println("Function fdvlfdv;df executed");
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            System.out.println(userid);
            String query = " select songName from queue where userid=" + userid + ";";
            System.out.println(query);
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println(dbstatement);
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
                    System.out.println(song);
                }
                return test;
            }


        } catch (Exception e) {
            System.out.println("Exception in fetching songs");
            e.printStackTrace();
            return null;
        }

    }



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
     * todo add group playlist
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
     *
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
           String query = "Insert into playlist Values(?,?,?);";
           dbstatement = dbconnection.prepareStatement(query);
           dbstatement.setString(1,null);
           dbstatement.setString(2,playlistName);
           dbstatement.setInt(3,userid);
           int rs = dbstatement.executeUpdate();
           return true;
       }
       catch (SQLException sqle)
       {
           System.out.println("Error in creation of playlist");
           return false;

       }


    }

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
     *
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
    public void getalbumSongs(String albumname, int userid) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select id from album where AlbumName = '" + albumname + "';";
            dbstatement = dbconnection.prepareStatement(query);
            System.out.println(query);
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

}
