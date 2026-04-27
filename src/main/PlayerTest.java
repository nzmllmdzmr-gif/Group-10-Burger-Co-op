package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test; 

public class PlayerTest {

    @org.junit.jupiter.api.Test
    public void test1() {
        //Player初始位置 the original set of player
        System.out.println("check for 1");
        Player p = new Player("test1", 100, 100);
        
        assertEquals(100, p.getX());
        assertEquals(100, p.getY());
        System.out.println("set is ok");
    }

    @org.junit.jupiter.api.Test
    public void testMove() {
        // 移动逻辑move logic
        System.out.println("test for move");
        Player p = new Player("test2", 200, 300);
        
        // 右right
        p.move(1, 0); 
        
        // 下down
        p.move(0, 1);

        assertEquals(205, p.getX());
        assertEquals(305, p.getY());
    }

    @org.junit.jupiter.api.Test
    public void testId() {
        // 测试test
        Player p = new Player("Chef007", 50, 50);
        assertEquals("Chef007", p.getId());
    }
    @Test
    public void testGrillBoundaries() {
        int grillX = 200;     
        int grillY = 250;
        int grillWidth = 488;  
        int grillHeight = 200;

      
        assertEquals(688, grillX + grillWidth, "plate boundary");
        assertTrue(grillX < 400 && (grillX + grillWidth) > 400, "boundary should across two areas");
    }
}
