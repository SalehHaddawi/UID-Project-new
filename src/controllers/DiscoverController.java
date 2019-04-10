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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lunch.Lunch;
import utils.ImagesUtils;
import utils.Toast;

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
    @FXML
    private AnchorPane anchorePane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton addImageToMyWallpapersButton;
    @FXML
    private ImageView noInternetImageView;

    private final List<String> cats = AppData.categories;

    int offset = 0;
    int limit = 15;
    int increaseAmount = 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Collections.shuffle(cats);

        anchorePane.widthProperty().addListener((obs, oldV, newV) -> {
            scrollPane.setMinWidth(anchorePane.getWidth());
        });

        if (ImagesUtils.isNetAvailable()) {
            categoriesVBox.getChildren().remove(noInternetImageView);
            loadImagesLines();
        }else{
            loadMoreButton.setText("Try Agian");
        }
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
        spinner.setVisible(false);
    }

    @FXML
    private void addImageToMyWallappers(ActionEvent event) {
        choosenImageVBox.setVisible(false);

        ImagesUtils.saveImageToHD(null);
    }

    @FXML
    private void onLoadMoreCategories(ActionEvent event) {
        if (ImagesUtils.isNetAvailable()) {
            categoriesVBox.getChildren().remove(noInternetImageView);
            loadImagesLines();
        }else{
            Toast.makeText(Lunch.appStage, "Please Check Your Internet Connection", 1500);
            
            loadMoreButton.setText("Try Agian");
        }
    }

    private void loadImagesLines() {
        loadMoreButton.setText("Load More");
                
        try {
            int i;
            for (i = offset; i < limit; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DiscoverImagesLine.fxml"));

                Parent root = loader.load();

                DiscoverImagesLineController d = loader.<DiscoverImagesLineController>getController();

                d.init(choosenImageVBox, spinner, choosenImageView, addImageToMyWallpapersButton);

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
}
