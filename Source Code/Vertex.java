package JOJOLAND;

import java.util.*;

class Vertex<T extends Comparable<T>, N extends Comparable <N>> {
    
    T vertexInfo;
    int deg;
    boolean visited;  //for dijkstra
    boolean checker;  //for extra feature 7
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;
    int distance;   //distance to source
    List<String> shortestPath; //a list of vertices that forms the shortest path (Dijkstra algorithm)
    
    public Vertex(T vInfo, Vertex<T,N> next){
        vertexInfo = vInfo;
        deg = 0;
        visited = false;
        nextVertex = next;
        firstEdge = null;
        distance = 0;
        checker = false;
        shortestPath = new ArrayList<>();
    }
    
    public T getName(){
        return this.vertexInfo;
    }
    
    @Override
    public String toString(){
        return vertexInfo + " " + deg;
    }
    
    public List<Edge<T, N>> getAllEdges() {
        List<Edge<T, N>> allEdges = new ArrayList<>();
        Edge<T, N> currentEdge = firstEdge;
        while (currentEdge != null) {
            allEdges.add(currentEdge);
            currentEdge = currentEdge.nextEdge;
        }
        return allEdges;
    }
    
    //check if the vertex has been visited previously
    public boolean hasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public List<String> getShortestPaths() {
        return shortestPath;
    }

    public void setShortestPaths(List<String> path) {
        this.shortestPath = path;
    }
    
    public boolean hasChecker() {
        return checker;
    }

    public void setChecker(boolean visited) {
        this.checker = visited;
    }
}
