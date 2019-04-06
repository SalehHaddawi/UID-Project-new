package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DiscoverController implements Initializable {

    @FXML
    private VBox categoriesVBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadCategories();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void loadCategories() throws IOException {
        for (int i = 1; i <= 10; i++) {
            Parent root = FXMLLoader.load(getClass().getResource("/views/DiscoverImagesLine.fxml"));
            
            Text category = (Text) root.lookup("#categoryText");
            HBox hbox = (HBox) root.lookup("#discoverLineHBox");
            
            for (int j = 0; j < 4; j++) {
                Parent wallpaper = FXMLLoader.load(getClass().getResource("/views/Wallpaper.fxml"));
                hbox.getChildren().add(wallpaper);
            }
            
            
            category.setText("Hello "+i);
            
            ImageView wallpaperImage = (ImageView) root.lookup("#wallpaperImage");
            //wallpaperImage.setImage(value);
            
            categoriesVBox.getChildren().add(root);
        }
    }
}
