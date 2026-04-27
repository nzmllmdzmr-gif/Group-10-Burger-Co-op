package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class IntroPanel extends JPanel {

    private BufferedImage[] frames;
    private int currentFrame = 0;

    public IntroPanel(JFrame frame) {
        this.setFocusable(true);

        //the total is 90 fps
        int totalFrames = 90;
        frames = new BufferedImage[totalFrames];

        //loading images
        for (int i = 0; i < totalFrames; i++) {
        	String path = String.format("/ezgif-frame-%03d.png", i + 1);
            frames[i] = ImageLoader.loadImage(path);

            //display if not success
            if (frames[i] == null) {
                System.out.println("Failed to load: " + path);
            }
        }

        //100ms for each fps
        Timer timer = new Timer(100, e -> {
            currentFrame++;

            //go the game when finish images
            if (currentFrame >= frames.length) {
                ((Timer) e.getSource()).stop();

                frame.setContentPane(new GamePanel());
                frame.revalidate();
            }

            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentFrame < frames.length && frames[currentFrame] != null) {
            g.drawImage(frames[currentFrame], 0, 0, getWidth(), getHeight(), null);
        }
    }
}