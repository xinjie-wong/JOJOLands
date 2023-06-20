package JOJOLAND;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AnotherOneBitestheDust {
    
    public List<String> removeDuplicates(List<String> locations) {
        Set<String> uniqueLocations = new HashSet<>();
        List<String> result = new ArrayList<>();
        
        for (String location : locations) {
            if (!uniqueLocations.contains(location)) {
                uniqueLocations.add(location);
                result.add(location);
            }
        }
        return result;
    }
    
    public void run() {
        Scanner cs = new Scanner(System.in);
        System.out.print("Enter Yoshikage Kira's path: ");
        String input = cs.nextLine();
        
        List<String> locations = new ArrayList<>();
        String[] locationArray = input.split("->");
        for (String location : locationArray) {
            locations.add(location.trim());
        }
        
        // Check the number of duplicates
        List<String> uniqueLocations = removeDuplicates(locations);
        if (locations.size() - uniqueLocations.size() > 2) {
            String output = String.join(" -> ", uniqueLocations);
            System.out.println("=".repeat(200));
            System.out.println("Bites the Dust is most likely to be activated when Kira passed through");
            System.out.println(output);
            System.out.println("=".repeat(200));
        } else {
            System.out.println("=".repeat(200));
            System.out.println("Bites the Dust is not activated.");
            System.out.println("=".repeat(200));
        }
    }
}
 








