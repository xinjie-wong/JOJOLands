package JOJOLAND;

import java.util.*;

class Graph<T extends Comparable<T>, N extends Comparable <N>> {
    Vertex<T,N> head;
    int size;
	
    public Graph(){
        head=null;
        size=0;
    }
    
    public int getDeg(T v){
        if (hasVertex(v)==true){
            Vertex<T,N> temp = head;
            while (temp!=null){
                if ( temp.vertexInfo.compareTo( v ) == 0 )
                    return temp.deg;
                temp=temp.nextVertex;
            }
        }
        return -1;
   }
       
    public boolean hasVertex(T v){
        if (head==null)
            return false;
        Vertex<T,N> temp = head;
        while (temp!=null){
            if ( temp.vertexInfo.compareTo( v ) == 0 )
                return true;
            temp=temp.nextVertex;
        }
        return false;
    }

    public void addVertex(T v){
        if (hasVertex(v)==false){
            Vertex<T,N> temp=head;
            Vertex<T,N> newVertex = new Vertex<>(v, null);
            if (head==null)   
                head=newVertex;
            else{
                Vertex<T,N> previous=head;
                while (temp!=null){
                    previous=temp;
                    temp=temp.nextVertex;
                }
                previous.nextVertex=newVertex;
            }
            size++;
        }
    }
   
    public ArrayList<T> getAllVertexObjects(){
        ArrayList<T> list = new ArrayList<>();
        Vertex<T,N> temp = head;
        while (temp!=null){
            list.add(temp.vertexInfo);
            temp=temp.nextVertex;
        }
        return list;
    }

    public ArrayList<Vertex<T,N>> getAllVertices(){
        ArrayList<Vertex<T,N>> list = new ArrayList<>();
        
        Vertex<T,N> temp = head;
        while (temp!=null){
            list.add(temp);
            temp=temp.nextVertex;
        }
        return list;
    }
    
