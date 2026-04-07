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


    public int getX() { return x; }
    public int getY() { return y; }
    public String getId() { return id; }
    //控制移动范围
    public void move(int dx, int dy) {
        int nextX = this.x + (dx * speed);
        int nextY = this.y + (dy * speed);
        
        if (nextX >= 0 && nextX <= 800 - 30) {
            this.x = nextX;
        }
        if (nextY >= 0 && nextY <= 600 - 60) { // 减60是为了避开标题栏
            this.y = nextY;
        }
    }
}
