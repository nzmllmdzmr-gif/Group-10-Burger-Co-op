package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Iterator; 
// panel
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	//把计数板实体化一下make zihan zhangs' entity,make the scoreboard entity
	private ScoreManager scoreManager = new ScoreManager(); 
	private Order currentOrder = new Order("Steak", 20); //make a task for steak
	private int t = 0;
	
	//the bgm
	private MainBgm bgm = new MainBgm();
	private SteakBgm steakBgm = new SteakBgm();

    //two player
    Player p1 = new Player("Player1", 150, 300);
    Player p2 = new Player("Player2", 600, 300);
    private Grill grill = new Grill();

    private String warningMessage = "";
    private int messageTimer = 0;

    private BufferedImage blueChefImg = ImageLoader.loadImage("/bluechef.png");
    private BufferedImage redChefImg = ImageLoader.loadImage("/redchef.png");
    private BufferedImage rawSteakImg = ImageLoader.loadImage("/rawsteak.png");
    private BufferedImage cookedSteakImg = ImageLoader.loadImage("/cookedsteak.png");
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
                // P1 move
                if (key == KeyEvent.VK_W) w = true;
                if (key == KeyEvent.VK_S) s = true;
                if (key == KeyEvent.VK_A) a = true;
                if (key == KeyEvent.VK_D) d = true;
                
                // P2 move
                if (key == KeyEvent.VK_UP) up = true;
                if (key == KeyEvent.VK_DOWN) down = true;
                if (key == KeyEvent.VK_LEFT) left = true;
                if (key == KeyEvent.VK_RIGHT) right = true;

                // P1 spacekey
                if (key == KeyEvent.VK_SPACE) handleInteraction(p1);
                // P2 Shiftkay
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

        Timer timer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (w) p1.move(0, -1);
                if (s) p1.move(0, 1);
                if (a) p1.move(-1, 0);
                if (d) p1.move(1, 0);
                
                if (up) p2.move(0, -1);
                if (down) p2.move(0, 1);
                if (left) p2.move(-1, 0);
                if (right) p2.move(1, 0);
                
                grill.update(); 
                
                //check if have the steak
                if (grill.getPattiesOnGrill().size() > 0) {
                    steakBgm.startSizzle();
                } else {
                    steakBgm.stopSizzle();
                }
                
                //the timer
                t++;
                if (t >= 60) { 
                    currentOrder.reduceTime();
                    if (currentOrder.isExpired()) {
                        scoreManager.deductTimeoutScore();
                        currentOrder = new Order("Steak", 20);
                    }
                    t = 0;
                }
                
                if (messageTimer > 0) messageTimer--;
                else warningMessage = "";

                repaint(); 
            }
        });
        grill.placePatty(0, "Beef");
        grill.placePatty(1, "Beef");
        
        //play the mainbgm
        bgm.playMusic();
        
        timer.start();
    }

    private void handleInteraction(Player p) {
    	// pick up raw burger
    	if (p.getHeldItem().equals("Nothing")
    	        && p.getX() >= 0 && p.getX() <= 230
    	        && p.getY() >= 160 && p.getY() <= 540) {

    	    p.setHeldItem("RawBurger");
    	    warningMessage = "Got raw burger!";
    	    messageTimer = 50;
    	    return;
    	}

    	// pick up raw steak
    	if (p.getHeldItem().equals("Nothing")
    	        && p.getX() >= 0 && p.getX() <= 230
    	        && p.getY() >= 555 && p.getY() <= 935) {

    	    p.setHeldItem("RawSteak");
    	    warningMessage = "Got raw steak!";
    	    messageTimer = 50;
    	    return;
    	}
        //only if player handle the well donw steak and near the deliver area
        if (p.getHeldItem().equals("CookedSteak") && p.getX() > 750) {
            scoreManager.addSteakScore();
            currentOrder = new Order("Steak", 20);
            p.setHeldItem("Nothing");
            return; 
        }

        if ((p.getHeldItem().equals("RawSteak") || p.getHeldItem().equals("RawBurger"))
                && p.getX() > 200 && p.getX() < 580
                && p.getY() > 200 && p.getY() < 480) {

            for (int i = 0; i < 6; i++) {
                if (grill.placePatty(i, p.getHeldItem())) {
                    p.setHeldItem("Nothing");
                    warningMessage = "Cooking " + p.getHeldItem() + "...";
                    messageTimer = 50;
                    return;
                }
            }

            warningMessage = "Grill is full!";
            messageTimer = 50;
            return;
        } 

        if (p.getHeldItem().equals("Nothing")) {
            Iterator<Patty> it = grill.getPattiesOnGrill().iterator();
            while (it.hasNext()) {
                Patty patty = it.next();
                double dist = Math.sqrt(Math.pow(p.getX() - patty.getX(), 2) + Math.pow(p.getY() - patty.getY(), 2));
                
                if (dist < 80) { 
                    if (patty.getProgress() >= 100) {
                        p.setHeldItem("CookedSteak");
                        warningMessage = "Got it!";
                        it.remove(); 
                    } else {
                        warningMessage = "Wait! It's still raw!";
                    }
                    messageTimer = 50;
                    break;
                }
            }
        } else {
            return;
        }
    }

    //画图的地方！！！for drawing the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(200, 200, 200)); 
        g.fillRect(0, 80, 165, 520); 
        g.setColor(Color.BLACK);
        g.drawRect(0, 80, 165, 520);

        g.setColor(new Color(180, 180, 180));
        g.fillRect(5, 85, 155, 250); 
        g.setColor(Color.BLACK);
        g.drawRect(5, 85, 155, 250);
        g.drawString("RAW BURGER", 40, 210);

        g.setColor(new Color(180, 180, 180));
        g.fillRect(5, 345, 155, 250); 
        g.setColor(Color.BLACK);
        g.drawRect(5, 345, 155, 250);
        g.drawString("RAW STEAK", 45, 470);
        
        int bevX = 650; 
        int bevY = 480; 
        int bevWidth = 120; 
        int bevHeight = 100;

        g.setColor(new Color(173, 216, 230)); 
        g.fillRect(bevX, bevY, bevWidth, bevHeight); 
        g.setColor(Color.BLACK);
        g.drawRect(bevX, bevY, bevWidth, bevHeight);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("COLA", bevX + 40, bevY + 55);
    
        g.setColor(GameSettings.COLOR_GRILL); 
        g.fillRect(230, 260, 320, 200); 
        g.setColor(Color.BLACK);
        g.drawRect(230, 260, 320, 200);
        g.setColor(new Color(255, 255, 255, 200)); 
        g.fillRoundRect(20, 90, 160, 60, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("ORDER: " + currentOrder.getFoodName(), 30, 115);
        g.drawString("TIME: " + currentOrder.getTimeLeft() + "s", 30, 135);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("SCORE: " + scoreManager.getScore(), 720, 40);
        
        for (Patty p : grill.getPattiesOnGrill()) {
            BufferedImage currentImg = (p.getProgress() >= 100) ? cookedSteakImg : rawSteakImg;
            if (currentImg != null) {
                g.drawImage(currentImg, p.getX(), p.getY(), 75, 75, null);
                g.setColor(Color.WHITE);
                g.drawString(p.getProgress() + "%", p.getX() + 20, p.getY() - 5);
            }
        }

        //p1p2dont change
        if (!p1.getHeldItem().equals("Nothing") && blueChefWithSteakImg != null) {
            g.drawImage(blueChefWithSteakImg, p1.getX(), p1.getY(), 85, 85, null);
        } else if (blueChefImg != null) {
            g.drawImage(blueChefImg, p1.getX(), p1.getY(), 85, 85, null);
        }
        
        if (!p2.getHeldItem().equals("Nothing") && redChefWithSteakImg != null) {
            g.drawImage(redChefWithSteakImg, p2.getX(), p2.getY(), 85, 85, null);
        } else if (redChefImg != null) {
            g.drawImage(redChefImg, p2.getX(), p2.getY(), 85, 85, null);
        }

        if (!warningMessage.isEmpty()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(warningMessage, 350, 150);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("P1 Holding: " + p1.getHeldItem(), 20, 100);
        g.drawString("P2 Holding: " + p2.getHeldItem(), 700, 100);
    }
}
