package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.ClientInfo;
import sample.ClientMain;
import sample.SceneChanger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientFeaturesController implements Initializable {
    public Label name;
    public ClientInfo clientInfo;
    public ComboBox artistCB;
    public Button submitBT;
    public ComboBox genreCB;
    public ComboBox languageCB;
    public Button homeBT;

    public void submitFeatures(ActionEvent actionEvent) {
        String artist = artistCB.getSelectionModel().getSelectedItem().toString();
        String genre = genreCB.getSelectionModel().getSelectedItem().toString();
        String language = languageCB.getSelectionModel().getSelectedItem().toString();
        ClientMain.client.setFeatures(clientInfo.user_id,artist,genre,language);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientInfo = ClientMain.client.clientInfo;
        name.setText(clientInfo.name);
        List<String> artist;
        artist= ClientMain.client.getArtist();
        List<String> genre;
        genre = ClientMain.client.getGenre();
        List<String> language = new ArrayList<String>();
        language.add("English");
        language.add("Hindi");

        try{
        artistCB.getItems().addAll(artist);
        genreCB.getItems().addAll(genre);
        languageCB.getItems().addAll(language);
        }
        catch (Exception e)
        {
            System.out.println("Error in setting combo box");
        }
  }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        new SceneChanger().changeScene("FXML\\HomePage.fxml","Sangeet Yantra (Home)",actionEvent);
    }
}
