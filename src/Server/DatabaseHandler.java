package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import sample.*;

import javax.sound.midi.SysexMessage;

public class DatabaseHandler {
    private String USERNAME;
    private String PASSWORD;
    private String CONNECTIONURL = "jdbc:mysql://localhost:3306/sangeet";
    private Connection dbconnection;
    private PreparedStatement dbstatement;
    DatabaseHandler()  {
        USERNAME = "root";
        PASSWORD = "";

    }
<<<<<<< HEAD
    public Boolean createUser(ClientInfo clientInfo)  {
=======

    /**
     *
     * @param clientInfo
     */
    public void createUser(sample.ClientInfo clientInfo)  {
>>>>>>> 7efa4ce46b8c3d7719ce52011bfd6bf753f37b01
        try {


            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            System.out.println("Database connected");
            String query ="INSERT INTO user VALUES (?, ?, ?, ?, ?)";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1,clientInfo.name);
            dbstatement.setString(2,clientInfo.phone);
            dbstatement.setString(3,clientInfo.email);
            dbstatement.setString(4,clientInfo.username);
            dbstatement.setString(5,clientInfo.password);


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
    public boolean loginClient(String userName,String password)
    {   try {
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        String query = "SELECT * FROM user WHERE Username = '" + userName + "' AND Password = '" + password + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
<<<<<<< HEAD
            while(rs.next())
            {
                System.out.println(rs.getString("Name"));
                System.out.println(rs.getString("Phone"));
                System.out.println(rs.getString("Email"));
                System.out.println(rs.getString("Username"));
                System.out.println(rs.getString("Password"));
                return true;
=======
            if (rs.next())
            {  return true;
>>>>>>> 7efa4ce46b8c3d7719ce52011bfd6bf753f37b01
            }

        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            return false;

        }
        return false;
    }

    /**
     * Checking whether the logged user is a new user or not
     * this is for taking the entries of the features table
     * @param userName
     * @throws SQLException
     * @return
     */
    public boolean new_login(String userName) throws SQLException {
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        String query = "SELECT * FROM userdata WHERE UserName = '" + userName +  "';";
        dbstatement = dbconnection.prepareStatement(query);
        ResultSet rs = dbstatement.executeQuery();
        if(rs.getBoolean("new_login"))
        {
            return false;
        }
        else
        {

            String query2= "UPDATE userdata SET new_login='"+ "true" +"'WHERE UserName = '" + userName +"';";
            dbstatement = dbconnection.prepareStatement(query2);
            ResultSet rs2 = dbstatement.executeQuery();
            return true;
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
