package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Client;
import sample.ClientInfo;
import sample.ClientMain;
import sample.SceneChanger;

import javax.swing.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        String password = password1TF.getText();
        String password2 = password2TF.getText();
        if (password.equals(password2)) {

            String pattern = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(password);
            int ind1 = email.indexOf('@'), ind2 = email.indexOf('.');

            if(name.length() < 1 || password.length() < 1 || email.length() < 1 || phone.length()<1) {
                JOptionPane.showMessageDialog(null, "Fields Should Not Be Kept Empty!!");
                return ;
            }
            else if(!phone.matches("\\d{10}"))
            {
                JOptionPane.showMessageDialog(null, "Phone number invalid");
                return ;
            }
            else if(!((ind1 > 1 && ind1 < email.length() - 3) || (ind2 > 0 && ind2 < email.length() - 2))) {
                this.emailTF.setText("");
                JOptionPane.showMessageDialog(null,"Invalid Email");
                return ;
            }

            else if(password.length() < 8 || !m.matches()) {
                this.password1TF.setText("");
                JOptionPane.showMessageDialog(null, "Sign up Successfully");

                return ;
            }
            else
            {
                ClientInfo new_Client = new ClientInfo(name, email, phone, username, password);
                boolean check = ClientMain.client.createClient(new_Client);
                if (check) {
                    JOptionPane.showMessageDialog(null, "Sign up Successfully");

                } else {
                    JOptionPane.showMessageDialog(null, "User name already exists");


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
