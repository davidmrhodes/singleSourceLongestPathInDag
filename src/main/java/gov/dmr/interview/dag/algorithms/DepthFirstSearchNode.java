package gov.dmr.interview.dag.algorithms;

import gov.dmr.interview.dag.Vertex;

import java.util.Objects;
import java.util.Optional;

public class DepthFirstSearchNode<T> {

    private Color color;

    private Optional<Vertex<T>> predecessor;

    private int discoveryTime;

    private int finishTime;

    public DepthFirstSearchNode() {
        this.color = Color.WHITE;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Optional<Vertex<T>> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Optional<Vertex<T>> predecessor) {
        this.predecessor = predecessor;
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DepthFirstSearchNode<T> that = (DepthFirstSearchNode<T>) o;
        return discoveryTime == that.discoveryTime && finishTime == that.finishTime && color == that.color && predecessorEquals(that.getPredecessor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, predecessor, discoveryTime, finishTime);
    }

    @Override
    public String toString() {
        return "DepthFirstSearchNode{" +
                "color=" + color +
                ", predecessor=" + predecessor +
                ", discoveryTime=" + discoveryTime +
                ", finishTime=" + finishTime +
                '}';
    }

    private boolean predecessorEquals(Optional<Vertex<T>> other) {
        if (other.isEmpty()) {
            return this.getPredecessor().isEmpty();
        }
        if (this.getPredecessor().isEmpty()) {
            return false;
        }
        return other.get().getId().equals(this.getPredecessor().get().getId());
    }
}
