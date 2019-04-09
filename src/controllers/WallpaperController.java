package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class WallpaperController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private JFXSpinner spinner;

    String imageURL;
    String imageURLThumbnail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void intit() {
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

    @FXML
    private void showChoosenImage(MouseEvent event) {
        AppData.choosenImageVBox.setVisible(true);
        AppData.choosenImagSpinner.setVisible(true);

        Image img = new Image(imageURL, true);

        img.progressProperty().addListener((obs, oldV, newV) -> {
            if ((Double) newV == 1) {
                AppData.choosenImagSpinner.setVisible(false);
                Timeline timeline = new Timeline();

                AppData.choosenImageView.setScaleX(0);
                AppData.choosenImageView.setScaleY(0);

                KeyFrame key1 = new KeyFrame(Duration.seconds(0.3), new KeyValue(AppData.choosenImageView.scaleXProperty(), 1));
                KeyFrame key2 = new KeyFrame(Duration.seconds(0.3), new KeyValue(AppData.choosenImageView.scaleYProperty(), 1));

                timeline.getKeyFrames().addAll(key1, key2);
                timeline.play();
            }
        });

        AppData.choosenImageView.setImage(img);
    }
}
