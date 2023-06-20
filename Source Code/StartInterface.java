package JOJOLAND;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class StartInterface {
    
    static Scanner sc = new Scanner(System.in);
    static Graph map = new Graph();
    static String mapLocation;
    static int dayNum = 1;   //initialize day 1
    static String currentLocation = "Town Hall";    //initialize current location 
    static Stack<String> history = new Stack<>();
    static Stack<String> forward = new Stack<>();
    static ArrayList<String> list = new ArrayList<>();
    static RandomOrder ro = new RandomOrder();
    static ArrayList<ArrayList<OrderList>> residentOrderLists = new ArrayList<>();
    static int currDay = 0; //for randomorder
    
    //method to start the game
    public static void startGame() throws ParseException, IOException{
        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit\n");
                
        switch(getInput()){
            case 1:
                chooseMap();
                displayDay();
                getMission();
                break;
                
            case 2:
                System.out.print("Enter the path of your save file: ");
                loadGame();
                break;
            
            case 3:
                endGame();
                break;
                
            default:
                System.out.println("Invalid input. Please choose again.");
                startGame();
                break;
        }
    }
    
    //method to get an input for selection
    public static int getInput(){
        System.out.print("Select [integer only]: ");
        int input = sc.nextInt();
        sc.nextLine();
        System.out.println("=".repeat(200));
        return input;
    }
    
    //method to choose map
    public static void chooseMap(){
        System.out.println("Select a map: ");
        System.out.println("[1] Default Map");
        System.out.println("[2] Parallel Map");
        System.out.println("[3] Alternate Map\n");
        int num = getInput();
        switch (num){
            case 1:
                mapLocation = "resources/DefaultMap.txt";
                break;
            case 2:
                mapLocation = "resources/ParallelMap.txt";
                break;
            case 3:
                mapLocation = "resources/AlternateMap.txt";
                break;
            default:
                System.out.println("Invalid input. Please choose again");
                chooseMap();
        }
        createMap(mapLocation);
    }
    
    //method to create map
    public static void createMap(String loc){
        try{
            Scanner scan = new Scanner(new FileInputStream(loc));
            while(scan.hasNextLine()){
                String s = scan.nextLine();
                String[] edge = s.split(",");
                map.addEdge(edge[0], edge[1], Integer.valueOf(edge[2]));
            }
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
    
    //method to choose the day
    public static String getDay(int dayNum){
        String[] day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int num;
        if(dayNum>7){
            num = (dayNum%7)-1;
        }else{
            num = dayNum-1;
        }
        return day[num];
    }
    
    //method to display the day
    public static void displayDay(){
        System.out.printf("It's Day %d (%s) of our journey in JOJOLands!\n", dayNum, getDay(dayNum));
    }
    
    //method to choose mission for each location
    public static void getMission(){
        while(true){
            System.out.println("Current Location: " + currentLocation);
            switch(currentLocation){
                case "Town Hall":
                    MissionOne();
                    break;
                case "Morioh Grand Hotel":
                    MissionTwo();
                    break;
                case "Trattoria Trussardi", "Jade Garden", "Cafe Deux Magots", "Libeccio", "Savage Garden":
                    MissionThree();
                    break;
                case "Polnareff Land":
                    MissionFour();
                    break;
                case "Joestar Mansion":
                    MissionFive();
                    break;
                case "Angelo Rock":
                    MissionSix();
                    break;
                case "San Giorgio Maggiore":
                    MissionSeven();
                    break;
                case "Green Dolphin Street Prison":
                    MissionEight();
                    break;
                default:
                    DefaultMission();
                    break;
            }
        }
    }
    
    //Town Hall mission
    public static void MissionOne(){
        int i = 2;
        System.out.println("[1] Move to New Location");
        choices();
        if(!history.empty()){
            System.out.printf("[%d] Back to (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek()); 
            i++;
        }
        System.out.printf("[%d] Advance to Next Day\n", i);
        i++;
        System.out.printf("[%d] Save Game\n", i);
        i++;
        System.out.printf("[%d] Exit\n\n", i);
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(getInput()){
            case 1:                            
                moveLocation();
                break;
            case 2:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    advance();
                break;
            case 3: 
                if(history.empty() && forward.empty())
                    saveGame();
                else if((!history.empty() && forward.empty()) || history.empty() && !forward.empty())
                    advance();
                else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 4:
                if(history.empty() && forward.empty())
                    endGame();
                else if((!history.empty() && forward.empty()) ||( history.empty() && !forward.empty()) )
                    saveGame();
                else if(!history.empty() && !forward.empty())
                    advance();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    saveGame();
                else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty()))
                    endGame();
                else if(history.empty() && forward.empty())
                    System.out.println("Invalid input. Please choose again\n");
                    MissionOne();
                break;
            case 6:
                if(!history.empty() && !forward.empty())
                    endGame();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionOne();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionOne();
                break;
        }
    }
    
    //Morioh Grand Hotel mission
    public static void MissionTwo(){
        int i = 5;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] The Hand");
        System.out.println("[4] Thus Spoke Rohan Kishibe");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                heavensDoor(dayNum, currDay, residentOrderLists);
                break;
            case 3:
                //jump to Super Fly (basic feature 7);
                TheHand();
                break;
            case 4: 
                //jump to extra feature 8
                ThusSpokeRohanKishibe();
                break;
            case 5:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 6:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionTwo();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 7:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionTwo();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionTwo();
                break;
        }
    }
    
    //Trattoria Trussardi, Jade Garden, Cafe Deux Magots, Libeccio, Savage Garden mission
    public static void MissionThree(){
        int i = 6;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Waiting List and Order Processing List");
        System.out.println("[3] View Menu");
        System.out.println("[4] View Sales Information");
        System.out.println("[5] Milagro Man");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        int indexRest=0;
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(currentLocation){
            case "Jade Garden":
                indexRest = 0;
                break;
            case "Cafe Deux Magots":
                indexRest = 1;
                break;
            case "Trattoria Trussardi":
                indexRest = 2;
                break;
            case "Libeccio":
                indexRest = 3;
                break;
            case "Savage Garden":
                indexRest = 4;
                break;
            default:
                break;
        }
        Menu menu = new Menu(indexRest);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Pearl Jam (basic feature 3)              
                PearlJam(dayNum, residentOrderLists);
                break;  
            case 3: 
                //jump to Pearl Jam (basic feature 3)
                menu.displayMenu();
                break;
            case 4:
                //jump to Moody Blue (basic feature 5)
                if(currDay!=dayNum){
                    residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
                    currDay = dayNum;
                }
                MoodyBlues moodyBlues = new MoodyBlues(dayNum, currentLocation, residentOrderLists);
                boolean milagro = false; //not in Milagro Man mode
                List<List<Sale>> salesRecord = new ArrayList<>(); //empty list
                moodyBlues.salesInfo(milagro, salesRecord);
                break;
            case 5:
                //jump to Milagro Man (basic feature 6)
                if(currDay!=dayNum){
                    residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
                    currDay = dayNum;
                }
                MilagroMan milagroMan = new MilagroMan(dayNum, currentLocation, residentOrderLists);
                milagroMan.milagroManMode();
                break;
            case 6:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 7:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 8:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionThree();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionThree();
                break;
        }
    }
    
    //Polnareff Land mission
    public static void MissionFour(){
        int i = 3;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                heavensDoor(dayNum, currDay, residentOrderLists);
                break;
            case 3:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 4:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionFour();
                break;
        }
    }
    
    //Joestar Mansion mission
    public static void MissionFive(){
        int i = 4;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] The Golden Spirit");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                heavensDoor(dayNum, currDay, residentOrderLists);
                break;
            case 3:
                //jump to extra feature 7
                TheGoldenSpirit();
                break;
            case 4:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 5:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 6:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionFive();
                break;
        }
    }
    
    //Angelo Rock mission
    public static void MissionSix(){
        int i = 5;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] Red Hot Chili Pepper");
        System.out.println("[4] Another One Bites the Dust");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        if(currDay!=dayNum){
            residentOrderLists = ro.randomOrderGenerator(dayNum, currDay);
            currDay = dayNum;
        }
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                heavensDoor(dayNum, currDay, residentOrderLists);
                break;
            case 3:
                //jump to Super Fly (basic feature 7);
                RedHotChiliPepper();
                break;
            case 4: 
                //jump to extra feature 1;
                AnotherOneBites();
                break;
            case 5:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 6:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 7:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionSix();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionSix();
                break;
        }
    }
    
    //Joestar Mansion mission
    public static void MissionSeven(){
        int i = 4;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] Stay the Hell Away from Me!");
        System.out.println("[3] The Golden Spirit");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to extra feature 6
                StayTheHell();
                break;
            case 3:
                //jump to extra feature 7
                TheGoldenSpirit();
                break;
            case 4:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 5:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 6:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionSeven();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionSeven();
                break;
        }
    }
    
    //Green Dolphin Street Prison mission
    public static void MissionEight(){
        int i = 3;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] Dirty Deeds Done Dirt Cheap");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to extra feature 4
                DirtyDeedsDone();
                break;
            case 3:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 4:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionEight();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionEight();
                break;
        }
    }
    
    //Default mission
    public static void DefaultMission(){
        int i = 2;
        System.out.println("Sorry, no mission available at " + currentLocation);
        System.out.println("[1] Move to New Location");
        choices();
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 3:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    DefaultMission();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 4:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    DefaultMission();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                DefaultMission();
                break;
        }
    }
    
    //method to move to new location
    public static void moveLocation(){
        history.push(currentLocation);       
        System.out.print("Enter the destination [alphabet only]: ");
        String destinationOption = sc.nextLine().toUpperCase();
        char optionChar = destinationOption.charAt(0);
        int index = optionChar - 'A';
        if(index >= 0 && index < list.size()){
            currentLocation = (String) list.get(index);
            System.out.println("Moving to " + currentLocation);
            System.out.println("=".repeat(200));
        }else{
            System.out.println("Invalid input. Please choose again\n");
            history.pop();
            moveLocation();
        }
        
        //there's forward history, the record will be cleared
        if(!forward.empty())
            forward.clear();
    }
    
    public static void choices(){
        list = map.getNeighbours(currentLocation);
        if(!history.empty())        //removing locations that will contrast with moveForward and moveBackward
            list.remove(history.peek());
        if(!forward.empty())
            list.remove(forward.peek());
        if(list.isEmpty()){
            System.out.print("    No new location\n");
        }else{
            for(int i=0; i<list.size(); i++){
                if(i!=0 && i%2==0)
                    System.out.println("");
                String option = String.valueOf((char)('A' + i));
                System.out.printf("    [%s] %-25s", option, list.get(i));
            }
            System.out.println("");
        }
    }
    
    //method to move backward
    public static void moveBackward(){
        if(!forward.empty())
            forward.clear();
        String reverseLocation = history.pop();
        forward.push(currentLocation);
        currentLocation = reverseLocation;
    }
    
    //method to move forward
    public static void moveForward(){
        history.push(currentLocation);
        currentLocation = forward.pop();
        if(!forward.empty())
            forward.clear();
    }   
    
    //method to return to Town Hall directly
    public static void TownHall(){
        //check if Town Hall is the backward/forward adjacent location
        if(!history.empty() && history.peek().equals("Town Hall")){
            history.pop();
            forward.push(currentLocation);
        }else if(!forward.empty() && forward.peek().equals("Town Hall")){
            history.push(currentLocation);
            forward.clear();
        }else{
            history.push(currentLocation);
            if(!forward.empty())
                forward.clear();
        }
        currentLocation = "Town Hall";
    }
    
    //method to advance to next day
    public static void advance(){
        dayNum++;
        forward.clear();
        history.clear();
        displayDay();
    }
    
    //method to save the game
    public static void saveGame(){
        System.out.println("Saving game.....");

        JSONObject json = new JSONObject();
        
        //add chosen map
        json.put("mapLocation", mapLocation);
        
        //add dayNum
        json.put("dayNum", dayNum);
                
        //add currentLocation
        json.put("currentLocation", currentLocation);
        
        //add previous location
        if (!history.empty()) {
            JSONArray historyArray = new JSONArray();
            while (!history.empty()) {
                historyArray.add(history.pop());
            }
            json.put("history", historyArray);
        }
        
        //add forward location
        if(!forward.empty())
            json.put("forward", forward.pop());
        
        //add information relevant to resident's order list
        json.put("currDay", currDay);
        JSONArray totalOrders = new JSONArray();
        if(!residentOrderLists.isEmpty()){
            for(ArrayList<OrderList> resident : residentOrderLists){
                JSONArray residentOrder = new JSONArray();
                for(OrderList info : resident){
                    JSONObject item = new JSONObject();
                    item.put("name", info.getName());  
                    item.put("age", info.getAge());
                    item.put("gender", info.getGender());
                    item.put("arrivalTime", info.getArrivalTime());
                    item.put("dayNum", info.getDayNum());
                    item.put("food", info.getFood());
                    item.put("restaurant", info.getRestaurant());
                    item.put("indexRest", info.getIndexRest());
                    item.put("indexOrder", info.getIndexOrder());
                    item.put("price", info.getPrice());
                    residentOrder.add(item);
                }
                totalOrders.add(residentOrder);
            }
            json.put("residentOrderLists", totalOrders);
        }
        
        try {
            PrintWriter output = new PrintWriter(new FileWriter("savedGame.json"));
            output.write(json.toString());
            output.close();
        } catch (IOException e) {
            System.out.println("Problem occurs while saving the game...");
        }
        
        System.out.println("Game saved at savedGame.json");
        endGame();   
    }
    
    //method to load the game
    public static void loadGame() throws FileNotFoundException, ParseException, IOException{
        String savedPath = sc.nextLine();
        System.out.println("=".repeat(200));
        
        JSONObject json  = (JSONObject) new JSONParser().parse(new FileReader(savedPath));
        
        //read chosen map
        mapLocation = (String) json.get("mapLocation");
        
        //read dayNum
        dayNum = (int)(long)json.get("dayNum");
        
        //read currentLocation
        currentLocation = (String) json.get("currentLocation");
        
        //reload history
        if (json.containsKey("history")) {
            JSONArray historyArray = (JSONArray) json.get("history");
            for (int i=historyArray.size()-1; i>=0; i--) {
                String element = (String) historyArray.get(i);
                history.push(element);
            }
        }
        
        //read forward location
        if(json.containsKey("forward"))
            forward.push((String) json.get("forward"));
        
        //reload information relevant to resident's order list
        currDay = (int)(long)json.get("currDay");
        JSONArray totalOrder = (JSONArray) json.get("residentOrderLists");
        for (int i = 0; i < totalOrder.size(); i++) {
            JSONArray residentOrder = (JSONArray) totalOrder.get(i);
            ArrayList<OrderList> resident = new ArrayList<>();
            for (int j = 0; j < residentOrder.size(); j++) {
                JSONObject item = (JSONObject) residentOrder.get(j);
                String name = (String) item.get("name");
                String age = (String) item.get("age");
                String gender = (String) item.get("gender");
                int arrivalTime = (int)(long) item.get("arrivalTime");
                int num = (int)(long) item.get("dayNum");
                String food = (String) item.get("food");
                String restaurant = (String) item.get("restaurant");
                int indexrest = (int)(long) item.get("indexRest");
                int indexOrder = (int)(long) item.get("indexOrder");
                double price = (double) item.get("price");
                OrderList order = new OrderList(name, age, gender, arrivalTime, num, food, restaurant, indexrest, indexOrder, price);
                resident.add(order);
            }
            residentOrderLists.add(resident);
        }
        
                
        //then resume the game
        createMap(mapLocation);
        displayDay();
        getMission();
    }
    
    //method to exit the game
    public static void endGame(){
        System.out.println("THE END");
        System.out.println("Thank you for playing!");
        System.out.println("=".repeat(200));
        System.exit(0);
    }
    
    //method for Red Hot Chili Pepper
    public static void RedHotChiliPepper(){
        // Create an instance of MinimumSpanningTree
        RedHotChiliPepper<String,Integer> mst = new RedHotChiliPepper<>(map);
        
        // Find the minimum spanning tree
        mst.findMinimumSpanningTree();
    }
    
    //method for The Hand
    public static void TheHand() {
         // Create an instance of MinimumSpanningTree
        TheHand<String,Integer> mst = new TheHand<>(map);
        
        // Find the maximum spanning tree
        mst.findMaximumSpanningTree();
    }
    
    //method for Stay the Hell Away from Me!
    public static void StayTheHell(){
        StayTheHellAway s = new StayTheHellAway(map);
    }
    
    //method for The Golden Spirit
    public static void TheGoldenSpirit(){
        TheGoldenSpirit t = new TheGoldenSpirit();
    }
    
    //method for Another One Bites the Dust
    public static void AnotherOneBites() {
        AnotherOneBitestheDust btd = new AnotherOneBitestheDust();
        btd.run();
    }
    
    //method for Dirty Deeds Done Dirt Cheap
    public static void DirtyDeedsDone(){
        DirtyDeedsDoneDirtCheap d = new DirtyDeedsDoneDirtCheap(map);
    }
    
    //method for Thus Spoke Rohan Kishibe
    public static void ThusSpokeRohanKishibe(){
        ThusSpokeRohanKishibe tsrk = new ThusSpokeRohanKishibe(map, currentLocation);
        tsrk.thusSpokeRohanKishibe();
    }
    
    //method for Heaven's Door
    public static void heavensDoor(int dayNum, int currDay, ArrayList<ArrayList<OrderList>> residentOrderLists) {
        LoadFile loadSystemFile = new LoadFile();
        ArrayList<Resident> resident = loadSystemFile.loadresidentFromFile("resources/residents.csv");
        ArrayList<Stand> stand = loadSystemFile.loadstandFromFile("resources/stands.csv");
        ArrayList<Resident> residentsInArea = loadSystemFile.getResidentsInArea(currentLocation);
        loadSystemFile.printTable(residentsInArea);
        boolean loop = true;
        while (loop) {
            System.out.println("[1] View Resident's Profile");
            System.out.println("[2] Sort");
            System.out.println("[3] Exit");
            System.out.println("");

            System.out.print("Select: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    //jump to The Joestars (Basic Feature 4)
                    TheJoestars joestars = new TheJoestars(currentLocation, dayNum, currDay, residentOrderLists);
                    break;
                case "2":
                    // Prompt for the sorting order
                    // Sort the residents
                    ResidentComparator sorter = new ResidentComparator();
                    StandComparator sorter2 = new StandComparator();
                    sorter.sort(residentsInArea);
                    residentsInArea = sorter2.sort(residentsInArea);

                    // Print the sorted table
                    System.out.println("Sorted Table:");
                    loadSystemFile.printTable(residentsInArea);
                    break;
                case "3":
                    loop = false;
                    System.out.println("=".repeat(200));
                    break;
                default:
                    System.out.println("Wrong input, please select again.");
                    break;
            }
        }
    }

    //method for Pearl Jam
    public static void PearlJam(int dayNum, ArrayList<ArrayList<OrderList>> residentOrderLists) {
        PearlJam pearlJam = new PearlJam(currentLocation, dayNum, residentOrderLists);
        pearlJam.sortOrdersWithinRestaurants();
        pearlJam.displayWaitingList();
        pearlJam.displayOrderProcessingList();
    }
}
