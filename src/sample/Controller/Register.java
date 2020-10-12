package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Client;
import sample.ClientInfo;
import sample.ClientMain;
import sample.SceneChanger;

import javax.swing.*;
import java.io.IOException;


public class Register {
    public TextField nameTF;
    public TextField emailTF;
    public TextField phoneTF;
    public TextField usernameTF;
    public PasswordField password1TF;
    public PasswordField password2TF;
    public void register(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();
        String username = usernameTF.getText();
        String password1 = password1TF.getText();
        String password2 = password2TF.getText();
        if(password1 == password2)
        {
            ClientInfo new_Client = new ClientInfo(name,email,phone,username,password1);

        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\LoginPage.fxml","LoginPage",actionEvent);
    }
}
