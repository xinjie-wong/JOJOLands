package JOJOLAND;

import java.util.Scanner;
import java.util.*;

public class StayTheHellAway {

    static Scanner sc = new Scanner(System.in);
    static Graph map = new Graph();
    static List<String> locations;

    // constructor to initialize the list of locations
    public StayTheHellAway(Graph map) {
        this.map = map;
        locations = this.map.getAllVertexObjects();
        HellAway();
    }

    public static void HellAway() {
        System.out.println("List of locations: ");
        int i = 0;
        for (String location : locations) {
            System.out.printf("[%s] %s\n", i, location);
            i++;
        }
        System.out.println("");

        // get input from user
        System.out.print("Source: ");
        String source = sc.nextLine();
        
        System.out.print("Destination: ");
        String destination = sc.nextLine();

        System.out.print("Identified Locations (comma-separated): ");
        String identifiedLocationsInput = sc.nextLine();
        String[] identifiedLocations = identifiedLocationsInput.split(",");

        // Remove identified locations from the graph
        removeIdentifiedLocations(map, identifiedLocations);
        
        // check if location is available
        if (map.hasVertex(source) && map.hasVertex(destination)) {
            System.out.println("=".repeat(200));
            List<String> shortestPath = Dijkstra(source, destination);
            int pathLength = calculatePathLength(shortestPath);
            printPath(shortestPath, pathLength);
        } else {
            System.out.println("");
            if (!map.hasVertex(source))
                System.out.println("Source is not found");
            if (!map.hasVertex(destination))
                System.out.println("Destination is not found");

            // ask if the user wants to reenter the locations
            System.out.print("Do you want to try again? [y/n]: ");
            String s = sc.nextLine();
            if (s.equals("y")) {
                System.out.println("=".repeat(200));
                HellAway();
            } else if (s.equals("n")) {
                System.out.println("=".repeat(200));
                System.out.println("Returning back.....\n");
                restoreIdentifiedLocations(map, identifiedLocations);
                System.out.println("=".repeat(200));
            }
        }
    }

    //method to remove identified locations
    public static void removeIdentifiedLocations(Graph<String, Integer> map, String[] identifiedLocations) {
        for (String location : identifiedLocations) {
            map.removeVertex(location);
        }
    }
    
    //method to restore identified locations back into graph
    public static void restoreIdentifiedLocations(Graph<String, Integer> map, String[] identifiedLocations) {
        for (String location : identifiedLocations) {
            map.restoreVertex(location);
        }
    }
    
    
    // method to calculate the path length
    public static int calculatePathLength(List<String> path) {
        int length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String source = path.get(i);
            String destination = path.get(i + 1);
            length += (int) map.getEdgeWeight(source, destination);
        }
        return length;
    }

    // method to find the shortest path (Dijkstra's algorithm)
    public static List<String> Dijkstra(String source, String destination) {
        Vertex<String, Integer> sourceVertex = map.getVertex(source);
        Vertex<String, Integer> destinationVertex = map.getVertex(destination);

        // reset the vertices in case some of the variables in the vertices have been changed
        map.resetVertices();

        // set distance of the source vertex to 0, since source to source = 0
        sourceVertex.setDistance(0);

        // use PriorityQueue to store unvisited vertices in ascending order of the distance from the source
        PriorityQueue<Vertex<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        queue.add(sourceVertex); // adding the source vertex

        while (!queue.isEmpty()) {

            // removing the first vertex from the queue
            Vertex<String, Integer> vertex = queue.poll();
            vertex.setVisited(true); // mark the vertex as visited

            // check if reaching the destination
            if (vertex == destinationVertex) {
                break;
            }

            // getting the neighbor vertices
            Edge<String, Integer> edge = vertex.firstEdge;
            while (edge != null) {
                Vertex<String, Integer> neighbour = edge.toVertex;

                // check if the neighbor vertex has been visited previously
                if (!neighbour.hasChecker() && !neighbour.hasVisited()) {
                    int distance = vertex.getDistance() + edge.weight; // summing up to distance from the source to the
                    // neighbor vertex
                    
                    // if the new distance is shorter than the existing distance from the source
                    if (distance < neighbour.getDistance()) {
                        neighbour.setDistance(distance);
                        List<String> newPath = new ArrayList<>(vertex.getShortestPaths());
                        newPath.add(vertex.vertexInfo); // append the neighbor vertex to the current vertex for each path
                        neighbour.setShortestPaths(newPath);
                        queue.add(neighbour);
                    }
                }
                edge = edge.nextEdge;
            }
        }

        List<String> shortestPath = destinationVertex.getShortestPaths();
        shortestPath.add(destination);
        return shortestPath;
    }

    // method to print the shortest path
    public static void printPath(List<String> path, int weight) {
        System.out.println("Optimal Path:");
        for (int i = 0; i < path.size(); i++) {
            if (i == path.size() - 1){
                System.out.printf("%s\n", path.get(i));
                System.out.printf("(%dkm)", weight);}
            else{
                System.out.print(path.get(i) + " -> ");}
        }
        System.out.println("");
        System.out.println("=".repeat(200));

        // ask if the user wants to reenter the locations
        System.out.print("Do you want to try again? [y/n]: ");
        String s = sc.nextLine();
        if (s.equals("y")) {
            HellAway();
        } else if (s.equals("n")) {
            System.out.println("Returning back.....");
            System.out.println("=".repeat(200));
        }
    }
}