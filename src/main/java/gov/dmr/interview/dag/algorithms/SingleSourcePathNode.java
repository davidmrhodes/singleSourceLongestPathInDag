package gov.dmr.interview.dag.algorithms;

import gov.dmr.interview.dag.Vertex;

import java.util.Optional;

public class SingleSourcePathNode<T> {

    private Color color;

    private int distance;

    private Optional<Vertex<T>> predecessor;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Optional<Vertex<T>> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Optional<Vertex<T>> predecessor) {
        this.predecessor = predecessor;
    }

}
