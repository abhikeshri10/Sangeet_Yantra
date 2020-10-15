package sample.Controller;

import com.sun.org.apache.xalan.internal.xsltc.runtime.ErrorMessages_it;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientMain;
import sample.SceneChanger;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GroupController implements Initializable {

    public Label nameLB;
    public sample.ClientInfo clientInfo;
    public TextField groupNameTF;
    public Button createGroupBT;
    public ComboBox selectGroup1CB;
    public ComboBox addUsersCB;
    public ComboBox selectGroup2CB;
    public Button addPlaylistBT;
    public ComboBox addPlaylistCB;

    public void goToPlaylist(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",nameLB);
    }

    public void goToAlbum(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",nameLB);
    }

    public void goToSong(ActionEvent actionEvent) throws IOException {

        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);
    }


    public void goToHistory(ActionEvent actionEvent) {

        new SceneChanger().changeScene2("FXML\\History.fxml","Song", nameLB);
    }

    public void goToGroup(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",nameLB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientInfo = ClientMain.client.clientInfo;
        nameLB.setText(clientInfo.name);
        List<String> groups = ClientMain.client.getGroupsCreated(clientInfo.user_id);
        if(groups==null)
        {
            System.out.println("No groups Present");
        }
        else
        {
            selectGroup1CB.getItems().addAll(groups);
        }

        List<String> users = ClientMain.client.getusers();
        if(users==null)
        {
            System.out.println("No users Present");
        }
        else
        {
            addUsersCB.getItems().addAll(users);        }

        List<String> allgroups = ClientMain.client.getAllGroups(clientInfo.user_id);
        if(allgroups==null)
        {

        }
        else
        {selectGroup2CB.getItems().addAll(allgroups);}

        addPlaylistCB.getItems().addAll(ClientMain.client.getPlaylist(clientInfo.user_id));

    }

    public void createGroup(ActionEvent actionEvent) {
        String groupName = groupNameTF.getText();
        boolean status = ClientMain.client.createGroup(groupName,clientInfo.user_id);
        if(status)
        {
            JOptionPane.showMessageDialog(null,"Successfully created group");
            selectGroup1CB.getItems().addAll(ClientMain.client.getGroupsCreated(clientInfo.user_id));
            selectGroup2CB.getItems().addAll(ClientMain.client.getAllGroups(clientInfo.user_id));
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Group Creation unsuccessful");

        }
    }

    public void addPlaylist(ActionEvent actionEvent) {
        String groupName = selectGroup2CB.getSelectionModel().getSelectedItem().toString();
        String addPlaylist = addPlaylistCB.getSelectionModel().getSelectedItem().toString();
        boolean status = ClientMain.client.addPlaylistToGroup(groupName,addPlaylist);
    }

    public void addUser(ActionEvent actionEvent) {
        String groupName = selectGroup1CB.getSelectionModel().getSelectedItem().toString();

        String userName = addUsersCB.getSelectionModel().getSelectedItem().toString();
        if(groupName==null||userName==null)
        {
            JOptionPane.showMessageDialog(null,"Please select the user and group properly");
        }
        else{
        boolean status = ClientMain.client.addUserToGroup(groupName,userName);
        }

    }
}
