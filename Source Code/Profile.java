package JOJOLAND;

import java.util.ArrayList;

public class Profile{
    private String name;
    private String currentLocation;
    
    public Profile(String name, String currentLocation){
        this.name = name;
        this.currentLocation = currentLocation;
    }
    
    public boolean residentProfile(String residentFilePath, String standFilePath){

        LoadFile loadSystemFile = new LoadFile();
        ArrayList<Resident> resident = loadSystemFile.loadresidentFromFile(residentFilePath);
        ArrayList<Stand> stand = loadSystemFile.loadstandFromFile(standFilePath);
        //check name and print profile details
        boolean containName = false;
        for(int i=0;i<resident.size();i++){
            if(resident.get(i).getResidentialArea().equals(currentLocation)){
                if((resident.get(i).getName()).equals(name)){
                    System.out.println(name + "'s Profile");
                    System.out.println("Name\t: " + resident.get(i).getName() + "\n" 
                        + "Age\t: " + resident.get(i).getAge() + "\n" 
                        + "Gender\t: " + resident.get(i).getGender() + "\n"
                        + "Parents\t: " + resident.get(i).getParents());
                    //check name if have stand
                    for(int j=0;j<stand.size();j++){
                        if((stand.get(j).getStandUser()).equals(name)){
                                System.out.println("Stand\t\t\t: " + stand.get(j).getStand() + "\n" 
                                    + "Destructive Power\t: " + stand.get(j).getDestructivePower() + "\n" 
                                    + "Speed\t\t\t: " + stand.get(j).getSpeed() + "\n"
                                    + "Range\t\t\t: " + stand.get(j).getRange() + "\n" 
                                    + "Stamina\t\t\t: " + stand.get(j).getStamina() + "\n" 
                                    + "Precision\t\t: " + stand.get(j).getPrecision() + "\n" 
                                    + "Development Potential\t: " + stand.get(j).getDevelopmentPotential());
                           }
                    }
                    containName = true;
               }
            }
        }
        return containName;
    }
}