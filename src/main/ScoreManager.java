package main;

public class ScoreManager {
    private int score = 0;

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }
}