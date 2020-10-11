package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.*;
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
            System.out.println("Succesfully logged in");
        }
        else
        {
            System.out.println("Login Failed");
        }
    }

    public void goToRegister(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\Register.fxml","Register",actionEvent);

    }
}
