package utils;

import Model.AppData;
import Threading.NotifyingRunnable;
import Threading.ThreadCompleteListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class ImagesUtils {
    
    static boolean lastOperation;

    private static boolean tryDownload(String imageURL, String imageThumbnail, String path) {
        try {
            download(imageURL, path);
            return true;
        } catch (IOException e) {
            try {
                download(imageThumbnail, path);
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return false;
    }

    private static void download(String imageURL, String path) throws IOException {
        InputStream urlStream = null;
        PushbackInputStream pushUrlStream = null;
        ByteArrayInputStream bais = null;

        try {
            URL url = new URL(imageURL);

            int pushbackLimit = 100;

            urlStream = url.openStream();
            pushUrlStream = new PushbackInputStream(urlStream, pushbackLimit);
            byte[] firstBytes = new byte[pushbackLimit];
            // download the first initial bytes into a byte array, which we will later pass to 
            // URLConnection.guessContentTypeFromStream  
            pushUrlStream.read(firstBytes);
            // push the bytes back onto the PushbackInputStream so that the stream can be read 
            // by ImageIO reader in its entirety
            pushUrlStream.unread(firstBytes);

            String imageType = null;
            // Pass the initial bytes to URLConnection.guessContentTypeFromStream in the form of a
            // ByteArrayInputStream, which is mark supported.
            bais = new ByteArrayInputStream(firstBytes);
            String mimeType = URLConnection.guessContentTypeFromStream(bais);
            if (mimeType != null && mimeType.startsWith("image/")) {
                imageType = mimeType.substring("image/".length());
            } else {
                throw new IOException("Can't get Image type");
            }

            // read in image
            BufferedImage inputImage = ImageIO.read(pushUrlStream);

            File outputImage = (path == null) ? new File("src/../wallpapers/" + System.currentTimeMillis() + "." + imageType)
                    : new File(path);

            ImageIO.write(inputImage, imageType, outputImage);
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlStream != null) {
                urlStream.close();
            }
            if (pushUrlStream != null) {
                pushUrlStream.close();
            }
            if (bais != null) {
                bais.close();
            }
        }
    }

    public static void saveImageToHD(String path) {
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                lastOperation = tryDownload(AppData.choosenWallpaper.getImageURL(), AppData.choosenWallpaper.getImageURLThumbnail(), path);
            }
        };

        ThreadCompleteListener listener = (Runnable runnable) -> {
            Platform.runLater(() -> {
                if(lastOperation){
                String message = path == null ? "Wallpaper Added Seccussfully" : "Wallpaper saved Successfully";
                Toast.makeText(lunch.Lunch.appStage, message, 500);
                }else{
                    Toast.makeText(lunch.Lunch.appStage, "Couldn't Complete Operation!", 500);
                }
            });
            System.out.println("Image Saved");
        };

        run.addListener(listener);

        Thread saveImagesToHDThread = new Thread(run);
        saveImagesToHDThread.start();
    }

    public static void saveImageAsToHD() {
        //Show save file dialog
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpeg", "*.jpg", "*.gif", "*.tif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(lunch.Lunch.appStage);

        if (file != null) {
            ImagesUtils.saveImageToHD(file.getAbsolutePath());
        }
    }

    public static boolean isNetAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
