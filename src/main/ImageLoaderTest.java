package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class ImageLoaderTest {
    @Test
    public void testLoadMyImages() {
        //check if these images can be read
        assertNotNull(ImageLoader.loadImage("/bluechef.png"), "Missing bluechef");
        assertNotNull(ImageLoader.loadImage("/redchef.png"), "Missing redchef");
        assertNotNull(ImageLoader.loadImage("/rawsteak.png"), "Missing rawsteak");
        assertNotNull(ImageLoader.loadImage("/cookedsteak.png"), "Missing cookedsteak");
    }
}