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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.ClientMain;
import sample.SceneChanger;
import sample.Song;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
    public Button SpeedInc;
    public Button SpeedDec;
    public Button Repeat;
    public Slider volumeSlider;
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


                player.onEndOfMediaProperty().addListener(new ChangeListener<Runnable>() {
                    @Override
                    public void changed(ObservableValue<? extends Runnable> observable, Runnable oldValue, Runnable newValue) {
                        if(!test) {
                            Listi.getSelectionModel().selectNext();
                        }
                        {
                            Listi.getSelectionModel();
                        }
                    }


                });


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



                volumeSlider.setValue(player.getVolume()*100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(volumeSlider.getValue()/100);
                    }
                });
                player.play();
                playbtn.setText("Pause");




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
        List<String> list1= ClientMain.client.getSongs();
//        DoubleProperty widthProp = mediaview.fitWidthProperty();
//        DoubleProperty heightProp = mediaview.fitHeightProperty();
//
//        widthProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
//        heightProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));

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
        // PlaySong(actionEvent);
    }

    public void PlayPrev(ActionEvent actionEvent) {
        Listi.getSelectionModel().selectPrevious();
        // PlaySong(actionEvent);
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
    public void goToGroup(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",songName);
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
            test=true;
        }
        else
        {
            test=true;
        }
    }

    public void openLocal(ActionEvent actionEvent) {
        try {
            System.out.println("open song clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            Media m = new Media(file.toURI().toURL().toString());
            if (player != null) {
                player.dispose();
            }

            player = new MediaPlayer(m);

            mediaview.setMediaPlayer(player);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
