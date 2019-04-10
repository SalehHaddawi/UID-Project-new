package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    @FXML
    private JFXButton saveImageToHDButton;
    @FXML
    private JFXButton removeImageFromHDButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try (Stream<Path> walk = Files.walk(Paths.get(new File("src/../wallpapers").toURI()))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toFile().toURI().toString()).collect(Collectors.toList());

            for (String imageLocation : result) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imageLocation);

                w.setImageURLThumbnail(imageLocation);
                
                w.setInMyWallpaper(true);
                
                w.setTilePaneParent(wallpapersTilePane);

                w.init(choosenImageVBox, spinner, choosenImageView, saveImageToHDButton);

                wallpapersTilePane.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
    }

    @FXML
    private void removeImageFromMyWallappers(ActionEvent event) throws IOException {
        choosenImageView.setImage(null);

        AppData.choosenWallpaper.removeWallpaper(wallpapersTilePane);

        Files.deleteIfExists(Paths.get(AppData.choosenWallpaper.getImageURL().substring(6)));

        choosenImageVBox.setVisible(false);
    }

    @FXML
    private void saveImageToHD(ActionEvent event) {
        
    }
}
