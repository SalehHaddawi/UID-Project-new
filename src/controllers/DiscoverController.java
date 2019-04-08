package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class DiscoverController implements Initializable {

    @FXML
    private VBox categoriesVBox;

    String[] cats = {"GALAXY", "Football", "Snow", "Lightning", "Animals", "Basketball", "Curry", "Storms", "Volcano", "Lord Of The Rings"};
    @FXML
    private VBox choosenImageVBox;
    @FXML
    private ImageView choosenImageView;
    @FXML
    private JFXSpinner spinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadImagesLines();
        
        AppData.choosenImageVBox = choosenImageVBox;
        AppData.choosenImageView = choosenImageView;
        AppData.choosenImagSpinner = spinner;
    }

    void loadImagesLines() {
        try {
            for (String s : cats) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DiscoverImagesLine.fxml"));

                Parent root = loader.load();

                DiscoverImagesLineController d = loader.<DiscoverImagesLineController>getController();

                d.setCategory(s);

                categoriesVBox.getChildren().add(root);
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
    }
}
