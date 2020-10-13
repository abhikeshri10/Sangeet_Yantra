package sample.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import sample.ClientMain;
import sample.SceneChanger;
import sample.Song;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class SongPlayer implements Initializable {


    public ComboBox Queue;
    public Label songName;
    public Slider slider;
    public Button playbtn;
    public Button nextbtn;
    public Button prevbtn;
    public MediaView mediaview;
    public MediaPlayer player;
    public ComboBox Listi;
    public MenuItem playlistMenu;
    public MenuItem albumMenu;
    public MenuItem songMenu;
    Song song =new Song();

    public void PlaySong(ActionEvent actionEvent) {


       Boolean check= ClientMain.client.playSong(Listi.getSelectionModel().getSelectedItem().toString());
       //System.out.println(Listi.getSelectionModel().getSelectedItem().toString());
       if(check)
       {
           System.out.println("Executed");
           try {
               Media m = new Media(ClientMain.client.songInfo.file.toURI().toURL().toString());


               if (player != null) {
                   player.dispose();
               }


               player = new MediaPlayer(m);

               mediaview.setMediaPlayer(player);
               player.play();
               player.setOnReady(() -> {
                   //when player gets ready..
                   slider.setMin(0);
                   slider.setMax(player.getMedia().getDuration().toMinutes());

                   slider.setValue(0);

                       playbtn.setText("Play");



               });
               slider.valueProperty().addListener(new ChangeListener<Number>() {
                   @Override
                   public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                       if (slider.isPressed()) {
                           double val = slider.getValue();
                           player.seek(new Duration(val * 60 * 1000));
                       }
                   }
               });

               System.out.println("song started");



               //song.startSong();
           } catch (Exception e)
           {
               e.printStackTrace();
               System.out.println("Error in Song");
           }


       }
       else
       {
           System.out.println("UI failed");
       }
    }

    public void Openlist(ActionEvent actionEvent) {
        System.out.println("Clicked");



        //Listi.getChildren().addAll(list);
//        Listi.setMaxHeight(200);
//        Listi.getItems().addAll(data);
//        Listi.setVisible(true);
      //  list.setItems(data);




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView<String> list = new ListView<String>();
        List<String> list1= ClientMain.client.getSongs();
        System.out.println("INITIALISED");
        try
        {
           System.out.println(list1);
            Listi.getItems().addAll(list1);
        }
        catch(Exception e)
        {
            System.out.println("Error in getting song");
        }
    }

    public void PlayNext(ActionEvent actionEvent) {

        Listi.getSelectionModel().selectNext();
        PlaySong(actionEvent);
    }

    public void PlayPrev(ActionEvent actionEvent) {
        Listi.getSelectionModel().selectPrevious();
        PlaySong(actionEvent);
    }

    public void goToPlaylist(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\Playlist.fxml","Playlist",actionEvent);
    }

    public void goToAlbum(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\Album.fxml","Album",actionEvent);
    }

    public void goToSong(ActionEvent actionEvent) throws IOException {
       new SceneChanger().changeScene("FXML\\SongPlayer.fxml","Song",actionEvent);
    }

}
