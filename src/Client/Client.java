package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;


        try {
             socket = new Socket("localhost", 5436);

           
            System.out.println("Client created.");
//            System.out.println("Enter name of client.");
//            String name = bufferedReader.readLine();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                while(true)
                {

                    String s = bufferedReader.readLine();
                    Query query = new Query(s);

                    objectOutputStream.writeObject(query);
                    objectOutputStream.flush();
                }

            }
            catch (IOException io)
            {
                io.printStackTrace();
            }

            // Thread t = new Thread(new HandleClient(socket));
            // t.start();
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}

