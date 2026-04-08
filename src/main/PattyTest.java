package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PattyTest {

    @Test
   public void testPattyInitialization() {
        // create a patty
        Patty patty = new Patty("Beef", 250, 280, 0);

        // confirm initial value
        assertEquals(250, patty.getX(), "X is 250");
        assertEquals(280, patty.getY(), "Y is 280");
        assertEquals(0, patty.getSlot(), "slot is 0");
        assertEquals(0, patty.getProgress(), "progress from 0");
    }

    @Test
  public  void testCookingProcess() {
        Patty patty = new Patty("Beef", 250, 280, 0);

        
        for (int i = 0; i < 5; i++) {
            patty.cook();
        }

        assertEquals(5, patty.getProgress(), "progress is 5 when use 5 times");
    }

    @Test
  public  void testCookingCap() {
        Patty patty = new Patty("Beef", 250, 280, 0);
        
        
        for (int i = 0; i < 150; i++) {
            patty.cook();
        }

       
        assertEquals(100, patty.getProgress(), "progress sholdent be 100");
    }
}