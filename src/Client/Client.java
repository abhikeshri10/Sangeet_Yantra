package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static Socket socket = null;
    public static ObjectOutputStream objectOutputStream =null;
    public static void main(String[] args) {

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
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


                    String s = "select * from student;";
                    Query query = new Query(s);

                    objectOutputStream.writeObject(query);
                    objectOutputStream.flush();
                while(true)
                {}

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

