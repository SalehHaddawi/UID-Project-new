package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lunch.Lunch;

public class MainController implements Initializable {

    @FXML
    private VBox mainVBox;
    @FXML
    private HBox myWallpapersHBox;
    @FXML
    private HBox discoverHBox;
    @FXML
    private HBox searchHBox;

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

    @FXML
    private void goToMyWallpapers(MouseEvent event) throws IOException {
        if (currentPage == AppPages.MyWallpapers) {
            return;
        }

        currentPage = AppPages.MyWallpapers;

        makeActive(event != null ? event.getSource() : null);

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

        makeActive(event.getSource());

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

        makeActive(event.getSource());

        mainVBox.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Search.fxml"));
        mainVBox.getChildren().add(root);
    }

    private void makeActive(Object source) {
        if (source == null) {
            if (!myWallpapersHBox.getStyleClass().contains("active-navigation-button")) {
                myWallpapersHBox.getStyleClass().add("active-navigation-button");
            }
            return;
        } else {
            myWallpapersHBox.getStyleClass().remove("active-navigation-button");
        }

        if (source.equals(myWallpapersHBox)) {
            if (!myWallpapersHBox.getStyleClass().contains("active-navigation-button")) {
                myWallpapersHBox.getStyleClass().add("active-navigation-button");
            }
        } else {
            myWallpapersHBox.getStyleClass().remove("active-navigation-button");
        }

        if (source.equals(discoverHBox)) {
            if (!discoverHBox.getStyleClass().contains("active-navigation-button")) {
                discoverHBox.getStyleClass().add("active-navigation-button");
            }
        } else {
            discoverHBox.getStyleClass().remove("active-navigation-button");
        }

        if (source.equals(searchHBox)) {
            if (!searchHBox.getStyleClass().contains("active-navigation-button")) {
                searchHBox.getStyleClass().add("active-navigation-button");
            }
        } else {
            searchHBox.getStyleClass().remove("active-navigation-button");
        }
    }
}
