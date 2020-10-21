package sample.Controller;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.ClientMain;
import sample.SceneChanger;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlaylistController implements Initializable {
    public Label nameLB;
    public sample.ClientInfo clientInfo;
    public ComboBox selectSongCB;
    public Button addSongBT;
    public Button createPlaylistBT;
    public List<String> songList = null;
    public ComboBox selectPlaylistCB;
    public ComboBox selectPlaylist2CB;
    public ComboBox addSong2CB;
    public Button addSong2BT;
    public Button modfiyPlaylist;
    public TextField playlistNameTF;
    public AnchorPane scrollPane;
    public ComboBox groupPlaylistCB;
    public Button playGroupPlaylistBT;
    public Button playlist;

    //    public void setScrollPane(List<String > ls)
//    {
//        for(int i=0;i<ls.size();i++)
//        {
//            CheckBox c = new CheckBox(ls.get(i));
//            scrollPane.getChildren().add(c);
//            String s=ls.get(i);
//            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//
//                public void handle(ActionEvent e)
//                {
//                    if (c.isSelected())
//                        songList.add(s);
//
//                }
//
//            };
//            c.setOnAction(event);
//        }
//
//        // set event to checkbox
//
//    }
    public void goToPlaylist(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Playlist.fxml", "Playlist", nameLB);
    }

    public void goToAlbum(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Album.fxml", "Album", nameLB);
    }

    public void goToSong(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml", "Song", nameLB);
    }
    public void goToHistory(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\History.fxml","Song", nameLB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientInfo = ClientMain.client.clientInfo;
        nameLB.setText(clientInfo.name);

        List<String> songs = ClientMain.client.getAllSongs();
        System.out.println(songs);


        addSong2CB.getItems().addAll(songs);
        selectPlaylistCB.getItems().addAll(ClientMain.client.getPlaylist(clientInfo.user_id));
        selectPlaylist2CB.getItems().addAll(ClientMain.client.getPlaylist(clientInfo.user_id));
        groupPlaylistCB.getItems().addAll(ClientMain.client.getGroupPlaylist(clientInfo.user_id));

    }

    /**
     * @param actionEvent
     */
    public void addSong(ActionEvent actionEvent) {
        String song;
        song = selectSongCB.getSelectionModel().getSelectedItem().toString();
        if(song==null)
        {
            JOptionPane.showMessageDialog(null, "Please select song");
        }
        else
        {   System.out.println(song);
            songList.add(song);
        }

    }

    public void createPlaylist(ActionEvent actionEvent) {
        String playlistName = playlistNameTF.getText();
        //List<String> newList = songList.stream().distinct().collect(Collectors.toList());
        boolean status = ClientMain.client.createPlaylist(clientInfo.user_id,playlistName);
        if(status)
        {
            JOptionPane.showMessageDialog(null, "Playlist created Successfully");
            new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",nameLB);

        }
        else
        {
            JOptionPane.showMessageDialog(null, "Playlist Could not be created");
        }

    }

    /**
     *
     * @param actionEvent
     */
    public void addPlaylistSong(ActionEvent actionEvent) {

        String song = addSong2CB.getSelectionModel().getSelectedItem().toString();
        String playlist = selectPlaylist2CB.getSelectionModel().getSelectedItem().toString();
        boolean status = ClientMain.client.addSongToPlaylist(playlist,song);
        if(status)
        {
            JOptionPane.showMessageDialog(null, "Song inserted Successfully");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error in song insertion");
        }
    }


    public void goToGroup(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",nameLB);
    }
    public void playGroupPlaylist(ActionEvent actionEvent) {
     
    }

    public void playPlaylist(ActionEvent actionEvent) {
        ClientMain.client.setPlaylisttoqueue(selectPlaylistCB.getSelectionModel().getSelectedItem().toString(),clientInfo.user_id);
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song", nameLB);
    }
}



