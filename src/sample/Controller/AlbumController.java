package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import sample.ClientMain;
import sample.SceneChanger;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlbumController implements Initializable{
    public MenuItem playlistMenu;
    public MenuItem albumMenu;
    public MenuItem songMenu;
    public Label nameLB;
    public sample.ClientInfo clientInfo;
    public ComboBox artistCB;
    public ComboBox albumCB;
    public Button playAlbum;
    public Button playArtist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientInfo = ClientMain.client.clientInfo;
        nameLB.setText(clientInfo.name);
        List<String> album = ClientMain.client.getAlbum();
        List<String> artist = ClientMain.client.getArtist();
        albumCB.getItems().addAll(album);
        artistCB.getItems().addAll(artist);

    }
    public void goToPlaylist(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",nameLB);
    }

    public void goToAlbum(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",nameLB);
    }

    public void goToSong(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);
    }

    public void playAlbum(ActionEvent actionEvent) {
        ClientMain.client.setAlbumtoqueue(albumCB.getSelectionModel().getSelectedItem().toString(),clientInfo.user_id);
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);
    }

    public void playArtist(ActionEvent actionEvent) {

        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);

    }
    public void goToHistory(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\History.fxml","Song", nameLB);
    }
    public void goToGroup(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",nameLB);
    }


}
