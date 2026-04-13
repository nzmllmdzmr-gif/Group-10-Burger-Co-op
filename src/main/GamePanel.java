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
                if (key == KeyEvent.VK_SHIFT) handleInteraction(p2);
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
        //only if player handle the well donw steak and near the deliver area
        if (p.getHeldItem().equals("CookedSteak") && p.getX() > 750) {
            scoreManager.addSteakScore();
            currentOrder = new Order("Steak", 20);
            p.setHeldItem("Nothing");
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
            p.setHeldItem("Nothing");
        }
    }

    //画图的地方！！！for drawing the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //the below is not the food area
        g.setColor(GameSettings.COLOR_COOKING);
        g.fillRect(0, 0, 400, 600);
        g.setColor(GameSettings.COLOR_ASSEMBLY);
        g.fillRect(400, 0, 488, 600);
        g.setColor(Color.BLACK);
        g.drawLine(400, 0, 400, 600);
        g.setColor(GameSettings.COLOR_GRILL);
        g.fillRect(200, 250, 488, 200);
        g.setColor(Color.BLACK);
        g.drawRect(200, 250, 488, 200);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 888, 80);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 888, 80);        

        //draw area for deliver the food画一个出餐口
        g.setColor(Color.GREEN);
        g.fillRect(800, 200, 88, 150); 
        g.setColor(Color.WHITE);
        g.drawString("GOAL", 820, 280);

        //draw a area for getting task画个任务窗口
        g.setColor(new Color(255, 255, 255, 200)); 
        g.fillRoundRect(20, 90, 160, 60, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("ORDER: " + currentOrder.getFoodName(), 30, 115);
        g.drawString("TIME: " + currentOrder.getTimeLeft() + "s", 30, 135);

        //draw the score area画右上角的计数点
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
