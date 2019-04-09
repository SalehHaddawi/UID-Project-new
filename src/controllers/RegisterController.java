package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lunch.Lunch;

public class RegisterController implements Initializable {

    @FXML
    private TextField fullNameTextFiled;
    @FXML
    private TextField emailTextFiled;
    @FXML
    private PasswordField passwordFiled;
    @FXML
    private PasswordField confirmPasswordFiled;
    @FXML
    private Text fullNameErrorText;
    @FXML
    private Text emailErrorText;
    @FXML
    private Text passwordErrorText;
    @FXML
    private Text confirmPasswordErrorText;

    private Node focusNode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToLogin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));

        Scene scene = new Scene(root);

        Lunch.appStage.setScene(scene);
    }

    @FXML
    private void register(ActionEvent event) {
        isValidUser();
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Lunch.appStage.setIconified(true);
    }

    boolean isValidUser() {
        boolean userIsValid = true;
        focusNode = null;

        if (fullNameTextFiled.getText().isEmpty()) {

            showError(fullNameTextFiled, fullNameErrorText, "Enter Name");

            userIsValid = false;
        } else if (fullNameTextFiled.getText().length() < 2) {

            showError(fullNameTextFiled, fullNameErrorText, "2 characters at least");

            userIsValid = false;
        } else {
            fullNameErrorText.setText("");
            fullNameTextFiled.getParent().getStyleClass().remove("login-textfield-error");
        }

        if (emailTextFiled.getText().isEmpty()) {

            showError(emailTextFiled, emailErrorText, "Enter Email");

            userIsValid = false;
        } else if (!emailTextFiled.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            showError(emailTextFiled, emailErrorText, "Invalid Email");

            userIsValid = false;
        } else {
            emailTextFiled.getParent().getStyleClass().remove("login-textfield-error");
            emailErrorText.setText("");
        }

        if (passwordFiled.getText().isEmpty()) {

            showError(passwordFiled, passwordErrorText, "Enter Password");

            userIsValid = false;
        } else if (passwordFiled.getText().length() < 6) {

            showError(passwordFiled, passwordErrorText, "6 characters at least");

            userIsValid = false;
        } else {
            passwordErrorText.setText("");
            passwordFiled.getParent().getStyleClass().remove("login-textfield-error");
        }

        if (confirmPasswordFiled.getText().isEmpty()) {

            showError(confirmPasswordFiled, confirmPasswordErrorText, "Enter Password Again");

            userIsValid = false;
        } else if (!confirmPasswordFiled.getText().equals(passwordFiled.getText())) {

            showError(confirmPasswordFiled, confirmPasswordErrorText, "Passwords Not match");

            userIsValid = false;
        } else {
            confirmPasswordFiled.getParent().getStyleClass().remove("login-textfield-error");
            confirmPasswordErrorText.setText("");
        }

        return userIsValid;
    }

    void showError(TextField textField, Text errorText, String errorMessage) {
        if (!textField.getParent().getStyleClass().contains("login-textfield-error")) {
            textField.getParent().getStyleClass().add("login-textfield-error");
        }

        errorText.setText(errorMessage);

        animateError(textField.getParent());

        if (focusNode == null) {
            focusNode = textField;
            focusNode.requestFocus();
        }   
    }

    void animateError(Node node) {
        Timeline animationTimeline = new Timeline();
        KeyFrame key1 = new KeyFrame(Duration.seconds(0.1), new KeyValue(node.translateXProperty(), 10));
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.2), new KeyValue(node.translateXProperty(), -10));
        KeyFrame key3 = new KeyFrame(Duration.seconds(0.3), new KeyValue(node.translateXProperty(), 5));
        KeyFrame key4 = new KeyFrame(Duration.seconds(0.4), new KeyValue(node.translateXProperty(), -5));
        KeyFrame key5 = new KeyFrame(Duration.seconds(0.5), new KeyValue(node.translateXProperty(), 0));
        animationTimeline.getKeyFrames().addAll(key1, key2, key3, key4, key5);
        animationTimeline.play();
    }
}
