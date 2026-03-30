package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //create 窗口
        JFrame myFrame = new JFrame("Burger Game M1");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //加面板
        GamePanel panel = new GamePanel();
        myFrame.add(panel);
        
        //窗口size
        myFrame.setSize(888, 666);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        
        System.out.println("Game Loaded Successfully..."); 
    }
}
