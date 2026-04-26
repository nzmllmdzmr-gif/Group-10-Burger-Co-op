package main;

public class Player {
	private int x, y;
    private final int speed = 5;
    private final String id; // "Player1"or"Player2"

    public Player(String id, int startX, int startY) {
        this.id = id;
        this.x = startX;
        this.y = startY;
    }


    public int getX() { return x; }
    public int getY() { return y; }
    public String getId() { return id; }
    //control move size
    public void move(int dx, int dy) {
        int nextX = this.x + (dx * speed);
        int nextY = this.y + (dy * speed);
        
        if (nextX >= 0 && nextX <= 800 - 110) {
            this.x = nextX;
        }
        if (nextY >= 260 && nextY <= 600 - 120) {
            this.y = nextY;
        }
    }
    // record what handled by player，for example "Nothing"
    private String heldItem = "Nothing"; 

    // set the thing on hand
    public void setHeldItem(String item) {
        this.heldItem = item;
    }

    // check what in hand
    public String getHeldItem() {
        return this.heldItem;
    }
}