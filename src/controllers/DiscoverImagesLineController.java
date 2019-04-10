package controllers;

import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.GoogleImages;

public class DiscoverImagesLineController implements Initializable {

    @FXML
    private Text categoryText;
    @FXML
    private HBox discoverLineHBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXButton refreshCategoryButton;

    VBox choosenImageVBox;
    JFXSpinner choosenImageSpinner;
    ImageView choosenImageView;
    JFXButton choosenImageButton;

    List<String> imagesURLs;
    Timeline timeline;
    int loadedImages = 0;
    int imagesToload = 4;
    boolean loadedWallpapers = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshCategoryButton.setDisable(true);
    }

    @FXML
    private void refreshCategoryWallpapers(ActionEvent event) {
        discoverLineHBox.getChildren().clear();
        discoverLineHBox.getChildren().add(spinner);

        playRefreshAnimation();

        init(choosenImageVBox, choosenImageSpinner, choosenImageView, choosenImageButton);
    }

    public void init(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView, JFXButton choosenImageButton) {
        this.choosenImageVBox = choosenImageVBox;
        this.choosenImageSpinner = choosenImageSpinner;
        this.choosenImageView = choosenImageView;
        this.choosenImageButton = choosenImageButton;
        
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                imagesURLs = GoogleImages.search(categoryText.getText() + "Wallpaper", imagesToload, loadedImages, true);
            }
        };

        ThreadCompleteListener completeListener = (Runnable runnable) -> {
            Platform.runLater(() -> {
                createWallpapers(choosenImageVBox, choosenImageSpinner, choosenImageView, choosenImageButton);
            });
        };

        run.addListener(completeListener);

        Thread loadingURLs = new Thread(run);
        loadingURLs.start();
    }

    private void createWallpapers(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView, JFXButton choosenImageButton) {
        discoverLineHBox.getChildren().remove(spinner);
        refreshCategoryButton.setDisable(false);

        loadedWallpapers = true;

        try {
            for (int i = 0; i < 4 * 2; i += 2) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imagesURLs.get(i));

                w.setImageURLThumbnail(imagesURLs.get(i + 1));

                w.init(choosenImageVBox, choosenImageSpinner, choosenImageView, choosenImageButton);

                discoverLineHBox.getChildren().add(root);
            }

            loadedImages += 4;
            imagesToload += 4;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCategory(String cat) {
        categoryText.setText(cat);
    }

    public void setImagesURLs(List<String> urls) {
        this.imagesURLs = urls;
    }

    private void playRefreshAnimation() {
        refreshCategoryButton.setDisable(true);

        loadedWallpapers = false;

        if (timeline == null) {
            timeline = new Timeline();
            KeyFrame key1 = new KeyFrame(Duration.seconds(1), new KeyValue(refreshCategoryButton.rotateProperty(), 360));
            timeline.getKeyFrames().add(key1);

            timeline.setOnFinished(value -> {
                if (!loadedWallpapers) {
                    timeline.playFromStart();
                }
            });
        }

        refreshCategoryButton.setRotate(0);
        timeline.playFromStart();
    }
}
