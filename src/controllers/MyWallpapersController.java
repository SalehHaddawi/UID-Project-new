package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MyWallpapersController implements Initializable {

    @FXML
    private TilePane wallpapersTilePane;
    @FXML
    private VBox choosenImageVBox;
    @FXML
    private ImageView choosenImageView;
    @FXML
    private JFXSpinner spinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (String imageUrl : AppData.userURLs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imageUrl);

                w.setImageURLThumbnail(imageUrl);

                w.init(choosenImageVBox, spinner, choosenImageView);

                wallpapersTilePane.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
    }

    @FXML
    private void addImageToMyWallappers(ActionEvent event) {
    }
}
