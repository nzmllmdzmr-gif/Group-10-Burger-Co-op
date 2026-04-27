package main;

import java.util.List;    
import java.util.ArrayList;

public class OrderManager {
private List<Order> activeOrders = new ArrayList<>();
    
    public void generateOrder() {
    	String[] menu = {"Burger","Steak","Cola"};
    	 List<String> foods = new ArrayList<String>();
    	 int count = 2 + (int)(Math.random() * 2);
    	 for (int i = 0; i < count; i++) {
    	        String randomFood = menu[(int)(Math.random() * menu.length)];
    	        foods.add(randomFood);
    	    }

    	    activeOrders.add(new Order(foods, 60));
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
