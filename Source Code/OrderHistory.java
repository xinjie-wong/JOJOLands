package JOJOLAND;

import java.util.ArrayList;
import static JOJOLAND.StartInterface.currentLocation;

public class OrderHistory {
    private String name;
    private int dayNum;
    String residentFilePath = "resources/residents.csv";
    LoadFile loadSystemFile = new LoadFile();
    ArrayList<Resident> resident = loadSystemFile.loadresidentFromFile(residentFilePath);
    
    public OrderHistory(String name, int dayNum){
        this.name = name;
        this.dayNum = dayNum;
    }
    
    public void printOrderHistory(ArrayList<ArrayList<OrderList>> residentOrderLists) {
        System.out.println("");
        System.out.println("Order History");
        System.out.println("+------+------------------------------------------+----------------------+");
        System.out.println("| Day  | Food                                     | Restaurant           |");
        System.out.println("+------+------------------------------------------+----------------------+");
        int residentIndex = -1;
        for (int i = 0; i < resident.size(); i++) {
            if (resident.get(i).getName().equals(name)) {
                residentIndex = i;
                break;
            }
        }
        ArrayList<OrderList> orderList = residentOrderLists.get(residentIndex);
        for (int i = 0; i < orderList.size(); i++) {
            System.out.printf("| %-2d   | %-40s | %-20s |%n", i+1, orderList.get(i).getFood(), orderList.get(i).getRestaurant());
        }
        System.out.println("+------+------------------------------------------+----------------------+");
        System.out.println("=".repeat(200));
    }
}
