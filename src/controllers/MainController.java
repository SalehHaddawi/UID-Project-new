package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lunch.Lunch;

public class MainController implements Initializable {

    @FXML
    private VBox mainVBox;

    enum AppPages {
        MyWallpapers, Discover, Search
    };

    AppPages currentPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            goToMyWallpapers(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onMinimize(ActionEvent event) {
        Lunch.appStage.setIconified(true);
    }

    private void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToMyWallpapers(MouseEvent event) throws IOException {
        if (currentPage == AppPages.MyWallpapers) {
            return;
        }

        currentPage = AppPages.MyWallpapers;

        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/MyWallpapers.fxml"));
        mainVBox.getChildren().add(root);
    }

    @FXML
    private void goToDiscover(MouseEvent event) throws IOException {
        if (currentPage == AppPages.Discover) {
            return;
        }

        currentPage = AppPages.Discover;

        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Discover.fxml"));
        mainVBox.getChildren().add(root);
    }

    @FXML
    private void goToSearch(MouseEvent event) throws IOException {
        if (currentPage == AppPages.Search) {
            return;
        }

        currentPage = AppPages.Search;

        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Search.fxml"));
        mainVBox.getChildren().add(root);
    }
}
