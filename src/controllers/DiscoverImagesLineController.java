package controllers;

import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utils.GoogleImagesSearch;

public class DiscoverImagesLineController implements Initializable {

    @FXML
    private Text categoryText;
    @FXML
    private HBox discoverLineHBox;

    List<String> imagesURLs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                imagesURLs = GoogleImagesSearch.search(categoryText.getText() + "Wallpaper", 4);
            }
        };
        
        ThreadCompleteListener completeListener = (Runnable runnable) -> {
            Platform.runLater(()->{
                createWallpapers();
            });
        };
        
        run.addListener(completeListener);
        
        Thread loadingURLs = new Thread(run);
        loadingURLs.start();
    }

    void createWallpapers() {
        try {
            for (int i = 0; i < 4; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();
                
                w.setImageURL(imagesURLs.get(i));
                
                w.intit();
                
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
