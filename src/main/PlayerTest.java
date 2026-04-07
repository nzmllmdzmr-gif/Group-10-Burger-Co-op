package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test; 

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
    @Test
    public void testGrillBoundaries() {
        int grillX = 200;     
        int grillY = 250;
        int grillWidth = 488;  
        int grillHeight = 200;

      
        assertEquals(688, grillX + grillWidth, "大铁板右边界应为688");
        assertTrue(grillX < 400 && (grillX + grillWidth) > 400, "大铁板必须横跨左右两个校区");
    }
}
