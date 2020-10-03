/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Abhishek keshri
 */
public class HandleQuery implements Runnable {

    Socket socket;
    ObjectInputStream objectInputStream;

    HandleQuery(Socket socket) {
        this.socket = socket;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Register(String s)//1st query type
    {
        
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
                Query query = (Query) objectInputStream.readObject();
                String request = query.getQuery();
                //here we will check the first letter of request 
                //and then using if else or switch case we can then trasnsfer the query to relevant position
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

}
