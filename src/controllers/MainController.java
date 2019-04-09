package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import lunch.Lunch;

public class MainController implements Initializable {

    @FXML
    private VBox mainVBox;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onMinimize(ActionEvent event) {
        Lunch.appStage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToMyWallpapers(ActionEvent event) throws IOException {
        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/MyWallpapers.fxml"));
        mainVBox.getChildren().add(root);
    }

    @FXML
    private void goToDiscover(ActionEvent event) throws IOException {
        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Discover.fxml"));
        mainVBox.getChildren().add(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Search.fxml"));
        mainVBox.getChildren().add(root);
    }

    @FXML
    private void goToOptions(ActionEvent event) {
    }
}
