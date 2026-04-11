package main;

public class ScoreManager {
    private int score = 0;

    //成功送出一份牛排加10分,add 10 point if the steak finish in time
    public void addSteakScore() {
        this.score += 10;
    }

    //如果订单超时了，扣5分,reduce 5 if over time,and make sure the score wont less than 0 
    public void deductTimeoutScore() {
        this.score -= 5;
        if (this.score < 0) this.score = 0;
    }

    public int getScore() {
        return score;
    }
}