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
                new SceneChanger().changeScene("FXML\\Playlist.fxml","Playlist",actionEvent);
        }

        public void goToAlbum(ActionEvent actionEvent) throws IOException {
                new SceneChanger().changeScene("FXML\\Album.fxml","Album",actionEvent);
        }

        public void goToSong(ActionEvent actionEvent) throws IOException {
                new SceneChanger().changeScene("FXML\\SongPlayer.fxml","Sangeet Yantra",actionEvent);
        }

        public void goSong(ActionEvent actionEvent) throws IOException {
                new SceneChanger().changeScene("FXML\\SongPlayer.fxml","Sangeet Yantra",actionEvent);
        }
}
