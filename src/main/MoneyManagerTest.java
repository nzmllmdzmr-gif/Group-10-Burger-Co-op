package main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MoneyManagerTest {
    @Test
    public void testInitialMoney() {
        MoneyManager mm = new MoneyManager();
        //check if the oringinal money is 0
        assertEquals(0, mm.getMoney(), "money should be 0");
    }
    @Test
    public void testAddMoney() {
        MoneyManager mm = new MoneyManager();
        mm.addMoney(); 
        //check if 10
        assertEquals(10, mm.getMoney(), "should be 10 after add 10");
    }
    @Test
    public void testAddMultipleTimes() {
        MoneyManager mm = new MoneyManager();
        mm.addMoney();
        mm.addMoney();
        //check if final money is 20
        assertEquals(20, mm.getMoney(), "should be 20 after twice add");
    }

    @Test
    public void testDeductMoneyCannotBelowZero() {
        MoneyManager mm = new MoneyManager();
        mm.deductMoney();
        assertEquals(0, mm.getMoney(), "money should not be less than 0");
    }
}