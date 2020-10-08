package Server;

import java.io.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendResponse implements Runnable{
    Socket socket ;
    ObjectOutputStream objectOutputStream;
    private Response response;
    SendResponse(Socket socket) {
        this.socket = socket;

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(HandleQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setResponse(Response response) {
        this.response = response;

    }

    @Override
    public void run() {
        while (true)
        {
            try {
                objectOutputStream.writeObject(response);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
