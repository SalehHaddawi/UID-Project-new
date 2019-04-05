package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lunch.Lunch;

public class LoginController implements Initializable {

    @FXML
    private TextField emailTextFiled;
    @FXML
    private PasswordField passwordFiled;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToRegister(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));

        Scene scene = new Scene(root);

        Lunch.appStage.setScene(scene);
    }

    @FXML
    private void login(ActionEvent event) {
    }
}
