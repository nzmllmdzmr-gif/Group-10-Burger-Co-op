package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// 画布
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
    // 两个玩家
    Player p1 = new Player("Player1", 150, 300);
    Player p2 = new Player("Player2", 600, 300);

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

        // 游戏循环
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
                
                repaint(); 
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 背景分区
        g.setColor(GameSettings.COLOR_COOKING);
        g.fillRect(0, 0, 400, 600);
        
        g.setColor(GameSettings.COLOR_ASSEMBLY);
        g.fillRect(400, 0, 400, 600);
        
        g.setColor(Color.BLACK);
        g.drawLine(400, 0, 400, 600);

        // 画玩家，大小也用A定义的那个
        g.setColor(Color.BLUE);
        g.fillRect(p1.getX(), p1.getY(), GameSettings.PLAYER_SIZE, GameSettings.PLAYER_SIZE);
        
        g.setColor(Color.RED);
        g.fillRect(p2.getX(), p2.getY(), GameSettings.PLAYER_SIZE, GameSettings.PLAYER_SIZE);
    }
}
