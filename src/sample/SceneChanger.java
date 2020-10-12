package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    public void changeScene(String sceneName, String title, ActionEvent event) throws IOException {

        Parent new_scene;
        try {
            new_scene = FXMLLoader.load(getClass().getResource(sceneName));
            Scene Home = new Scene(new_scene);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
