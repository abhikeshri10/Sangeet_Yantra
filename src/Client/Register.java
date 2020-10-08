package Client;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import javax.swing.*;


public class Register {
    public TextField nameTF;
    public TextField emailTF;
    public TextField phoneTF;
    public TextField usernameTF;
    public TextField password1TF;
    public TextField password2TF;
    public void register(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();
        String username = usernameTF.getText();
        String password1 = password1TF.getText();
        String password2 = password2TF.getText();
        if(password1 == password2)
        {
            String s = "select * form user where Email = email or Username = username;";
           //send this query and wait for the response
            //response is stored as Resultset rs; if
            //while(rs.next)count++; if(count>0)
            //useralready exist try logging in or enter different username
            //if not
            String s2 = "insert into ";
        }
        else{
            //pop up message the password entered is not correct
            JOptionPane.showMessageDialog(null,"password entered is not correct","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void login(ActionEvent actionEvent) {


    }
}
