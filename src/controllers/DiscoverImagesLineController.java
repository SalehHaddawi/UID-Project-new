package controllers;

import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.GoogleImages;

public class DiscoverImagesLineController implements Initializable {

    @FXML
    private Text categoryText;
    @FXML
    private HBox discoverLineHBox;
    @FXML
    private JFXSpinner spinner;

    List<String> imagesURLs;
    ListChangeListener<Node> listener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void init(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView) {
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                imagesURLs = GoogleImages.search(categoryText.getText() + "Wallpaper", 4, true);
            }
        };

        ThreadCompleteListener completeListener = (Runnable runnable) -> {
            Platform.runLater(() -> {
                createWallpapers(choosenImageVBox, choosenImageSpinner, choosenImageView);
            });
        };

        run.addListener(completeListener);

        Thread loadingURLs = new Thread(run);
        loadingURLs.start();
    }

    private void createWallpapers(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView) {
        discoverLineHBox.getChildren().remove(spinner);
        
        try {
            for (int i = 0; i < 4 * 2; i += 2) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imagesURLs.get(i));

                w.setImageURLThumbnail(imagesURLs.get(i + 1));

                w.init(choosenImageVBox, choosenImageSpinner, choosenImageView);

                discoverLineHBox.getChildren().add(root);
            }
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
}
