package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.ClientMain;
import sample.SceneChanger;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
public class HistoryController implements Initializable {
    public Label nameLB;
    public sample.ClientInfo clientInfo;
    public TextArea historyTA;
    public Label mostPlayed;
    public ComboBox historyCB;
    public Button playHistory;
    public Button playMostPlayedBT;
    List<String> history;
    public void goToPlaylist(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",nameLB);
    }

    public void goToAlbum(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",nameLB);
    }

    public void goToSong(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientInfo = ClientMain.client.clientInfo;
        nameLB.setText(clientInfo.name);
        history = ClientMain.client.getHistory(clientInfo.user_id);
        for(int i=0;i<history.size();i++)
        {
            historyTA.appendText(history.get(i)+"\n");
        }
        historyCB.getItems().addAll(history);
        mostPlayed.setText(this.mostFrequent(history));
    }



    public void goToHistory(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\History.fxml","Song", nameLB);
    }
    public void goToGroup(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",nameLB);
    }
    public String mostFrequent(List<String> a)
    {

        // Sort the array
        Collections.sort(a);

        // find the max frequency using linear
        // traversal
        int max_count = 1;
        String res = a.get(0);
        int curr_count = 1;

        for (int i = 1; i < a.size(); i++)
        {
            if (a.get(i).equals(a.get(i-1)))
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = a.get(i-1);
                }
                curr_count = 1;
            }
        }

        // If last element is most frequent
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = a.get(a.size()-1);
        }

        return res;
    }

    public void playSong(ActionEvent actionEvent) {
        List<String > newQueue = new ArrayList<String>();
        if (history.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Select a song to be added");

        }
        else
        {

            String song = historyCB.getSelectionModel().getSelectedItem().toString();
            newQueue.add(song);
            ClientMain.client.modifyQueue(newQueue, ClientMain.client.clientInfo.user_id);

            new SceneChanger().changeScene2("FXML\\SongPlayer.fxml", "Song", nameLB);
        }
    }

    public void playMostPlayed(ActionEvent actionEvent) {
        List<String > newQueue = new ArrayList<String>();
        if (history.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Select a song to be added");

        }
        else
        {

            String song = mostPlayed.getText();
            newQueue.add(song);
            ClientMain.client.modifyQueue(newQueue, ClientMain.client.clientInfo.user_id);

            new SceneChanger().changeScene2("FXML\\SongPlayer.fxml", "Song", nameLB);
        }


    }
}
