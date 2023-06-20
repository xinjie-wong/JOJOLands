package JOJOLAND;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.*;

class RedHotChiliPepper<T extends Comparable<T>, N extends Number & Comparable<N>> {

    public Graph<T, N> graph;
    public Graph<T, N> mst;
    
    public RedHotChiliPepper(Graph<T, N> graph) {
        this.graph = graph;
        this.mst = new Graph<>(); // Initialize MST graph
    }
    
    public void findMinimumSpanningTree() {

        // Initialize an empty graph for the MST
        List<Edge<T, N>> allEdges = graph.getAllEdges();

        // Sort all edges based on weight
        Collections.sort(allEdges, Comparator.comparing(Edge::getWeight));
        
        // Create a map to track visited vertices
        Map<T, Boolean> visited = new HashMap<>();
        for (Vertex<T, N> vertex : graph.getAllVertices()) {
            visited.put(vertex.vertexInfo, false);
        }

        // Start with the first vertex
        Vertex<T, N> startVertex = graph.getAllVertices().get(0);
        visited.put(startVertex.vertexInfo, true);

        // Create a priority queue to store the edges
        PriorityQueue<Edge<T, N>> priorityQueue = new PriorityQueue<>();
        priorityQueue.addAll(startVertex.getAllEdges());

        int totalLength = 0; // Initialize the total length

        while (!priorityQueue.isEmpty()) {
            // Get the edge with minimum weight
            Edge<T, N> edge = priorityQueue.remove();

            Vertex<T, N> toVertex = edge.toVertex;

            if (!visited.get(toVertex.vertexInfo)) {
                // Add the edge to the minimum spanning tree
                mst.addEdge2(edge.fromVertex.vertexInfo, toVertex.vertexInfo, edge.weight);
                visited.put(toVertex.vertexInfo, true);

                // Add the weight of the edge to the total length
                totalLength += edge.weight.intValue();

                // Add the edges of the new vertex to the priority queue
                priorityQueue.addAll(toVertex.getAllEdges());
            }
        }

        // Print the necessary power cables to be upgraded
        System.out.println("Necessary Power Cables to be Upgraded:");
        int index = 1;
        for (Edge<T, N> edge : mst.getAllEdges2()) {
            System.out.println(index + ". " + edge.fromVertex.vertexInfo + " --- " + edge.toVertex.vertexInfo + " (" + edge.weight + " km)");
            index++;
        }
        System.out.println("Total Length: " + totalLength + " km");
        System.out.println("=".repeat(200));
        System.out.println();
    }
}