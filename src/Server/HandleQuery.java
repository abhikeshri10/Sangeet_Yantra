/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.Socket;

/**
 *
 * @author Abhishek keshri
 */
public class HandleQuery implements Runnable{
    Socket socket ;
    
    HandleQuery(Socket socket)
    {
        this.socket = socket;
        
    }

    @Override
    public void run() {
       while (true)
       {
           
       }
       
    }
    
}
