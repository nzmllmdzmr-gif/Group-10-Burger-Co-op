package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Iterator; 
import java.util.List; 
// panel
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	//把计数板实体化一下make zihan zhangs' entity,make the scoreboard entity
	private MoneyManager scoreManager = new MoneyManager(); 
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

    //图片聚集地  images
    private BufferedImage blueChefImg = ImageLoader.loadImage("/bluechef.png");
    private BufferedImage redChefImg = ImageLoader.loadImage("/redchef.png");
    private BufferedImage rawSteakImg = ImageLoader.loadImage("/rawsteak.png");
    private BufferedImage cookedSteakImg = ImageLoader.loadImage("/cookedsteak.png");
    private BufferedImage blueChefWithSteakImg = ImageLoader.loadImage("/bluechefwithsteak.png");
    private BufferedImage redChefWithSteakImg = ImageLoader.loadImage("/redchefwithsteak.png");
    private BufferedImage colaMachineImg = ImageLoader.loadImage("/colamaterials.png");
    private BufferedImage blueChefWithColaImg = ImageLoader.loadImage("/bluechefwithcola.png");
    private BufferedImage redChefWithColaImg = ImageLoader.loadImage("/redchefwithcola.png");
    private BufferedImage blueChefWithRawSteakImg = ImageLoader.loadImage("/bluechefwithrawsteak.png");
    private BufferedImage redChefWithRawSteakImg = ImageLoader.loadImage("/redchefwithrawsteak.png");
    private BufferedImage materialsImg = ImageLoader.loadImage("/materials.png");
    private BufferedImage deliverWindowImg = ImageLoader.loadImage("/deliver.png");
    private BufferedImage moneyBoardImg = ImageLoader.loadImage("/moneyboard.png");
  
    boolean w, s, a, d;
    boolean up, down, left, right;
    
    private OrderManager orderManager = new OrderManager();

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

                // P1 enterkey
                if (key == KeyEvent.VK_ENTER) handleInteraction(p1);
                // P2 spacekey
                if (key == KeyEvent.VK_SPACE) handleInteraction(p2);
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

                    for (int i = 0; i < orderManager.getOrders().size(); i++) {
                        Order o = orderManager.getOrders().get(i);

                        o.reduceTime();

                        if (o.isExpired()) {
                        	scoreManager.deductMoney();
                            orderManager.getOrders().remove(i);
                            orderManager.generateOrder();
                            i--; // 防止跳过元素
                        }
                    }

                    t = 0;
                }
                
                if (messageTimer > 0) messageTimer--;
                else warningMessage = "";

                repaint(); 
            }
        });
        
        //play the mainbgm
        bgm.playMusic();
        
        timer.start();
        for (int i = 0; i < 3; i++) {
            orderManager.generateOrder();
        }
    }

    private void handleInteraction(Player p) {
        if (p.getHeldItem().equals("Nothing")
                && p.getX() >= 520 && p.getX() <= 850
                && p.getY() >= 330 && p.getY() <= 560) {

            p.setHeldItem("Cola");
            warningMessage = "Got cola!";
            messageTimer = 50;
            return;
        }

        if (p.getHeldItem().equals("Cola")
                && p.getX() >= 650 && p.getX() <= 888
                && p.getY() >= 100 && p.getY() <= 330) {

            p.setHeldItem("Nothing");
            warningMessage = "Cola delivered!";
            messageTimer = 50;
            return;
        }

        if (p.getHeldItem().equals("Nothing")
                && p.getX() >= 0 && p.getX() <= 330
                && p.getY() >= 400 && p.getY() <= 610) {

            p.setHeldItem("RawSteak");
            warningMessage = "Got raw steak!";
            messageTimer = 50;
            return;
        }
        

        //only if player handle the well down steak and near the deliver area
    	if (p.getHeldItem().equals("CookedSteak")
    	        && p.getX() >= 650 && p.getX() <= 888
                && p.getY() >= 100 && p.getY() <= 330) {

    	    String deliveredItem;

    	    if (p.getHeldItem().equals("CookedSteak")) {
    	        deliveredItem = "Steak";
    	    } else {
    	        deliveredItem = "Burger";
    	    }

    	    for (int i = 0; i < orderManager.getOrders().size(); i++) {
    	        Order order = orderManager.getOrders().get(i);

    	        if (order.getFoodName().equals(deliveredItem)) {
    	        	scoreManager.addMoney();
    	            orderManager.getOrders().remove(i);
    	            orderManager.generateOrder();

    	            p.setHeldItem("Nothing");
    	            warningMessage = "Order delivered!";
    	            messageTimer = 50;
    	            return;
    	        }
    	    }

            p.setHeldItem("Nothing");
    	    warningMessage = "Steak delivered!";
    	    messageTimer = 50;
    	    return;
    	}

    	if (p.getHeldItem().equals("RawSteak")
    	        && p.getX() > 40 && p.getX() < 750
    	        && p.getY() > 160 && p.getY() < 275) {
        	
        	String food = p.getHeldItem();
            for (int i = 0; i < 6; i++) {
                if (grill.placePatty(i, p.getHeldItem())) {
                	 p.setHeldItem("Nothing");
                	    warningMessage = "Cooking " + food + "...";
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
                
                if (dist < 130) { 
                	if (patty.getProgress() >= 100) {

                	    if (patty.getType().equals("RawSteak")) {
                	        p.setHeldItem("CookedSteak");
                	    }

                	    warningMessage = "Got it!";
                	    it.remove();
                	}else {
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

        if (moneyBoardImg != null) {
            g.drawImage(moneyBoardImg, 319, 10, 250, 90, null);
        }

        if (materialsImg != null) {
            g.drawImage(materialsImg, 40, 460, 470, 115, null);
        }

        int bevX = 630; 
        int bevY = 390; 
        int bevWidth = 210; 
        int bevHeight = 210;

        int deliverX = 720;
        int deliverY = 115;
        int deliverWidth = 150;
        int deliverHeight = 150;
        
        g.drawString("P1 Holding: " + p1.getHeldItem(), 25, 180);

        if (colaMachineImg != null) {
            g.drawImage(colaMachineImg, bevX, bevY, bevWidth, bevHeight, null);
        } else {
            g.setColor(new Color(173, 216, 230)); 
            g.fillRect(bevX, bevY, bevWidth, bevHeight); 
            g.setColor(Color.BLACK);
            g.drawRect(bevX, bevY, bevWidth, bevHeight);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("COLA", bevX + 40, bevY + 55);
        }

        if (deliverWindowImg != null) {
            g.drawImage(deliverWindowImg, deliverX, deliverY, deliverWidth, deliverHeight, null);
        } else {
            g.setColor(new Color(240, 220, 160)); 
            g.fillRect(deliverX, deliverY, deliverWidth, deliverHeight); 
            g.setColor(Color.BLACK);
            g.drawRect(deliverX, deliverY, deliverWidth, deliverHeight);
            g.drawString("DELIVER", deliverX + 25, deliverY + 60);
        }
    
        g.setColor(GameSettings.COLOR_GRILL); 
        g.fillRect(40, 160, 610, 105); 
        g.setColor(Color.BLACK);
        g.drawRect(40, 160, 610, 105);
        g.setColor(new Color(255, 255, 255, 200)); 
        g.fillRoundRect(20, 90, 160, 60, 10, 10);
        g.setColor(Color.BLACK);
        List<Order> orders = orderManager.getOrders();

        int startX = 30;
        int startY = 100;

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);

            g.drawString("ORDER: " + o.getFoodName(), startX, startY + i * 40);
            g.drawString("TIME: " + o.getTimeLeft() + "s", startX, startY + i * 40 + 15);
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("MONEY: " + scoreManager.getMoney(), 720, 40);
        
        for (Patty p : grill.getPattiesOnGrill()) {
            BufferedImage currentImg = (p.getProgress() >= 100) ? cookedSteakImg : rawSteakImg;
            if (currentImg != null) {
                g.drawImage(currentImg, p.getX(), p.getY(), 75, 75, null);
                g.setColor(Color.WHITE);
                g.drawString(p.getProgress() + "%", p.getX() + 20, p.getY() - 5);
            }
        }

        //p1p2dont change
        if (p1.getHeldItem().equals("Cola") && blueChefWithColaImg != null) {
            g.drawImage(blueChefWithColaImg, p1.getX(), p1.getY(), 110, 110, null);
        } else if (p1.getHeldItem().equals("RawSteak") && blueChefWithRawSteakImg != null) {
            g.drawImage(blueChefWithRawSteakImg, p1.getX(), p1.getY(), 110, 110, null);
        } else if (p1.getHeldItem().equals("CookedSteak") && blueChefWithSteakImg != null) {
            g.drawImage(blueChefWithSteakImg, p1.getX(), p1.getY(), 110, 110, null);
        } else if (blueChefImg != null) {
            g.drawImage(blueChefImg, p1.getX(), p1.getY(), 110, 110, null);
        }
        
        if (p2.getHeldItem().equals("Cola") && redChefWithColaImg != null) {
            g.drawImage(redChefWithColaImg, p2.getX(), p2.getY(), 110, 110, null);
        } else if (p2.getHeldItem().equals("RawSteak") && redChefWithRawSteakImg != null) {
            g.drawImage(redChefWithRawSteakImg, p2.getX(), p2.getY(), 110, 110, null);
        } else if (p2.getHeldItem().equals("CookedSteak") && redChefWithSteakImg != null) {
            g.drawImage(redChefWithSteakImg, p2.getX(), p2.getY(), 110, 110, null);
        } else if (redChefImg != null) {
            g.drawImage(redChefImg, p2.getX(), p2.getY(), 110, 110, null);
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