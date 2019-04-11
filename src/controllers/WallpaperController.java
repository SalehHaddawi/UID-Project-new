package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.ImagesUtils;
import utils.Toast;

public class WallpaperController implements Initializable {

    @FXML
    public ImageView imageView;
    @FXML
    private JFXSpinner spinner;

    String imageURL;
    String imageURLThumbnail;

    VBox choosenImageVBox;
    JFXSpinner choosenImageSpinner;
    ImageView choosenImageView;
    JFXButton choosenImageButton;
    TilePane tilePaneParent;

    boolean inMyWallpapers = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void showChoosenImage(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }

        choosenImageVBox.setVisible(true);
        choosenImageSpinner.setVisible(true);

        AppData.choosenWallpaper = this;

        choosenImageButton.setDisable(true);

        Image img = new Image(imageURL, true);

        img.progressProperty().addListener((obs, oldV, newV) -> {
            if ((Double) newV == 1) {
                choosenImageSpinner.setVisible(false);
                choosenImageButton.setDisable(false);

                Timeline timeline = new Timeline();

                choosenImageView.setScaleX(0);
                choosenImageView.setScaleY(0);

                KeyFrame key1 = new KeyFrame(Duration.seconds(0.3), new KeyValue(choosenImageView.scaleXProperty(), 1));
                KeyFrame key2 = new KeyFrame(Duration.seconds(0.3), new KeyValue(choosenImageView.scaleYProperty(), 1));

                timeline.getKeyFrames().addAll(key1, key2);
                timeline.play();
            }
        });

        img.errorProperty().addListener((obs, oldV, newV) -> {
            if (newV) { // something wrong happened
                choosenImageSpinner.setVisible(false);
                choosenImageView.setImage(imageView.getImage());

                Toast.makeText(lunch.Lunch.appStage, "Couldn't load Image With Full Resolution!", 500);
                System.out.println("something wrong happened");
            }
        });

        choosenImageView.setImage(img);
    }

    public void init(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView, JFXButton choosenImageButton) {
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item2 = new MenuItem("Save Image As");
        item2.setOnAction(value -> {
            ImagesUtils.saveImageAsToHD();
        });

        if (!inMyWallpapers) {
            MenuItem item1 = new MenuItem("Add To My Wallpapers");
            item1.setOnAction(value -> {
                ImagesUtils.saveImageToHD(null);
            });

            // Add MenuItem to ContextMenu
            contextMenu.getItems().addAll(item1, item2);

        } else {
            MenuItem item1 = new MenuItem("Remove From My Wallpapers");
            item1.setOnAction(value -> {
                try {
                    if (Toast.showConfirmation()) {

                        removeWallpaper(tilePaneParent);

                        Files.deleteIfExists(Paths.get(getImageURL().substring(6)));

                        Toast.makeText(lunch.Lunch.appStage, "Wallpaper Removed Succssfully", 1500);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            // Add MenuItem to ContextMenu
            contextMenu.getItems().addAll(item1, item2);
        }

        imageView.setOnContextMenuRequested(value -> {
            AppData.choosenWallpaper = this;
            contextMenu.show(imageView, value.getScreenX(), value.getScreenY());
        });

        this.choosenImageVBox = choosenImageVBox;
        this.choosenImageSpinner = choosenImageSpinner;
        this.choosenImageView = choosenImageView;
        this.choosenImageButton = choosenImageButton;

        Image image = new Image(imageURLThumbnail, 200, 200, false, true, true);

        imageView.setImage(image);

        image.progressProperty().addListener((obs, oldV, newV) -> {
            if ((Double) newV == 1) {
                spinner.setVisible(false);
            }
        });
    }

    public void setImageURL(String url) {
        this.imageURL = url;
    }

    public void setImageURLThumbnail(String imageURLThumbnail) {
        this.imageURLThumbnail = imageURLThumbnail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageURLThumbnail() {
        return imageURLThumbnail;
    }

    public void removeWallpaper(TilePane p) {
        imageView.setImage(null);

        p.getChildren().remove(imageView.getParent());
    }

    public boolean isInMyWallpaper() {
        return inMyWallpapers;
    }

    public void setInMyWallpaper(boolean inMyWallpaper) {
        this.inMyWallpapers = inMyWallpaper;
    }

    public TilePane getTilePaneParent() {
        return tilePaneParent;
    }

    public void setTilePaneParent(TilePane tilePaneParent) {
        this.tilePaneParent = tilePaneParent;
    }
}
