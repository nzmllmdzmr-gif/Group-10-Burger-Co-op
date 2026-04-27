package main;

public class MoneyManager {
    private int money = 0;

    //成功送出一份食物加钱,add money if the order finish in time
    public void addMoney() {
        this.money += 10;
    }

    //如果订单超时了，扣钱,reduce money if over time,and make sure the money wont less than 0 
    public void deductMoney() {
        this.money -= 5;
        if (this.money < 0) this.money = 0;
    }

    public int getMoney() {
        return money;
    }
}