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
import java.net.URL;
import java.net.URLConnection;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class ImagesUtils {

    private static void tryDownload(String imageURL, String imageThumbnail) {
        try {
            download(imageURL, null);
        } catch (IOException e) {
            try {
                download(imageThumbnail, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void tryDownload(String imageURL, String imageThumbnail, String path) {
        try {
            download(imageURL, path);
        } catch (IOException e) {
            try {
                download(imageThumbnail, path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void download(String imageURL, String path) throws IOException {
        try {
            URL url = new URL(imageURL);

            int pushbackLimit = 100;

            InputStream urlStream = url.openStream();
            PushbackInputStream pushUrlStream = new PushbackInputStream(urlStream, pushbackLimit);
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
            ByteArrayInputStream bais = new ByteArrayInputStream(firstBytes);
            String mimeType = URLConnection.guessContentTypeFromStream(bais);
            if (mimeType.startsWith("image/")) {
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
        }
    }

    public static void saveImageToHD(String path) {
        NotifyingRunnable run = new NotifyingRunnable() {
            @Override
            public void doRun() {
                tryDownload(AppData.choosenWallpaper.getImageURL(), AppData.choosenWallpaper.getImageURLThumbnail(),path);
            }
        };

        ThreadCompleteListener listener = (Runnable runnable) -> {
            System.out.println("Image Saved");
        };

        run.addListener(listener);

        Thread saveImagesToHDThread = new Thread(run);
        saveImagesToHDThread.start();
    }
    
    public static void saveImageAsToHD(){
        //Show save file dialog
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png","*.jpeg","*.jpg","*.gif","*.tif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(lunch.Lunch.appStage);

        if (file != null) {
            ImagesUtils.saveImageToHD(file.getAbsolutePath());
        }
    }
}
