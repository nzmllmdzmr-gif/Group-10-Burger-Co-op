package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class OrderTest {

    @Test
    public void testGetFoodName() {
        Order order = new Order(Arrays.asList("Burger"), 60);
        assertEquals(Arrays.asList("Burger"), order.getFoodName());
    }

    @Test
    public void testGetTimeLeft() {
        Order order = new Order(Arrays.asList("Steak"), 60);
        assertEquals(60, order.getTimeLeft());
    }

    @Test
    public void testReduceTime() {
        Order order = new Order(Arrays.asList("Burger"), 60);
        order.reduceTime();
        assertEquals(59, order.getTimeLeft());
    }

    @Test
    public void testIsExpiredFalse() {
        Order order = new Order(Arrays.asList("Steak"), 10);
        assertFalse(order.isExpired());
    }

    @Test
    public void testIsExpiredTrue() {
        Order order = new Order(Arrays.asList("Burger"), 1);
        order.reduceTime();
        assertTrue(order.isExpired());
    }
}