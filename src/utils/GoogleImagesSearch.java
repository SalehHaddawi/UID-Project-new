package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GoogleImagesSearch {
    public static java.util.List<String> search(String keyword, int limit) {
        java.util.List<String> imagesURLs = new java.util.ArrayList<String>();
        
        File pythonFile = new File("src/executable/google_images_download.exe");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec(pythonFile.getAbsolutePath() + " --keywords \"" + keyword + "\" --safe_search --print_paths --limit " + limit + " --no_download");
           
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
                        
            String line = null;
            while ((line = bre.readLine()) != null) {
                if (line.contains("http")) {
                    imagesURLs.add(line.substring(line.indexOf(":") + 1).trim());
                }
            }
            
            bre.close();

            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return imagesURLs;
    }
    
    public static java.util.List<String> search(String keyword, int limit, int offset) {
        java.util.List<String> imagesURLs = new java.util.ArrayList<String>();
        
        File pythonFile = new File("src/executable/google_images_download.exe");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec(pythonFile.getAbsolutePath() + " --keywords \"" + keyword + "\" --format \"png\"--safe_search --offset "+offset+" --print_paths --limit " + limit + " --no_download");
           
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
                        
            String line = null;
            while ((line = bre.readLine()) != null) {
                if (line.contains("http")) {
                    imagesURLs.add(line.substring(line.indexOf(":") + 1).trim());
                }
            }
            
            bre.close();

            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return imagesURLs;
    }
}
