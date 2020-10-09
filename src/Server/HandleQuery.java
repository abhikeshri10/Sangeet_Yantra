/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/**
 *
 * @author Abhishek keshri
 */
public class HandleQuery implements Runnable{
    public SendResponse sendResponse;
    Socket socket;
    ObjectInputStream objectInputStream;
    Response response;
    //static Connection cnx;
    HandleQuery(Socket socket/* ,Connection cnx*/) {
        this.socket = socket;
        //this.cnx = cnx;
        response = new Response();
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
             try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(HandleQuery.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    public void setSendResponse(SendResponse sendResponse) {
        this.sendResponse = sendResponse;
    }

    public void Register(String s)//1st query type
    {
        response.setQuery_status(true);
        sendResponse.setResponse(response);//setting the response object in the send thread
    }
    public void Login(String s)//2nd query type
    {
        
    }
    public void Playlist(String s)//3rd query type
    {
        
    }
    public void Songs(String s)//4th query type
    {
        
    }

    @Override
    public void run(){
        while (true) {
            try {

                String r=objectInputStream.readObject().toString();
                PreparedStatement pr= Server.cnx.prepareStatement(r);
                ResultSet re= pr.executeQuery();
                while(re.next())
                {
                    System.out.println(re.getString("Name"));
                    System.out.println(re.getString("Email"));
                    System.out.println(re.getString("Phone"));
                }
                //here we will check the first letter of request
                //and then using if else or switch case we can then transfer the query to relevant position
            } catch (IOException | ClassNotFoundException|SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
