package main;

public class Patty {
    private String type;
    private int x, y;
    private int slot;
    private int cookingProgress = 0; // 0:生, 100:熟

    public Patty(String type, int x, int y, int slot) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.slot = slot;
    }
 // 在 Patty 类里增加这两个方法，GamePanel 才能读到坐标
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void cook() {
        if (cookingProgress < 100) cookingProgress++;
    }

    public int getSlot() { return slot; }
}