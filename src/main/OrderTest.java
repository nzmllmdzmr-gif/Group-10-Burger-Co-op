package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    public void testGetFoodName() {
        Order order = new Order("Burger", 60);
        assertEquals("Burger", order.getFoodName());
    }

    @Test
    public void testGetTimeLeft() {
        Order order = new Order("Steak", 60);
        assertEquals(60, order.getTimeLeft());
    }

    @Test
    public void testReduceTime() {
        Order order = new Order("Burger", 60);
        order.reduceTime();
        assertEquals(59, order.getTimeLeft());
    }

    @Test
    public void testIsExpiredFalse() {
        Order order = new Order("Steak", 10);
        assertFalse(order.isExpired());
    }

    @Test
    public void testIsExpiredTrue() {
        Order order = new Order("Burger", 1);
        order.reduceTime(); // 变成0
        assertTrue(order.isExpired());
    }
}