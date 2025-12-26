package gov.dmr.interview.dag;

import gov.dmr.interview.dag.algorithms.DepthFirstSearchNode;
import gov.dmr.interview.dag.algorithms.SingleSourcePathNode;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private final T id;

    private final List<Edge<T>> edges;

    private final DepthFirstSearchNode<T> dfsNode;

    public SingleSourcePathNode<T> getSspNode() {
        return sspNode;
    }

    private final SingleSourcePathNode<T> sspNode;

    public Vertex(T id) {
        this.id = id;
        this.edges = new ArrayList<>();
        dfsNode = new DepthFirstSearchNode<T>();
        sspNode = new SingleSourcePathNode<>();
    }

    public T getId() {
        return id;
    }

    public void addNeighbor(Vertex<T> neighbor) {
        edges.add(new Edge<>(this, neighbor));
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public DepthFirstSearchNode<T> getDfsNode() {
        return dfsNode;
    }
}