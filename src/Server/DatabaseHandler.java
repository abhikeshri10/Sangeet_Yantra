package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import sample.*;
public class DatabaseHandler {
    private String USERNAME;
    private String PASSWORD;
    private String CONNECTIONURL = "jdbc:mysql://localhost:3306/sangeet";
    private Connection dbconnection;
    private PreparedStatement dbstatement;
    DatabaseHandler() {
        USERNAME = "root";
        PASSWORD = "";
    }
    public void createUser(sample.ClientInfo clientInfo)  {
        try {

            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);

            System.out.println("Database connected");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.out.println("Database connection failed");
        }
    }
    public boolean loginClient(String userName,String password)
    {   try {
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        String query = "SELECT * FROM userdata WHERE UserName = '" + userName + "' AND Password = '" + password + "';";
            dbstatement = dbconnection.prepareStatement(query);
            ResultSet rs = dbstatement.executeQuery();
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException sqle)
        {

        }
        return false;
    }
    private ResultSet Query(String query) throws SQLException {
        ResultSet resultSet = null;
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = dbstatement.executeQuery();
        return resultSet;
    }

}
