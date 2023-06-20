package JOJOLAND;

import java.util.ArrayList;
import java.util.Scanner;

//this class will be called in StartInterface after user views resident info (Basic feature 2) 
public class TheJoestars {
    private String currentLocation;
    private int dayNum;
    private int currDay;
    String residentFilePath = "resources/residents.csv";
    String standFilePath = "resources/stands.csv";
    LoadFile loadSystemFile = new LoadFile();
    ArrayList<Resident> resident = loadSystemFile.loadresidentFromFile(residentFilePath);
    ArrayList<ArrayList<OrderList>> residentOrderLists;
    Scanner sc = new Scanner(System.in);
    
    public TheJoestars(String currentLocation, int dayNum, int currDay, ArrayList<ArrayList<OrderList>> residentOrderLists){ 
        this.currentLocation = currentLocation;
        this.dayNum = dayNum;
        this.currDay = currDay;
        this.residentOrderLists = residentOrderLists;
        
        //after selecting [1] View Resident's Profile, enter name to view data
        boolean containName = false; //check if entered name is correct or not
        System.out.print("Enter the resident's name: ");
        String name = sc.nextLine();
        while(containName == false){
            System.out.println("=".repeat(200));

            //view profile
            Profile profile = new Profile(name, currentLocation);
            containName = profile.residentProfile(residentFilePath, standFilePath);
            if(containName == false){
                System.out.println("Invalid name");
                System.out.print("Enter the resident's name: ");
                name = sc.nextLine();
            }
        }
        
        //prints order history for selected resident
        OrderHistory order = new OrderHistory(name,dayNum);
        order.printOrderHistory(residentOrderLists);
    }
}