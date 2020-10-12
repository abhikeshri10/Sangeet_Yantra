package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    public PasswordField passwordPF;
    @FXML
    public TextField usernameTF;
    @FXML
    public Button loginBT;
    @FXML
    public Button registerBT;
    @FXML
    public Button homeBT;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String ip = "localhost";
        int port = 6005;
        ClientMain.client = new Client();
        ClientMain.client.connectToServer(ip,port);
    }

    public void login(ActionEvent actionEvent) throws IOException {

        boolean success = ClientMain.client.loginClient(usernameTF.getText(),passwordPF.getText());
        if(success)
        {
<<<<<<< HEAD
            JOptionPane.showMessageDialog(null, "Login Successfull");
=======
            System.out.println("Successfully logged in");
            boolean isNewUser = ClientMain.client.isNewUser(usernameTF.getText());
            if(isNewUser)
            {
                System.out.println("Yes the user is new user");
            }
            else
            {
                System.out.println("No the user is old user");
            }
>>>>>>> 7efa4ce46b8c3d7719ce52011bfd6bf753f37b01
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Login failed");
        }
    }

    public void goToRegister(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\Register.fxml","Register",actionEvent);
    }

    public void openHome(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\ClientFeatures.fxml","Sangeet Yantra",actionEvent);
    }
}
