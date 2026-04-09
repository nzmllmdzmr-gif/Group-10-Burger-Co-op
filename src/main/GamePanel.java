package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage; 

// panel
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
    // two player
    Player p1 = new Player("Player1", 150, 300);
    Player p2 = new Player("Player2", 600, 300);
    private Grill grill = new Grill();

    private String warningMessage = "";
    private int messageTimer = 0;

    //load the images
    private BufferedImage blueChefImg = ImageLoader.loadImage("/bluechef.png");
    private BufferedImage redChefImg = ImageLoader.loadImage("/redchef.png");
    private BufferedImage rawSteakImg = ImageLoader.loadImage("/rawsteak.png");
    private BufferedImage cookedSteakImg = ImageLoader.loadImage("/cookedsteak.png");
    
    //chef with steak
    private BufferedImage blueChefWithSteakImg = ImageLoader.loadImage("/bluechefwithsteak.png");
    private BufferedImage redChefWithSteakImg = ImageLoader.loadImage("/redchefwithsteak.png");

    boolean w, s, a, d;
    boolean up, down, left, right;

    public GamePanel() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) w = true;
                if (key == KeyEvent.VK_S) s = true;
                if (key == KeyEvent.VK_A) a = true;
                if (key == KeyEvent.VK_D) d = true;
                
                if (key == KeyEvent.VK_UP) up = true;
                if (key == KeyEvent.VK_DOWN) down = true;
                if (key == KeyEvent.VK_LEFT) left = true;
                if (key == KeyEvent.VK_RIGHT) right = true;

                // 空格键交互space key using
                if (key == KeyEvent.VK_SPACE) handleInteraction(p1);
                if (key == KeyEvent.VK_ENTER) handleInteraction(p2);
            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) w = false;
                if (key == KeyEvent.VK_S) s = false;
                if (key == KeyEvent.VK_A) a = false;
                if (key == KeyEvent.VK_D) d = false;
                
                if (key == KeyEvent.VK_UP) up = false;
                if (key == KeyEvent.VK_DOWN) down = false;
                if (key == KeyEvent.VK_LEFT) left = false;
                if (key == KeyEvent.VK_RIGHT) right = false;
            }
        });

        // circular
        Timer timer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // P1 
                if (w) p1.move(0, -1);
                if (s) p1.move(0, 1);
                if (a) p1.move(-1, 0);
                if (d) p1.move(1, 0);
                
                // P2 
                if (up) p2.move(0, -1);
                if (down) p2.move(0, 1);
                if (left) p2.move(-1, 0);
                if (right) p2.move(1, 0);
                
                grill.update(); 
                
                if (messageTimer > 0) messageTimer--;
                else warningMessage = "";

                repaint(); 
            }
        });
        grill.placePatty(0, "Beef");
        grill.placePatty(1, "Beef");
        timer.start();
    }

    //only can pick if well done
    private void handleInteraction(Player p) {
        if (p.getHeldItem().equals("Nothing")) {
            for (Patty patty : grill.getPattiesOnGrill()) {
                double dist = Math.sqrt(Math.pow(p.getX() - patty.getX(), 2) + Math.pow(p.getY() - patty.getY(), 2));
                if (dist < 80) { 
                    if (patty.getProgress() >= 100) {
                        p.setHeldItem("CookedSteak");
                        warningMessage = "Got it!";
                    } else {
                        warningMessage = "Wait! It's still raw!";
                    }
                    messageTimer = 50;
                    break;
                }
            }
        } else {
            p.setHeldItem("Nothing");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
  
        g.setColor(GameSettings.COLOR_COOKING);
        g.fillRect(0, 0, 400, 600);
        g.setColor(GameSettings.COLOR_ASSEMBLY);
        g.fillRect(400, 0, 488, 600);
        g.setColor(Color.BLACK);
        g.drawLine(400, 0, 400, 600);

        // add grill
        g.setColor(GameSettings.COLOR_GRILL);
        g.fillRect(200, 250, 488, 200);
        g.setColor(Color.BLACK);
        g.drawRect(200, 250, 488, 200);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 888, 80);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 888, 80);
        
        //draw steak
        for (Patty p : grill.getPattiesOnGrill()) {
            BufferedImage currentImg = (p.getProgress() >= 100) ? cookedSteakImg : rawSteakImg;
            if (currentImg != null) {
                g.drawImage(currentImg, p.getX(), p.getY(), 75, 75, null);
    
                g.setColor(Color.WHITE);
                g.drawString(p.getProgress() + "%", p.getX() + 20, p.getY() - 5);
            }
        }

        //player1(size85)
        if (!p1.getHeldItem().equals("Nothing") && blueChefWithSteakImg != null) {
            g.drawImage(blueChefWithSteakImg, p1.getX(), p1.getY(), 85, 85, null);
        } else if (blueChefImg != null) {
            g.drawImage(blueChefImg, p1.getX(), p1.getY(), 85, 85, null);
        }
        
        //player2(size85x85)
        if (!p2.getHeldItem().equals("Nothing") && redChefWithSteakImg != null) {
            g.drawImage(redChefWithSteakImg, p2.getX(), p2.getY(), 85, 85, null);
        } else if (redChefImg != null) {
            g.drawImage(redChefImg, p2.getX(), p2.getY(), 85, 85, null);
        }

        //warings words
        if (!warningMessage.isEmpty()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(warningMessage, 350, 150);
        }

        //words showed
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("P1 Holding: " + p1.getHeldItem(), 20, 100);
        g.drawString("P2 Holding: " + p2.getHeldItem(), 700, 100);
    }
}