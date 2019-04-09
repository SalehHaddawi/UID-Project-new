package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
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
    public VBox categoriesVBox;
    @FXML
    public VBox choosenImageVBox;
    @FXML
    public ImageView choosenImageView;
    @FXML
    public JFXSpinner spinner;
    @FXML
    private JFXButton loadMoreButton;

    private List<String> cats = AppData.categories;
    
    int offset = 0;
    int limit = 10;
    int increaseAmount = 5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Collections.shuffle(cats);
        
        loadImagesLines();
    }

    private void loadImagesLines() {
        try {
            int i;
            for (i = offset; i < limit; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DiscoverImagesLine.fxml"));

                Parent root = loader.load();

                DiscoverImagesLineController d = loader.<DiscoverImagesLineController>getController();

                d.init(choosenImageVBox, spinner, choosenImageView);
                
                d.setCategory(cats.get(i));

                categoriesVBox.getChildren().add(root);
            }
            
            offset += i - offset;
            
            limit += increaseAmount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loadMoreButton.toFront();
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
        spinner.setVisible(false);
    }

    @FXML
    private void addImageToMyWallappers(ActionEvent event) {
        AppData.userURLs.add(AppData.choosenImageURL);
    }

    @FXML
    private void onLoadMoreCategories(ActionEvent event) {
        loadImagesLines();        
    }
}
