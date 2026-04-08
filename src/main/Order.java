package main;

public class Order {
    private String foodName;
    private int timeLeft;

    public Order(String foodName, int timeLeft) {
        this.foodName = foodName;
        this.timeLeft = timeLeft;
    }

    public void reduceTime() {
        if (timeLeft > 0) {
            timeLeft--;
        }
    }
}