package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Client;
import sample.ClientInfo;
import sample.ClientMain;
import sample.SceneChanger;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Register  {
    public TextField nameTF;
    public TextField emailTF;
    public TextField phoneTF;
    public TextField usernameTF;
    public PasswordField password1TF;
    public PasswordField password2TF;


    public void register(ActionEvent actionEvent) throws IOException {
        String name = nameTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();
        String username = usernameTF.getText();
        String password1 = password1TF.getText();
        String password2 = password2TF.getText();

        if (password1.equals(password2)) {
            if (password1.length() < 8) {
                JOptionPane.showMessageDialog(null, "Too short Password");
            } else {
                ClientInfo new_Client = new ClientInfo(name, email, phone, username, password1);
                boolean check =ClientMain.client.createClient(new_Client);
                if (check) {
                    JOptionPane.showMessageDialog(null, "Sign up Successfull");
                   
                } else {
                    JOptionPane.showMessageDialog(null, "Sign up not Successfull");


                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "Password mismatches");
        }






}
    public void login(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\LoginPage.fxml","LoginPage",actionEvent);
    }





}
