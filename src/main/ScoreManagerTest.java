package main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ScoreManagerTest {
    @Test
    public void testInitialScore() {
        ScoreManager sm = new ScoreManager();
        //check if the oringinal score is 0
        assertEquals(0, sm.getScore(), "score should be 0");
    }
    @Test
    public void testAddScore() {
        ScoreManager sm = new ScoreManager();
        sm.addSteakScore(); 
        //check if 10
        assertEquals(10, sm.getScore(), "should be 10 after add 10");
    }
    @Test
    public void testAddMultipleTimes() {
        ScoreManager sm = new ScoreManager();
        sm.addSteakScore();
        sm.addSteakScore();
        //check if final score is 20
        assertEquals(20, sm.getScore(), "should be 20 after twice add");
    }
} 