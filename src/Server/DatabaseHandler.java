package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getList() {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "Select Songname from song ;";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = dbstatement.executeQuery();
            //System.out.println(dbstatement);
            List<String> test=new ArrayList<>();
            while(rs.next())
            {
                String song=rs.getNString("SongName");
                test.add(song);
                //System.out.println(song);
            }
            return test;
        }
        catch(Exception e)
        {
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
           System.out.println("Featurs insert error database");
       }
    }
    /**
     *
     * @param query
     * @return
     * @throws SQLException
     */
    private ResultSet Query(String query) throws SQLException {
        ResultSet resultSet = null;
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = dbstatement.executeQuery();
        return resultSet;
    }

}
