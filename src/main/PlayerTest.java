package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test; 

// Author: Zihan Zhang
public class PlayerTest {

    @org.junit.jupiter.api.Test
    public void test1() {
        // 测Player初始位置
        System.out.println("正在开始测试1...");
        Player p = new Player("test1", 100, 100);
        
        //
        assertEquals(100, p.getX());
        assertEquals(100, p.getY());
        System.out.println("测试1过掉了，坐标没问题。");
    }

    @org.junit.jupiter.api.Test
    public void testMove() {
        // 移动逻辑
        System.out.println("正在测试移动...");
        Player p = new Player("test2", 200, 200);
        
        // 右
        p.move(1, 0); 
        
        // 下
        p.move(0, 1);
        assertEquals(205, p.getY());
    }

    @org.junit.jupiter.api.Test
    public void testId() {
        // 测试
        Player p = new Player("Chef007", 50, 50);
        assertEquals("Chef007", p.getId());
    }
}
