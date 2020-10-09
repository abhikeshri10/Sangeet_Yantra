package Client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client  extends Application {
    public static Socket socket = null;
    public static ObjectOutputStream objectOutputStream =null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Sangeet Yantra");
        primaryStage.setScene(new Scene(root, 800, 625));
        primaryStage.show();
    }
    public static void main(String[] args) {
            //launch(args);
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

//
//                    String s = "select * from student;";
//                    Query query = new Query(s);
//
//                    objectOutputStream.writeObject(query);
//                    objectOutputStream.flush();
//                while(true)
//                {}

            }
            catch (IOException io)
            {
                io.printStackTrace();
            }

            // Thread t = new Thread(new HandleClient(socket));
            // t.start();
         launch(args);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}

