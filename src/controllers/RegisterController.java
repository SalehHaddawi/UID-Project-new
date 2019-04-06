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
        
        if(fullNameTextFiled.getText().isEmpty()){
            fullNameErrorText.setText("Enter Name");
            if(!fullNameTextFiled.getParent().getStyleClass().contains("login-textfield-error")){
                fullNameTextFiled.getParent().getStyleClass().add("login-textfield-error");
            }
            userIsValid = false;
            fullNameTextFiled.requestFocus();
            animateError(fullNameTextFiled.getParent());
        }else if(fullNameTextFiled.getText().length() < 2){
            fullNameErrorText.setText("2 characters at least");
            if(!fullNameTextFiled.getParent().getStyleClass().contains("login-textfield-error")){
                fullNameTextFiled.getParent().getStyleClass().add("login-textfield-error");
            }
            fullNameTextFiled.requestFocus();
            userIsValid = false;
            animateError(fullNameTextFiled.getParent());
        }else{
            fullNameErrorText.setText("");
            fullNameTextFiled.getParent().getStyleClass().remove("login-textfield-error");
        }
        
        
        if(emailTextFiled.getText().isEmpty()){
            emailErrorText.setText("Enter Email");
            if(!emailTextFiled.getParent().getStyleClass().contains("login-textfield-error"))
                emailTextFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            emailTextFiled.requestFocus();
            animateError(emailTextFiled.getParent());
        }else if(!emailTextFiled.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            emailErrorText.setText("Invalid Email");
            if(!emailTextFiled.getParent().getStyleClass().contains("login-textfield-error"))
                emailTextFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            emailTextFiled.requestFocus();
            animateError(emailTextFiled.getParent());
        }/*if(TODO: email is unique){
            emailErrorText.setText("Email is used");
            if(!emailTextFiled.getParent().getStyleClass().contains("login-textfield-error"))
                emailTextFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
        }*/else{
            emailTextFiled.getParent().getStyleClass().remove("login-textfield-error");
            emailErrorText.setText("");
        }
        
        if(passwordFiled.getText().isEmpty()){
            passwordErrorText.setText("Enter Password");
            if(!passwordFiled.getParent().getStyleClass().contains("login-textfield-error"))
                passwordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            passwordFiled.requestFocus();
            animateError(passwordFiled.getParent());
        }else if(passwordFiled.getText().length() < 6){
            passwordErrorText.setText("6 characters at least");
            if(!passwordFiled.getParent().getStyleClass().contains("login-textfield-error"))
                passwordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            passwordFiled.requestFocus();
            animateError(passwordFiled.getParent());
        }else{
            passwordErrorText.setText("");
            passwordFiled.getParent().getStyleClass().remove("login-textfield-error");
        }
        
        if(confirmPasswordFiled.getText().isEmpty()){
            confirmPasswordErrorText.setText("Enter Password Again");
            if(!confirmPasswordFiled.getParent().getStyleClass().contains("login-textfield-error"))
                confirmPasswordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            confirmPasswordFiled.requestFocus();
            animateError(confirmPasswordFiled.getParent());
        }else if(!confirmPasswordFiled.getText().equals(passwordFiled.getText())){
            confirmPasswordErrorText.setText("Passwords Not match");
            if(!confirmPasswordFiled.getParent().getStyleClass().contains("login-textfield-error"))
                confirmPasswordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
            confirmPasswordFiled.requestFocus();
            animateError(confirmPasswordFiled.getParent());
        }else{
            confirmPasswordFiled.getParent().getStyleClass().remove("login-textfield-error");
            confirmPasswordErrorText.setText("");
        }
        
        return userIsValid;
    }
    
    void animateError(Node node){
        Timeline animationTimeline = new Timeline();
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.1), new KeyValue(node.translateXProperty(), 10));
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.2), new KeyValue(node.translateXProperty(), -10));
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(0.3), new KeyValue(node.translateXProperty(), 5));
        KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(0.4), new KeyValue(node.translateXProperty(), -5));
        KeyFrame keyFrame5 = new KeyFrame(Duration.seconds(0.5), new KeyValue(node.translateXProperty(), 0));
        animationTimeline.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5);
        animationTimeline.play();
    }
}