    public List<Edge<T, N>> getAllEdges() {
        List<Edge<T, N>> allEdges = new ArrayList<>();

        Vertex<T, N> temp = head;
        while (temp != null) {
            Edge<T, N> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
                allEdges.add(currentEdge);
                currentEdge = currentEdge.nextEdge;
            }
            temp = temp.nextVertex;
        }
        return allEdges;
    }
    
    public List<Edge<T, N>> getAllEdges2() {
        List<Edge<T, N>> allEdges2 = new ArrayList<>();

        Vertex<T, N> temp = head;
        while (temp != null) {
            Edge<T, N> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
                allEdges2.add(currentEdge);
                currentEdge = currentEdge.nextEdge;
            }
            temp = temp.nextVertex;
        }
        return allEdges2;
    }
    
    public void addEdge(T source, T destination, N w){
        List<Edge<T, N>> allEdges = new ArrayList<>();
        addVertex(source);
        addVertex(destination);
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null){
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 ){
                // Reached source vertex, look for destination now
                Vertex<T,N> destinationVertex = head;
                while (destinationVertex!=null){
                    if ( destinationVertex.vertexInfo.compareTo( destination ) == 0 ){
                        // Reached destination vertex, add edge here
                        Edge<T,N> newEdgeOne = new Edge<>(sourceVertex,destinationVertex, w, sourceVertex.firstEdge);
                        Edge<T,N> newEdgeTwo = new Edge<>(destinationVertex,sourceVertex, w, destinationVertex.firstEdge); 
                        sourceVertex.firstEdge = newEdgeOne;
                        destinationVertex.firstEdge = newEdgeTwo;
                        sourceVertex.deg++;
                        destinationVertex.deg++;
                    }
                    destinationVertex=destinationVertex.nextVertex;
                }
            }
            sourceVertex=sourceVertex.nextVertex;
        }
    }
   
    public void addEdge2(T source, T destination, N w) {
        List<Edge<T, N>> allEdges2 = new ArrayList<>();
        addVertex(source);
        addVertex(destination);
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                // Reached source vertex, look for destination now
                Vertex<T, N> destinationVertex = head;
                while (destinationVertex != null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        // Reached destination vertex, add edge here
                        Edge<T, N> newEdge = new Edge<>(sourceVertex, destinationVertex, w, sourceVertex.firstEdge);
                        sourceVertex.firstEdge = newEdge;
                        sourceVertex.deg++;
                    }
                    destinationVertex = destinationVertex.nextVertex;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
    }
    
    public boolean hasEdge(T source, T destination){
        if (head==null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination)) 
            return false;
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null){
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )  {
                // Reached source vertex, look for destination now 
                Edge<T,N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                   if (currentEdge.toVertex.vertexInfo.compareTo(destination)==0) 
                   // destination vertex found 
                        return true;
                   currentEdge=currentEdge.nextEdge;
                }
            }
            sourceVertex=sourceVertex.nextVertex;
        }
        return false;
    }
   
    public N getEdgeWeight(T source, T destination){
        N notFound=null;
        if (head==null)
            return notFound;
        if (!hasVertex(source) || !hasVertex(destination)) 
            return notFound;
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null){
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 ){
            // Reached source vertex, look for destination now 
                Edge<T,N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null){
                   if (currentEdge.toVertex.vertexInfo.compareTo(destination)==0) 
                   // destination vertex found 
                        return currentEdge.weight;
                   currentEdge=currentEdge.nextEdge;
                }
            }
            sourceVertex=sourceVertex.nextVertex;
        }
        return notFound;
    }
   
    public ArrayList<T> getNeighbours (T v){
        if (!hasVertex(v))
            return null;
        ArrayList<T> list = new ArrayList<T>();
        Vertex<T,N> temp = head;
        while (temp!=null)	{
            if ( temp.vertexInfo.compareTo( v ) == 0 ){
                // Reached vertex, look for destination now
                Edge<T,N> currentEdge = temp.firstEdge;
                while (currentEdge != null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge=currentEdge.nextEdge;
                }
            }
            temp=temp.nextVertex;
        }
        return list;   
    }
   
    public void printEdges(){
        Vertex<T,N> temp=head;
        while(temp!=null){
            System.out.print("# " + temp.vertexInfo + " : " );
            Edge<T,N> currentEdge = temp.firstEdge;
            while (currentEdge != null){
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo +"] " );
                currentEdge=currentEdge.nextEdge;
            }
            System.out.println();
            temp=temp.nextVertex;
        }  
    }

    public Vertex<T, N> getVertex(T v) {
        if (head == null)
            return null;
        Vertex<T, N> temp = head;
        while (temp != null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return temp;
            temp = temp.nextVertex;
        }
        return null;
    }
    
    public void removeEdge(T source, T destination) {
        if (head == null)
            return;
        if (!hasVertex(source) || !hasVertex(destination))
            return;

        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                // Reached source vertex, look for destination now
                Edge<T, N> prevEdge = null;
                Edge<T, N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                    if (currentEdge.getSource().vertexInfo.compareTo(destination) == 0) {
                        // Destination vertex found, remove the edge
                        if (prevEdge == null) {
                            // Edge to be removed is the first edge
                            sourceVertex.firstEdge = currentEdge.nextEdge;
                        } else {
                            prevEdge.nextEdge = currentEdge.nextEdge;
                        }
                        sourceVertex.deg--;
                        return;
                    }
                    prevEdge = currentEdge;
                    currentEdge = currentEdge.nextEdge;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
    }
    
    //for extra feature 4
    //resetting the vertices in case the values of the variables have been changed 
    public void resetVertices() {
        Vertex<T, N> temp = head;
        while (temp != null) {
            temp.setDistance(Integer.MAX_VALUE);
            temp.setVisited(false);
            temp.setShortestPaths(new ArrayList());
            temp = temp.nextVertex;
        }
    } 
    
    //for extra feature
    public void resetEdgeWeight(T source, T destination, N w){
        Vertex<T,N> sourceVertex = getVertex(source);
        Vertex<T,N> destinationVertex = getVertex(destination);
        Edge<T, N> resetEdge = sourceVertex.firstEdge;
        while(resetEdge!=null){
            if(resetEdge.toVertex.vertexInfo.compareTo(destination) == 0){
                resetEdge.setWeight(w);
            }
            resetEdge = resetEdge.nextEdge;
        }
        Edge<T, N> resetEdgeTwo = destinationVertex.firstEdge;
        while(resetEdgeTwo!=null){
            if(resetEdgeTwo.toVertex.vertexInfo.compareTo(source) == 0){
                resetEdgeTwo.setWeight(w);
                break;
            }
            resetEdgeTwo = resetEdgeTwo.nextEdge;
        }
    }
    
    public void removeVertex(T v) {
        if (head == null) {
            return;
        }
        if (head.vertexInfo.compareTo(v) == 0) {
            head.setChecker(true);
            return;
        }
        Vertex<T, N> current = head;
        while (current != null) {
            if (current.vertexInfo.compareTo(v) == 0) {
                current.setChecker(true);
                return;
            }
            current = current.nextVertex;
        }
    }
    
    public void restoreVertex(T v) {
        if (head == null) {
            return;
        }
        if (head.vertexInfo.compareTo(v) == 0) {
            head.setChecker(false);
            return;
        }
        Vertex<T, N> current = head;
        while (current != null) {
            if (current.vertexInfo.compareTo(v) == 0) {
                current.setChecker(false);
                return;
            }
            current = current.nextVertex;
        }
    }
}