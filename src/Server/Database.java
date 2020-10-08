/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Abhishek keshri
 */

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1BestCsharp
 */
public class Database {

    private static String servername = "localhost";
    private static String username = "root";
    private static String dbname  = "student";
    private static Integer portnumber  = 3306;
    private static String password = "";
    public static Connection cnx;
    public static Connection getConnection()
    {
       cnx = null;
        
        MysqlDataSource datasource = new MysqlDataSource();
        
        datasource.setServerName(servername);
        datasource.setUser(username);
        datasource.setPassword(password);
        datasource.setDatabaseName(dbname);
        datasource.setPortNumber(portnumber);
        
        try{
            cnx = datasource.getConnection();
            System.out.println("Database Connection Established");
        } catch (SQLException ex) {
            Logger.getLogger(" Get Connection -> " + Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cnx;
        
    }
    
    
}