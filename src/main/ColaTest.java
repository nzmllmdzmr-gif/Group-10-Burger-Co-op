package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ColaTest {

    @Test
    public void testPlayerCanPickCola() {
        Player p = new Player("test", 100, 100);

        p.setHeldItem("Cola");

        assertEquals("Cola", p.getHeldItem());
    }

    @Test
    public void testPlayerCanDropCola() {
        Player p = new Player("test", 100, 100);

        p.setHeldItem("Cola");
        p.setHeldItem("Nothing");

        assertEquals("Nothing", p.getHeldItem());
    }
}