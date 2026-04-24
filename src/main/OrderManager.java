package main;

import java.util.List;    
import java.util.ArrayList;

public class OrderManager {
private List<Order> activeOrders = new ArrayList<>();
    
    public void generateOrder() {
    	String[] menu = {"Burger","Steak"};
        String randomFood = menu[(int)(Math.random() * menu.length)];
        activeOrders.add(new Order(randomFood, 60));
    }

    public void update() {
        for (Order o : activeOrders) {
            o.reduceTime();
        }
    }
    public List<Order> getOrders() {
        return activeOrders;
    }

}
