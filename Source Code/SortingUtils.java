package JOJOLAND;

import java.util.ArrayList;

public class SortingUtils {
    public static void sortResidents(ArrayList<Resident> residents, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionResidents(residents, low, high);
            sortResidents(residents, low, pivotIndex - 1);
            sortResidents(residents, pivotIndex + 1, high);
        }
    }

    public static int partitionResidents(ArrayList<Resident> residents, int low, int high) {
        Resident pivot = residents.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compareResidents(residents.get(j), pivot) <= 0) {
                i++;
                swapResidents(residents, i, j);
            }
        }

        swapResidents(residents, i + 1, high);
        return i + 1;
    }

    public static int compareResidents(Resident resident1, Resident resident2) {
        // Compare residents based on multiple field
        // Implement your custom comparison logic here
        // Return a negative value if resident1 is less than resident2
        // Return a positive value if resident1 is greater than resident2
        // Return 0 if resident1 is equal to resident2

        return 0; // Placeholder, modify this based on your logic
    }

    public static void swapResidents(ArrayList<Resident> residents, int i, int j) {
        Resident temp = residents.get(i);
        residents.set(i, residents.get(j));
        residents.set(j, temp);
    }
}