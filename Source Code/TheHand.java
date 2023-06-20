package JOJOLAND;

import java.util.*;

class TheHand<T extends Comparable<T>, N extends Number & Comparable<N>> {

    public Graph<T, N> graph;
    public Graph<T, N> mst;
    public Graph<T,N> directed;
    public int totalLength;

    public TheHand(Graph<T, N> graph) {
        this.graph = graph;
        // Initialize MST graph
        this.mst = new Graph<>(); 
        // Initialize directed graph
        this.directed = new Graph<>(); 
        this.totalLength = 0;
    }

    public void findMaximumSpanningTree() {
        
        // Initialize an empty graph for MST
        List<Edge<T, N>> allEdges = graph.getAllEdges();

        // Add edges into directed graph 
        for (Edge<T, N> edge : allEdges) {
            T fromVertex = edge.fromVertex.vertexInfo;
            T toVertex = edge.toVertex.vertexInfo;
            N weight = edge.getWeight();
            directed.addEdge2(fromVertex, toVertex, weight);
        }
        
        // Sort all edges based on weight in descending order
        Collections.sort(allEdges, Collections.reverseOrder(Comparator.comparing(Edge::getWeight)));

        // Create a map to track visited vertices
        Map<T, Boolean> visited = new HashMap<>();
        for (Vertex<T, N> vertex : graph.getAllVertices()) {
            visited.put(vertex.vertexInfo, false);
        }

        // Start with the first vertex
        Vertex<T, N> startVertex = graph.getAllVertices().get(0);
        visited.put(startVertex.vertexInfo, true);

        // Create a priority queue to store the edges
        PriorityQueue<Edge<T, N>> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        priorityQueue.addAll(startVertex.getAllEdges());
        
        while (!priorityQueue.isEmpty()) {
            // Get the edge with maximum weight
            Edge<T, N> edge = priorityQueue.remove();

            Vertex<T, N> toVertex = edge.toVertex;

            if (!visited.get(toVertex.vertexInfo)) {
                
                // Add the edge to the maximum spanning tree
                mst.addEdge2(edge.fromVertex.vertexInfo, toVertex.vertexInfo, edge.weight);
                
                visited.put(toVertex.vertexInfo, true);

                // Add the edges of the new vertex to the priority queue
                priorityQueue.addAll(toVertex.getAllEdges());
            }
        }
        
        List<Edge<T, N>> allDirEdges = directed.getAllEdges();
        
        // Convert undirected graph to directed graph
        for (Edge<T, N> edge : allDirEdges) {
            T fromVertex = edge.fromVertex.vertexInfo;
            T toVertex = edge.toVertex.vertexInfo;
            if (directed.hasEdge(fromVertex, toVertex) && directed.hasEdge(toVertex, fromVertex)) {
                directed.removeEdge(toVertex, fromVertex);
            }
        }

        // Compare edges in MST with edges in directed graph and remove common edges
        List<Edge<T, N>> mstEdges = mst.getAllEdges2();
        List<Edge<T, N>> directedEdges = directed.getAllEdges2();

        for (Edge<T, N> directedEdge : directedEdges) {
            for (Edge<T, N> mstEdge : mstEdges) {
                if (directed.hasEdge(mstEdge.fromVertex.vertexInfo, mstEdge.toVertex.vertexInfo) 
                        || directed.hasEdge(mstEdge.toVertex.vertexInfo, mstEdge.fromVertex.vertexInfo)){
                    directed.removeEdge(mstEdge.fromVertex.vertexInfo, mstEdge.toVertex.vertexInfo);
                    directed.removeEdge(mstEdge.toVertex.vertexInfo, mstEdge.fromVertex.vertexInfo);
                }
            }
        }
        
        List<Edge<T, N>> unnecessaryconnections = directed.getAllEdges2();
        
        // Print unnecessary water connections
        System.out.println("Unnecessary Water Connections to be Removed:");
        int index = 1;
        for (Edge<T, N> edge : unnecessaryconnections) {
            System.out.println(index + ". " + edge.fromVertex.vertexInfo + " --- " + edge.toVertex.vertexInfo + " (" + edge.weight + " km)");
            totalLength += edge.weight.intValue();
            index++;
        }
        System.out.println("Total Length: " + totalLength + " km");
        System.out.println("=".repeat(200));
    }
}