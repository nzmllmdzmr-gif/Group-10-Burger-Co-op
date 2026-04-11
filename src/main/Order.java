package main;

public class Order {
    private String foodName;
    private int timeLeft;

    public Order(String foodName, int timeLeft) {
        this.foodName = foodName;
        this.timeLeft = timeLeft;
    }

    //let time reduce
    public void reduceTime() {
        if (timeLeft > 0) {
            timeLeft--;
        }
    }

    //在左上角显示 "ORDER: Beef",show the order name on thr top of left screen
    public String getFoodName() {
        return foodName;
    }

    //在左上角显示 "Time: 20s",with the timer limit
    public int getTimeLeft() {
        return timeLeft;
    }

    //show id the order over limited time or not
    public boolean isExpired() {
        return timeLeft <= 0;
    }
}
