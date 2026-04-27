package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BurgerTest {

    @Test
    public void testRawBurgerPattyType() {
        Patty burger = new Patty("RawBurger", 100, 180, 0);
        assertEquals("RawBurger", burger.getType());
    }

    @Test
    public void testRawBurgerCanCook() {
        Patty burger = new Patty("RawBurger", 100, 180, 0);

        for (int i = 0; i < 20; i++) {
            burger.cook();
        }

        assertTrue(burger.getProgress() > 0);
    }

    @Test
    public void testRawBurgerCookingCap() {
        Patty burger = new Patty("RawBurger", 100, 180, 0);

        for (int i = 0; i < 500; i++) {
            burger.cook();
        }

        assertEquals(100, burger.getProgress());
    }

    @Test
    public void testPlayerCanHoldRawBurger() {
        Player p = new Player("test", 100, 100);
        p.setHeldItem("RawBurger");

        assertEquals("RawBurger", p.getHeldItem());
    }

    @Test
    public void testPlayerCanHoldCookedBurger() {
        Player p = new Player("test", 100, 100);
        p.setHeldItem("CookedBurger");

        assertEquals("CookedBurger", p.getHeldItem());
    }
}