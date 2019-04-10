package controllers;

import Model.AppData;
import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import utils.GoogleImages;

public class SearchController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private TilePane wallpapersTilePane;
    @FXML
    private VBox choosenImageVBox;
    @FXML
    private ImageView choosenImageView;
    @FXML
    private JFXSpinner spinner;
    
    List<String> imagesURLs;
    int searchLimit = 100;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }        

    @FXML
    private void onSearch(ActionEvent event) {
        wallpapersTilePane.getChildren().clear();
        
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                imagesURLs = GoogleImages.search(searchTextField.getText() , searchLimit, true);
            }
        };

        ThreadCompleteListener completeListener = (Runnable runnable) -> {
            Platform.runLater(() -> {
                createWallpapers(choosenImageVBox,spinner,choosenImageView);
            });
        };

        run.addListener(completeListener);

        Thread loadingURLs = new Thread(run);
        loadingURLs.start();
    }
    
    private void createWallpapers(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView) {
        try {
            for (int i = 0; i < imagesURLs.size(); i += 2) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imagesURLs.get(i));

                w.setImageURLThumbnail(imagesURLs.get(i + 1));

                w.init(choosenImageVBox, choosenImageSpinner, choosenImageView);

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
    private void addImageToMyWallappers(ActionEvent event) {
         AppData.userURLs.add(AppData.choosenImageURL);
    }
}
