package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import sample.Client;
import sample.ClientInfo;
import sample.ClientMain;
import sample.SceneChanger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecommendationController implements Initializable{
    public Label nameLB;
    public ClientInfo clientInfo;
    public TextArea pastDayTA;
    public TextArea recommendationTA;
    List<String> pastDay = new ArrayList<>();
    List<String> recommends = new ArrayList<>();

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
        clientInfo = ClientMain.client.clientInfo;
        nameLB.setText(clientInfo.name);

        pastDay = ClientMain.client.getpastRecommendation(clientInfo.user_id);
        for (int i=0;i<pastDay.size();i++)
        {
            pastDayTA.appendText(pastDay.get(i)+"\n");
        }
        recommends = ClientMain.client.getRecommends(clientInfo.user_id);
        if(recommends!=null)
        {for (int i=0;i<recommends.size();i++)
        {
            recommendationTA.appendText(recommends.get(i)+"\n");
        }}

    }

    public void playPast(ActionEvent actionEvent) {
        List<String > newQueue = pastDay;
        ClientMain.client.modifyQueue(newQueue,clientInfo.user_id);
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Songs",nameLB);
    }

    public void playRecommendation(ActionEvent actionEvent) {
    List<String> newQueue = recommends;
    ClientMain.client.modifyQueue(newQueue,clientInfo.user_id);
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Songs",nameLB);

    }
}
