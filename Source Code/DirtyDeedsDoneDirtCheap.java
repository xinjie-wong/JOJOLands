package JOJOLAND;

import java.util.Scanner;
import java.util.*;

public class DirtyDeedsDoneDirtCheap{
    
    static Scanner sc = new Scanner(System.in);
    static Graph map = new Graph();
    static List<String> locations;
    
    //constructor to initialize the list of locations
    public DirtyDeedsDoneDirtCheap(Graph map){
        this.map = map;
        locations = this.map.getAllVertexObjects();
        DirtyDeeds();
    }
    
    public static void DirtyDeeds(){    
        System.out.println("List of locations: ");
        int i = 0;
        for(String location : locations){
            System.out.printf("[%s] %s\n", i, location);
            i++;
        }
        System.out.println("");    
        
        //get input from user
        System.out.print("Source: ");
        String source = sc.nextLine();
        System.out.print("Destination: ");
        String destination = sc.nextLine();
        
        //check if location is available
        if(StartInterface.map.hasVertex(source) && StartInterface.map.hasVertex(destination)){
            System.out.println("=".repeat(200));
            ThreeShortestPaths(source, destination);
        }else{
            System.out.println("");
            if(!StartInterface.map.hasVertex(source))
                System.out.println("Source is not found");
            if(!StartInterface.map.hasVertex(destination))
                System.out.println("Destination is not found");
            
            //ask if the user want to reenter the locations
            System.out.print("Do you want to try again? [y/n]: ");
            String s = sc.nextLine();
            if(s.equals("y")){
                System.out.println("=".repeat(200));
                DirtyDeeds();
            }else if(s.equals("n")){
                System.out.println("=".repeat(200));
                System.out.println("Returning back.....\n");
                System.out.println("=".repeat(200));
            }
        }
    }
    
    public static void ThreeShortestPaths(String source, String destination) {
        List<List<String>> result = new ArrayList<>();
        List<List<String>> potentialPaths = new ArrayList<>();

        // Find the shortest path using Dijkstra's algorithm
        List<String> topShortestPath = Dijkstra(source, destination);
        topShortestPath.add(destination);
        result.add(topShortestPath);

        for (int i = 1; i < 3; i++){
            List<String> previousPath = result.get(i - 1);

            // Iterate over each edge in the previous path
            for (int j = 0; j < previousPath.size() - 1; j++) {
                String spurNode = previousPath.get(j);
                String nextSpurNode = previousPath.get(j+1);
                List<String> rootPath = previousPath.subList(0, j + 1);

                // Temporarily changing the edge weight between 
                int weight = (int) map.getEdgeWeight(spurNode, nextSpurNode);
                map.resetEdgeWeight(spurNode, nextSpurNode, Integer.MAX_VALUE);

                // Find the shortest path from the spur node to the destination
                List<String> spurPath = Dijkstra(spurNode, destination);

                // Check if a spur path exists
                if (!spurPath.isEmpty()) {
                    List<String> totalPath = new ArrayList<>(rootPath);
                    List<String> temp = spurPath.subList(1, spurPath.size());
                    totalPath.addAll(temp);
                    totalPath.add(destination);
                    // Add the potential path to the list
                    potentialPaths.add(totalPath);
                }

                // Restore back the edge weight
                map.resetEdgeWeight(spurNode, nextSpurNode, weight);
            }

            // Check if there are no potential paths
            if (potentialPaths.isEmpty()) {
                break;
            }
            
            // Sort the potential paths based on their total weight
            potentialPaths.sort(Comparator.comparingInt(DirtyDeedsDoneDirtCheap::calculatePathLength));
            
            // Add the lowest weight potential path to the result
            boolean checker = false;
            for(List<String> path : potentialPaths){
                for(List<String> paths : result){
                    if(path.size() != paths.size() && !paths.containsAll(path)){
                        result.add(path);
                        potentialPaths.remove(path);
                        checker = true;
                        break;
                    }
                }
                if(checker)
                    break;
            }
            
        }
        
        // Sort the paths based on their total weight again
        result.sort(Comparator.comparingInt(DirtyDeedsDoneDirtCheap::calculatePathLength));
         
        //get the length for the three paths
        List<Integer> length = new ArrayList<>();
        int totalLength = 0;
        for(List<String> path : result){
            totalLength = calculatePathLength(path);
            length.add(totalLength);
            totalLength = 0;
        }
        
        printPath(result, length);
    }
    
    //method to calculate the path length
    public static int calculatePathLength(List<String> path) {
        int length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String source = path.get(i);
            String destination = path.get(i + 1);
            length += (int) map.getEdgeWeight(source, destination);
        }
        return length;
    }
    
    //method to find the shortest path (Dijkstra algorithm)
    public static List<String> Dijkstra(String source, String destination){
        Vertex<String, Integer> sourceVertex = map.getVertex(source);
        Vertex<String, Integer> destinationVertex = map.getVertex(destination);
        
        //reset the vertices in case some of the variables in the vertices have been changed
        map.resetVertices();
        
        //set distance of the source vertex to 0, since source to source = 0
        sourceVertex.setDistance(0);
        
        //use PriorityQueue to store unvisited vertices in ascending order of the distance from the source
        PriorityQueue<Vertex<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(vertex -> vertex.getDistance()));
        queue.add(sourceVertex);    //adding the source vertex
        
        while(!queue.isEmpty()){
            
            //removing the first vertex from the queue
            Vertex<String, Integer> vertex = queue.poll();
            vertex.setVisited(true);    //mark the vertex as visited
            
            //check if reaching the destination
            if (vertex == destinationVertex) {
                break;
            }
            
            //getting the neighbour vertices
            Edge<String, Integer> edge = vertex.firstEdge;
            while(edge!=null){
                Vertex<String, Integer> neighbour = edge.toVertex;
                
                //check if the neighbour vertex has been visited previously
                if (!neighbour.hasVisited()) {
                    int distance = vertex.getDistance() + edge.weight;  //summing up to distance from the source to the neighbour vertex
                    
                    //if the new distance is shorter than the existing distance from the source
                    if(distance < neighbour.getDistance()){
                        neighbour.setDistance(distance);
                        List<String> newPath = new ArrayList<>(vertex.getShortestPaths());
                        newPath.add(vertex.vertexInfo);     //append the neighbour vertex to the current vertex for each path
                        neighbour.setShortestPaths(newPath);  
                        queue.add(neighbour);
                    }
                }
                edge  = edge.nextEdge;
            }
        }
        
        //destinationVertex.getShortestPaths().add(destination);
        return destinationVertex.getShortestPaths();
    }
    
    //method to print the top three shortest paths
    public static void printPath(List<List<String>> paths, List<Integer> weight){
        System.out.println("Top Three Shortest paths: ");
        int num = 1;
        for(List<String> path : paths){
            System.out.printf("%d. ", num);
            for(int i = 0; i < path.size(); i++){
                if(i==path.size()-1)
                    System.out.printf("%s (%dkm)\n",path.get(i), weight.get(num-1));
                else
                    System.out.print(path.get(i) + " --- ");
            }
            num++;
        }
        System.out.println("");
        System.out.println("=".repeat(200));
        
        //ask if the user want to reenter the locations
        System.out.print("Do you want to try again? [y/n]: ");
        String s = sc.nextLine();
        if(s.equals("y")){
            DirtyDeeds();
        }else if(s.equals("n")){
            System.out.println("Returning back.....\n");
        }
    }
 
}
