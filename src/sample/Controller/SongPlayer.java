package sample.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
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
    public Button SpeedInc;
    public Button SpeedDec;
    public Button Repeat;
    public Slider volumeSlider;
    public Button Shuffle;
    public Button allSongbtn;
    boolean test=false;
    Song song =new Song();

    public void PlaySong(ActionEvent actionEvent) {

        MediaPlayer.Status status=player.getStatus();
        if(status== MediaPlayer.Status.PLAYING)
        {
            player.pause();
            playbtn.setText("Play");
        }
        else
        {
            player.play();
            playbtn.setText("Pause");
        }




    }

    public void Openlist(ActionEvent actionEvent) {
        System.out.println("Clicked");
        Boolean check= ClientMain.client.playSong(Listi.getSelectionModel().getSelectedItem().toString());
        //System.out.println(Listi.getSelectionModel().getSelectedItem().toString());
        if(check)
        {
            System.out.println("Executed");
            try {
                Media m = new Media(ClientMain.client.songInfo.file.toURI().toURL().toString());

//                List<Track>subtitle =m.getTracks();
//                System.out.println(subtitle.add());


                // subtitle.getTrackID();
                if (player != null) {
                    player.dispose();
                }



                player = new MediaPlayer(m);

                mediaview.setMediaPlayer(player);
                player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        slider.setValue(newValue.toSeconds());
                    } });





                slider.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        player.seek(Duration.seconds(slider.getValue()));
                    }
                });

                slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        player.seek(Duration.seconds(slider.getValue()));
                    }
                });

                player.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        Duration total = m.getDuration();
                        slider.setMax(total.toSeconds());
                    }
                });
                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        if(!test) {
                            Listi.getSelectionModel().selectNext();
                            PlayNext(actionEvent);
                        }
                        {
                            Openlist(actionEvent);
                            PlaySong(actionEvent);
                        }
                    }
                });



                volumeSlider.setValue(player.getVolume()*100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(volumeSlider.getValue()/100);
                    }
                });
                player.pause();
                playbtn.setText("Play");




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



        //Listi.getChildren().addAll(list);
//        Listi.setMaxHeight(200);
//        Listi.getItems().addAll(data);
//        Listi.setVisible(true);
        //  list.setItems(data);




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView<String> list = new ListView<String>();


        List<String> list1=ClientMain.client.getSongs(ClientMain.client.clientInfo.user_id);

//        DoubleProperty widthProp = mediaview.fitWidthProperty();
//        DoubleProperty heightProp = mediaview.fitHeightProperty();
//
//        widthProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
//        heightProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));

//        System.out.println("INITIALISED");
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

    public void goToPlaylist(ActionEvent actionEvent)  {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",songName);
    }

    public void goToAlbum(ActionEvent actionEvent){
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",songName);
    }

    public void goToSong(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song",songName);
    }
    public void goToHistory(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\History.fxml","Song", songName);
    }

    public void Fast(ActionEvent actionEvent) {
        player.setRate(player.getRate()+0.5);
    }

    public void Slow(ActionEvent actionEvent) {
        player.setRate(player.getRate()-0.5);
    }

    public void setRepeat(ActionEvent actionEvent) {
        if(test==false)
        {
            Repeat.setText("Circular");
            test=true;
        }
        else
        {
            Repeat.setText("Repeat");
            test=false;
        }
    }

    public void Qshuffle(ActionEvent actionEvent) {

    }

    public void opendefaultqueue(ActionEvent actionEvent) {
        ClientMain.client.settoDeafault(ClientMain.client.clientInfo.user_id);
        List<String> list1=ClientMain.client.getSongs(ClientMain.client.clientInfo.user_id);
        Listi.getItems().addAll(list1);

    }
    public void goToGroup(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",songName);
    }
}
