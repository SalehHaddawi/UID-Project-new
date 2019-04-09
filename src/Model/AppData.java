package Model;

import controllers.DiscoverController;
import controllers.SearchController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppData {
    public static String choosenImageURL;
    
    public static List<String> categories = new ArrayList<>(900);
    public static List<String> userURLs = new ArrayList<>();
    
    public static void loadImagesCategories(){
        try {
            Scanner scanner = new Scanner(new File("src/categories/Images_Categories.txt"));
            while (scanner.hasNextLine()) {                
                categories.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
