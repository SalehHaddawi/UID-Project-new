package controllers;

import Model.AppData;
import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import impl.org.controlsfx.skin.AutoCompletePopup;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.controlsfx.control.textfield.TextFields;
import utils.GoogleImages;
import utils.ImagesUtils;

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
    @FXML
    private JFXButton addImageToMyWallpapersButton;
    @FXML
    private TilePane suggestionsTilePane;
    @FXML
    private HBox loadingSearchHBox;
    @FXML
    private ImageView noInternetImageView;
    @FXML
    private JFXButton searchButton;
    @FXML
    private ImageView noResultFound;
    @FXML
    private HBox searchHBox;

    List<String> imagesURLs;
    int searchLimit = 100;
    int suggestionsLimit = 15;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(searchTextField, AppData.categories);

        Collections.shuffle(AppData.categories);

        createSuggestionsButtons();
    }

    @FXML
    private void onSearch(ActionEvent event) {

        if (searchTextField.getText().trim().isEmpty()) {
            searchHBox.setStyle("-fx-border-color: #FFBABA;");
            
            return;
        }else{
            searchHBox.setStyle("");
        }

        wallpapersTilePane.getChildren().clear();

        if (!ImagesUtils.isNetAvailable()) {
            noInternetImageView.setVisible(true);
            noResultFound.setVisible(false);
            return;
        } else {
            noInternetImageView.setVisible(false);
        }

        searchButton.setDisable(true);
        suggestionsTilePane.setDisable(true);
        loadingSearchHBox.setVisible(true);
        
        String searchText = searchTextField.getText();
        if(!searchText.endsWith("allpaper")||!searchText.endsWith("allpapers")){
            searchText += "Wallpapers";
        }
        
        String finalSearchText = searchText;

        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                imagesURLs = GoogleImages.search(finalSearchText, searchLimit, true);
            }
        };

        ThreadCompleteListener completeListener = (Runnable runnable) -> {
            Platform.runLater(() -> {
                createWallpapers();
            });
        };

        run.addListener(completeListener);

        Thread loadingURLs = new Thread(run);
        loadingURLs.start();
    }

    @FXML
    private void closeChoosenImage(ActionEvent event) {
        choosenImageVBox.setVisible(false);
    }

    @FXML
    private void addImageToMyWallappers(ActionEvent event) {
        //AppData.userURLs.add(AppData.choosenImageURL);
        choosenImageVBox.setVisible(false);
        ImagesUtils.saveImageToHD(null);
    }

    private void createSuggestionsButtons() {
        for (int i = 0; i < suggestionsLimit; i++) {
            JFXButton suggestion = new JFXButton(AppData.categories.get(i));
            suggestion.setTextFill(Paint.valueOf("#7f7777"));

            suggestionsTilePane.getChildren().add(suggestion);

            suggestion.setOnMouseClicked(value -> {
                searchTextField.setText(suggestion.getText());
                onSearch(null);
            });
        }
    }

    private void createWallpapers() {
        loadingSearchHBox.setVisible(false);
        searchButton.setDisable(false);
        suggestionsTilePane.setDisable(false);

        if (imagesURLs.isEmpty()) {
            noResultFound.setVisible(true);
        } else {
            noResultFound.setVisible(false);
        }

        try {
            for (int i = 0; i < imagesURLs.size(); i += 2) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Wallpaper.fxml"));

                Parent root = loader.load();

                WallpaperController w = loader.<WallpaperController>getController();

                w.setImageURL(imagesURLs.get(i));

                w.setImageURLThumbnail(imagesURLs.get(i + 1));

                w.init(choosenImageVBox, spinner, choosenImageView, addImageToMyWallpapersButton);

                wallpapersTilePane.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
