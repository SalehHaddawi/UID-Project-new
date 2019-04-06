package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class WallpaperController implements Initializable {

    @FXML
    private ImageView wallpaperImage;
    @FXML
    private Button addToMyWallpaperButton;
    @FXML
    private HBox hbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hbox.setOnMouseEntered(event->{
            addToMyWallpaperButton.setVisible(true);
        });
        
        hbox.setOnMouseExited(event->{
            addToMyWallpaperButton.setVisible(false);
        });
        
        System.out.println("Iam An Image");
    }    

    @FXML
    private void addToMyWallpapers(ActionEvent event) {
        
    }
    
}
