package sample.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.ClientMain;
import sample.SceneChanger;
import sample.Song;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OfflineController implements Initializable {

    public Label nameLb;
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
    public TextArea subtitleArea;
    public ComboBox currentQueueCB;
    public Button getCurrentPostitionBT;
    public TextField newPostion;
    public Button editPostionBT;
    public TextField currentPositionTF;
    public ComboBox selectSongs2CB;
    public Button addSongBT;
    public ComboBox deleteSongsCB;
    List<String> allsongs;
    boolean test=false;
    Song song =new Song();
    List<String> list1= new ArrayList<String>();
    File currentSong ;
    File[] offlinefiles;
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
//        System.out.println("Clicked");

        if (!Listi.getSelectionModel().isEmpty()) {
            slider.setValue(0);

            Boolean check = this.playsong(Listi.getSelectionModel().getSelectedItem().toString());
            //System.out.println(Listi.getSelectionModel().getSelectedItem().toString());
            if (check) {
//                System.out.println("Executed");
                try {
                    Media m = new Media(currentSong.toURI().toURL().toString());
                    songName.setText(Listi.getSelectionModel().getSelectedItem().toString());
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
                    player.setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            if (!test) {
                                Listi.getSelectionModel().selectNext();
                                PlayNext(actionEvent);
                            }
                            {
                                Openlist(actionEvent);
                                PlaySong(actionEvent);
                            }
                        }
                    });



                    volumeSlider.setValue(player.getVolume() * 100);
                    volumeSlider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            player.setVolume(volumeSlider.getValue() / 100);
                        }
                    });
                    player.pause();
                    playbtn.setText("Play");


                    System.out.println("song started");


                    //song.startSong();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in Song");
                }


            } else {
                System.out.println("UI failed");
            }


//        Listi.getChildren().addAll(list);
//        Listi.setMaxHeight(200);
//        Listi.getItems().addAll(data);
//        Listi.setVisible(true);
//        list.setItems(data);


        }
    }

    private Boolean playsong(String toString) {
        String s = Listi.getSelectionModel().getSelectedItem().toString();
        String newString  = s + ".mp3";
        int flag =0;
        for (int i=0;i<offlinefiles.length;i++)
        {
            if(offlinefiles[i].getName().equals(newString))
            {
                currentSong= offlinefiles[i];
                flag = 1;

            }
        }
        if(flag==1)
        return true;
        else
            return false;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    File f = new File(ClientMain.client.Downloadpath);
    offlinefiles = f.listFiles();

    for(int i=0;i<offlinefiles.length;i++)
    {
       String s= offlinefiles[i].getName();
       String newString = s.substring(0,s.length()-4);
       list1.add(newString);

    }
    if(list1!=null)
    Listi.getItems().addAll(list1);
    }

    public void PlayNext(ActionEvent actionEvent) {

        Listi.getSelectionModel().selectNext();
        PlaySong(actionEvent);
    }

    public void PlayPrev(ActionEvent actionEvent) {
        Listi.getSelectionModel().selectPrevious();
        PlaySong(actionEvent);
    }

    public void goToGroup(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\Group.fxml","Playlist",songName);

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


    public void fetchLocal(ActionEvent actionEvent) {
        try {
            System.out.println("open song clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            Media m = new Media(file.toURI().toURL().toString());
            if (player != null) {
                player.dispose();
                slider.setValue(0);

            }

            player = new MediaPlayer(m);

            mediaview.setMediaPlayer(player);
            this.PlaySong(actionEvent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void stop(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();

        new SceneChanger().changeScene2("FXML\\Offline.fxml","Offline",nameLb);
    }
}
