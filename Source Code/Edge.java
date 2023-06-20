package JOJOLAND;

class Edge<T extends Comparable<T>, N extends Comparable<N>> implements Comparable<Edge<T, N>> {
    
    Vertex<T, N> toVertex;
    Vertex<T, N> fromVertex;
    N weight;
    Edge<T, N> nextEdge;

    public Edge(Vertex<T, N> source, Vertex<T, N> destination, N w, Edge<T, N> a) {
        this.fromVertex = source;
        this.toVertex = destination;
        this.weight = w;
        this.nextEdge = a;
    }

    public Vertex<T, N> getSource() {
        return this.toVertex;
    }

    public Vertex<T, N> getFromVertex() {
        return this.fromVertex;
    }

    public Edge<T, N> getToVertex() {
        return this.nextEdge;
    }

    public N getWeight() {
        return this.weight;
    }
    
    public void setWeight(N weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge<T, N> other) {
        return this.weight.compareTo(other.weight);
    }

    @Override
    public String toString() {
        return fromVertex.getName() + " -> " + toVertex.getName() + " " + this.weight;
    }
}



