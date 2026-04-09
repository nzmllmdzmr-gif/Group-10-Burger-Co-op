package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerInteractionTest {

    @Test
    public void testPlayerCanHoldSomething() {
        // create a player
        Player p = new Player("ChefB", 100, 100);
        
        // tell player that the beef is in your hand
        p.setHeldItem("Beef"); 
        
        //check if beef in player's hand
        assertEquals("Beef", p.getHeldItem());
    }
}