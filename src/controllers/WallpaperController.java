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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WallpaperController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private JFXSpinner spinner;

    String imageURL;
    String imageURLThumbnail;
    
    VBox choosenImageVBox;
    JFXSpinner choosenImageSpinner;
    ImageView choosenImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init(VBox choosenImageVBox, JFXSpinner choosenImageSpinner, ImageView choosenImageView) {
        this.choosenImageVBox = choosenImageVBox;
        this.choosenImageSpinner = choosenImageSpinner;
        this.choosenImageView = choosenImageView;
        
        
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
        choosenImageVBox.setVisible(true);
        choosenImageSpinner.setVisible(true);
        
        AppData.choosenImageURL = imageURL;

        Image img = new Image(imageURL, true);

        img.progressProperty().addListener((obs, oldV, newV) -> {
            if ((Double) newV == 1) {
                choosenImageSpinner.setVisible(false);
                
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
            if(newV){ // something wrong happened
                choosenImageSpinner.setVisible(false);
                choosenImageView.setImage(imageView.getImage());
                
                System.out.println("something wrong happened");
            }
        });

        choosenImageView.setImage(img);
    }    
}
