package main;

import java.util.ArrayList;
import java.util.List;

public class Grill {
    // Creat 6 slots
	private int[][] slots = {
			{70, 180}, {190, 180}, {310, 180}, 
		    {430, 180}, {550, 180}, {670, 180}
    };
    // store
    private List<Patty> pattiesOnGrill = new ArrayList<>(); 

    public Grill() {
    }

    public boolean placePatty(int slotIndex, String pType) {
        if (slotIndex < 0 || slotIndex >= slots.length) return false;
        // check the slot
        for (Patty p : pattiesOnGrill) {
            if (p.getSlot() == slotIndex) return false;
        }
       
        pattiesOnGrill.add(new Patty(pType, slots[slotIndex][0], slots[slotIndex][1], slotIndex));
        return true;
    }
    public List<Patty> getPattiesOnGrill() {
        return pattiesOnGrill;
    }

    // update cooking progress
    public void update() {
        for (Patty p : pattiesOnGrill) {
            p.cook(); 
        }
    }
}