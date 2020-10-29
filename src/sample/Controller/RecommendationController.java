package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import sample.ClientMain;
import sample.SceneChanger;

import java.net.URL;
import java.util.ResourceBundle;

public class RecommendationController implements Initializable{
    public Label nameLB;

    public void goToPlaylist(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",nameLB);
    }

    public void goToAlbum(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",nameLB);
    }

    public void goToSong(ActionEvent actionEvent) {
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
        
    }

    public void playPast(ActionEvent actionEvent) {
    }

    public void playRecommendation(ActionEvent actionEvent) {
    }
}
