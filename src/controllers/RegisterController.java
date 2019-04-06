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
import javafx.scene.text.Text;
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
        }else{
            fullNameErrorText.setText("");
            fullNameTextFiled.getParent().getStyleClass().remove("login-textfield-error");
        }
        
        if(emailTextFiled.getText().isEmpty()){
            emailErrorText.setText("Enter Email");
            emailTextFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
        }else{
            emailTextFiled.getParent().getStyleClass().remove("login-textfield-error");
            emailErrorText.setText("");
        }
        
        if(passwordFiled.getText().isEmpty()){
            passwordErrorText.setText("Enter Password");
            passwordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
        }else{
            passwordErrorText.setText("");
            passwordFiled.getParent().getStyleClass().remove("login-textfield-error");
        }
        
        if(confirmPasswordFiled.getText().isEmpty()){
            confirmPasswordErrorText.setText("Enter Password Again");
            confirmPasswordFiled.getParent().getStyleClass().add("login-textfield-error");
            userIsValid = false;
        }else{
            confirmPasswordFiled.getParent().getStyleClass().remove("login-textfield-error");
            confirmPasswordErrorText.setText("");
        }
        
        return userIsValid;
    }
}
