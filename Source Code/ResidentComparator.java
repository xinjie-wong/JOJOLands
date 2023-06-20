package JOJOLAND;

import java.util.*;

public class ResidentComparator implements Comparator<Resident> {

    @Override
    public int compare(Resident resident1, Resident resident2) {
        // Compare by residential area
        int result = resident1.getResidentialArea().compareToIgnoreCase(resident2.getResidentialArea());
        if (result != 0) {
            return result;
        }

        // Compare by name
        result = resident1.getName().compareToIgnoreCase(resident2.getName());
        if (result != 0) {
            return result;
        }

        // Compare by age
        result = resident1.getAge().compareToIgnoreCase(resident2.getAge());
        if (result != 0) {
            return result;
        }

        // Compare by gender
        result = resident1.getGender().compareToIgnoreCase(resident2.getGender());
        if (result != 0) {
            return result;
        }

        // Compare by parents
        return resident1.getParents().compareToIgnoreCase(resident2.getParents());
    }

    public ArrayList<Resident> sort(ArrayList<Resident> residents) {
        // Perform sorting using double for-loop
        for (int i = 0; i < residents.size() - 1; i++) {
            for (int j = i + 1; j < residents.size(); j++) {
                Resident resident1 = residents.get(i);
                Resident resident2 = residents.get(j);
                if (compare(resident1, resident2) > 0) {
                    // Swap residents if they are not in the correct order
                    Collections.swap(residents, i, j);
                }
            }
        }
        return residents;
    }
}