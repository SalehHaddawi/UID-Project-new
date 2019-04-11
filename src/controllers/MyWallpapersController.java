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
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import utils.ImagesUtils;
import utils.Toast;

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
    @FXML
    private ImageView noWallpaperFoundIMageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        wallpapersTilePane.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
            if (c.next()) {
                if (c.wasRemoved()) {
                    if (wallpapersTilePane.getChildren().isEmpty()) {
                        noWallpaperFoundIMageView.setVisible(true);
                    }
                }
            }
        });

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

        if (wallpapersTilePane.getChildren().isEmpty()) {
            noWallpaperFoundIMageView.setVisible(true);
        }
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
    }

    @FXML
    private void removeImageFromMyWallappers(ActionEvent event) throws IOException {

        try {
            if (Toast.showConfirmation()) {

                Files.deleteIfExists(Paths.get(AppData.choosenWallpaper.getImageURL().substring(6)));

                choosenImageView.setImage(null);

                AppData.choosenWallpaper.removeWallpaper(wallpapersTilePane);

                choosenImageVBox.setVisible(false);

                Toast.makeText(lunch.Lunch.appStage, "Wallpaper Removed Succssfully", 1500);
            }
        } catch (IOException e) {
            Toast.makeText(lunch.Lunch.appStage, "Couldn't Remove Wallpaper, Try Agian Later", 1500);
        }

    }

    @FXML
    private void saveImageToHD(ActionEvent event) {
        ImagesUtils.saveImageAsToHD();
    }
}
