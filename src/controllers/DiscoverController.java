package controllers;

import Model.AppData;
import com.jfoenix.controls.JFXSpinner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
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
    @FXML
    private VBox choosenImageVBox;
    @FXML
    private ImageView choosenImageView;
    @FXML
    private JFXSpinner spinner;

    List<String> cats = AppData.categories;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Collections.shuffle(cats);
        
        loadImagesLines();

        AppData.choosenImageVBox = choosenImageVBox;
        AppData.choosenImageView = choosenImageView;
        AppData.choosenImagSpinner = spinner;        
    }

    void loadImagesLines() {
        try {
            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DiscoverImagesLine.fxml"));

                Parent root = loader.load();

                DiscoverImagesLineController d = loader.<DiscoverImagesLineController>getController();

                d.setCategory(cats.get(i));

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

    void loadCategories() {
        try {
            Scanner scanner = new Scanner(new File("src/categories/Images Categories.txt"));
            while (scanner.hasNextLine()) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
