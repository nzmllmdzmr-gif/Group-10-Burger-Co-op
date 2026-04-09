package main;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            InputStream is = ImageLoader.class.getResourceAsStream(path);
            if (is == null) {
                System.out.println("Error: Cannot find " + path);
                return null;
            }
            return ImageIO.read(is);
        } catch (Exception e) {
            return null;
        }
    }
}