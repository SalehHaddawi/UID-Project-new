package Model;

import com.jfoenix.controls.JFXSpinner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AppData {
    public static VBox choosenImageVBox;
    public static ImageView choosenImageView;
    public static JFXSpinner choosenImagSpinner;
    
    public static List<String> categories = new ArrayList<>(900);
    
    public static void loadImagesCategories(){
        try {
            Scanner scanner = new Scanner(new File("src/categories/Images Categories.txt"));
            while (scanner.hasNextLine()) {                
                categories.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
