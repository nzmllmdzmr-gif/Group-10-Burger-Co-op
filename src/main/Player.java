package main;

public class Player {
	private int x, y;
    private final int speed = 5;
    private final String id; // "Player1" 或 "Player2"

    public Player(String id, int startX, int startY) {
        this.id = id;
        this.x = startX;
        this.y = startY;
    }

    public void move(int dx, int dy) {
        this.x += dx * speed;
        this.y += dy * speed;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getId() { return id; }
}