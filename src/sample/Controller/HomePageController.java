package sample.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
        public Label nameLB;
        public Label phoneLB;
        public Label emailLB;
        public Label usernameLB;
        public MenuItem playlistMenu;
        public MenuItem albumMenu;
        public MenuItem songMenu;
        public MenuItem historyMenu;
    public MenuItem groupMenu;
    private ClientInfo clientInfo;


        @Override
        public void initialize(URL location, ResourceBundle resources) {
                clientInfo = ClientMain.client.clientInfo;
                nameLB.setText(clientInfo.name);
                emailLB.setText(clientInfo.email);
                phoneLB.setText(clientInfo.phone);
                usernameLB.setText(clientInfo.username);
        }

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
}
