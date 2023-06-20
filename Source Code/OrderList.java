package JOJOLAND;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private String name;
    private String age;
    private String gender;
    private int arrivalTime;
    private int dayNum;
    private int totalDays=0;
    private String food;
    private String restaurant;
    private double price;
    private int indexRest;
    private int indexOrder;
    
    public OrderList(String name, String age, String gender, int arrivalTime, int dayNum, String food, String restaurant, int indexRest, int indexOrder, double price){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.arrivalTime = arrivalTime;
        this.dayNum = dayNum;
        this.food = food;
        this.restaurant = restaurant;
        this.indexRest = indexRest;
        this.indexOrder = indexOrder;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    
    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
    
    public int getArrivalTime(){
        return arrivalTime;
    }
    
    public int getTotalDays(){
        return totalDays;
    }
    
    public String getFood() {
        return food;
    }
    
    public String getRestaurant() {
        return restaurant;
    }
    
    public int getIndexRest(){
        return indexRest;
    }
    
    public int getIndexOrder(){
        return indexOrder;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    public double getPrice(){
        return price;
    }
    
    public int getDayNum(){
        return dayNum;
    }
    
    public static List<OrderList> getOrdersByRestaurant(List<OrderList> orderList, String restaurant) {
        List<OrderList> orders = new ArrayList<>();

        for (OrderList order : orderList) {
            if (order.getRestaurant().equalsIgnoreCase(restaurant)) {
                orders.add(order);
            }
        }
        return orders;
    }
}