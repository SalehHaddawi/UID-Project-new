package lunch;

import Model.AppData;
import com.jfoenix.controls.JFXDecorator;
import utils.ResizeHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Lunch extends Application {

    public static Stage appStage;

    @Override
    public void start(Stage stage) throws Exception {
        AppData.loadImagesCategories();
        
        System.out.println("Found " + AppData.categories.size() + " image Categories");
        
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        
        JFXDecorator decorator = new JFXDecorator(stage, root);

        Scene scene = new Scene(decorator);

        stage.setScene(scene);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.getScene().setFill(Color.TRANSPARENT);

        appStage = stage;

        stage.show();

        ResizeHelper.addResizeListener(stage, 1000, 600, Double.MAX_VALUE, Double.MAX_VALUE);        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
