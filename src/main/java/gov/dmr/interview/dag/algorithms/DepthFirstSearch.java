package gov.dmr.interview.dag.algorithms;

import gov.dmr.interview.dag.Edge;
import gov.dmr.interview.dag.Graph;
import gov.dmr.interview.dag.Vertex;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

public class DepthFirstSearch<T> {
    private ArrayList<Vertex<T>> vertices;

    private Optional<Consumer<Vertex<T>>> finishConsumer;

    private int time;

    private boolean ranDFS = false;

    private boolean hasBackEdge = false;

    public DepthFirstSearch(Graph<T> graph) {
       this(graph, null);
    }

    public DepthFirstSearch(Graph<T> graph, Consumer<Vertex<T>> finishConsumer) {
        vertices = graph.getVertices();
        this.finishConsumer = Optional.ofNullable(finishConsumer);
    }

    public void depthFirstSearch() {
        initialize();
        time = 1;
        for(int i = 0; i < vertices.size(); i++) {
            Vertex<T> vertex = vertices.get(i);
            if (vertex.getDfsNode().getColor() == Color.WHITE) {
                depthFirstSearchVisit(vertex);
            }
        }
        ranDFS = true;
    }

    public boolean isCyclic() {
        if (!ranDFS) throw new RuntimeException("depthFirstSearch() must be called before isCyclic()");
        return hasBackEdge;
    }

    private void initialize() {
        hasBackEdge = false;
        vertices.stream().forEach(vertex -> {
            DepthFirstSearchNode node = vertex.getDfsNode();
            node.setColor(Color.WHITE);
            node.setPredecessor(Optional.empty());
            node.setDiscoveryTime(0);
            node.setFinishTime(0);
            vertex.getEdges().stream().forEach(edge -> edge.setClassification(EdgeClassification.NOT_VISITED));
        });
    }

    private void depthFirstSearchVisit(Vertex<T> vertex) {
        DepthFirstSearchNode<T> node = vertex.getDfsNode();
        node.setColor(Color.GRAY);
        node.setDiscoveryTime(time++);
        vertex.getEdges().stream()
          .forEach(edge -> {
            classifyEdge(edge);
            Vertex<T> neighbor = edge.getTo();
            DepthFirstSearchNode<T> neighborNode = neighbor.getDfsNode();
            if (neighborNode.getColor() == Color.WHITE) {
                neighborNode.setPredecessor(Optional.of(vertex));
                depthFirstSearchVisit(neighbor);
            }
        });
        node.setColor(Color.BLACK);
        node.setFinishTime(time++);
        finishConsumer.ifPresent(vertexConsumer -> vertexConsumer.accept(vertex));
    }

    private void classifyEdge(Edge<T> edge) {
        if (edge.getClassification() == EdgeClassification.NOT_VISITED) {
            switch (edge.getTo().getDfsNode().getColor()) {
                case WHITE -> edge.setClassification(EdgeClassification.TREE);
                case GRAY -> {
                    edge.setClassification(EdgeClassification.BACK);
                    hasBackEdge = true;
                }
                case BLACK -> edge.setClassification(EdgeClassification.FORWARD_OR_CROSS);
            }
        }
    }
}
