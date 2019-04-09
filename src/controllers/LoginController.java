package controllers;

import com.jfoenix.controls.JFXDecorator;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lunch.Lunch;
import utils.ResizeHelper;

public class LoginController implements Initializable {

    @FXML
    private TextField emailTextFiled;
    @FXML
    private PasswordField passwordFiled;
    @FXML
    private Text emailErrorText;
    @FXML
    private Text passowrdErrorText;

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

        JFXDecorator decorator = new JFXDecorator(Lunch.appStage, root);
        decorator.setCustomMaximize(true);

        Scene scene = new Scene(decorator);

        Lunch.appStage.setScene(scene);
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        if (isValidUser()) {
            Lunch.appStage.close();
            
            Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));

            Lunch.appStage = new Stage();
            
            JFXDecorator decorator = new JFXDecorator(Lunch.appStage, root);

            Scene scene = new Scene(decorator);

            
            Lunch.appStage.setScene(scene);
            Lunch.appStage.show();

            ResizeHelper.addResizeListener(Lunch.appStage);
        }
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Lunch.appStage.setIconified(true);
    }

    boolean isValidUser() {
        boolean userIsValid = true;

        if (emailTextFiled.getText().isEmpty()) {
            emailErrorText.setText("Enter Email");
            if (!emailTextFiled.getParent().getStyleClass().contains("login-textfield-error")) {
                emailTextFiled.getParent().getStyleClass().add("login-textfield-error");
            }
            userIsValid = false;
        } else {
            emailErrorText.setText("");
            emailTextFiled.getParent().getStyleClass().remove("login-textfield-error");
        }

        if (passwordFiled.getText().isEmpty()) {
            passowrdErrorText.setText("Enter Password");
            if (!passwordFiled.getParent().getStyleClass().contains("login-textfield-error")) {
                passwordFiled.getParent().getStyleClass().add("login-textfield-error");
            }
            userIsValid = false;
        } else {
            passowrdErrorText.setText("");
            passwordFiled.getParent().getStyleClass().remove("login-textfield-error");
        }

        return userIsValid;
    }
}
