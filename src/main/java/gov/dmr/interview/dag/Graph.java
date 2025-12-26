package gov.dmr.interview.dag;


import java.util.ArrayList;
import java.util.Optional;

public class Graph<T> {
    ArrayList<Vertex<T>> vertices;

    public Graph(ArrayList<Vertex<T>> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    public Optional<Vertex<T>> getVertexById(T id) {
       return getVertices().stream().filter(v -> v.getId().equals(id)).findFirst();
    }
}