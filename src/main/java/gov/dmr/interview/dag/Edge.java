package gov.dmr.interview.dag;

import gov.dmr.interview.dag.algorithms.EdgeClassification;

public class Edge<T> {
    private final Vertex<T> from;

    private final Vertex<T> to;

    private EdgeClassification classification;

    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    public Vertex<T> getFrom() {
        return from;
    }

    public Vertex<T> getTo() {
        return to;
    }

    public EdgeClassification getClassification() {
        return classification;
    }

    public void setClassification(EdgeClassification classification) {
        this.classification = classification;
    }

}
