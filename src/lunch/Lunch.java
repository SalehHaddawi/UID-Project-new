package lunch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Lunch extends Application {
    
    public static Stage appStage;
        
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.initStyle(StageStyle.TRANSPARENT);
        
        appStage = stage;
        
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
