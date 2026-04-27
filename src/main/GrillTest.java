package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrillTest {
    private Grill grill;

    @BeforeEach
    public void setUp() {
        // new grill
        grill = new Grill();
    }

    @Test
    public void testPlacePattySuccessfully() {
        // put patty on slot 0
        boolean result = grill.placePatty(0, "Beef");
        assertTrue(result, "successfully");
        assertEquals(1, grill.getPattiesOnGrill().size(), "there is one patty on the grill");
    }

    @Test
    public void testDoublePlaceOnSameSlot() {
        // put two patties at one slot
        grill.placePatty(1, "Beef");
        boolean secondResult = grill.placePatty(1, "Beef");
        
        assertFalse(secondResult, "Can't put 2 patties on same slot");
        assertEquals(1, grill.getPattiesOnGrill().size());
    }

    @Test
    public void testGrillUpdateProgress() {

        grill.placePatty(0, "Beef");
        int initialProgress = grill.getPattiesOnGrill().get(0).getProgress();
        
        for (int i = 0; i < 3; i++) {
            grill.update(); 
        }
        
        int afterProgress = grill.getPattiesOnGrill().get(0).getProgress();
        assertTrue(afterProgress > initialProgress);
    }
}