package gov.dmr.interview.dag;

import gov.dmr.interview.dag.algorithms.DepthFirstSearch;
import gov.dmr.interview.dag.algorithms.SingleSourcePathNode;

import java.util.*;

public class DirectedAcyclicGraph<T> extends Graph<T> {

    public DirectedAcyclicGraph(ArrayList<Vertex<T>> vertices) {
        super(vertices);
    }

    public List<Vertex<T>> sort() {
        LinkedList<Vertex<T>> sortedVertices = new LinkedList<>();
        DepthFirstSearch<T> depthFirstSearch = new DepthFirstSearch<>(this, vertex -> sortedVertices.addFirst(vertex));
        depthFirstSearch.depthFirstSearch();
        if (depthFirstSearch.isCyclic()) {
            throw new RuntimeException("Graph is cyclic");
        }
        return sortedVertices;
    }

    public List<Vertex<T>> dagLongestPath(Vertex<T> start) {

        Optional<Vertex<T>> startVertex = getVertices().stream().filter(v -> v.getId().equals(start.getId())).findFirst();
        startVertex.orElseThrow(() -> new RuntimeException("No vertex with id " + start.getId()));

        List<Vertex<T>> sortedVertices = sort();

        DirectedAcyclicGraph<T> topologicalGraph = new DirectedAcyclicGraph<>(new ArrayList<>(sortedVertices));

        initializeSingleSource(topologicalGraph, start);
        topologicalGraph.getVertices().forEach(vertex -> {
            vertex.getEdges().forEach(edge -> relax(edge.getFrom(), edge.getTo()));
        });
        Optional<Vertex<T>> farthestVertex = topologicalGraph.getVertices().stream().max(Comparator.comparingLong(v -> v.getSspNode().getDistance()));
        if (farthestVertex.isPresent()) {
            int distance =  farthestVertex.get().getSspNode().getDistance();
            ArrayList<Vertex<T>> longestPath = new ArrayList<>(distance + 1);
            getPath(start, farthestVertex.get(), longestPath);
            return longestPath;
        } else {
            return new ArrayList<>();
        }
    }

    private void initializeSingleSource(DirectedAcyclicGraph<T> topologicalSortedGraph,  Vertex<T> start) {
        topologicalSortedGraph.getVertices().forEach(vertex -> {
            SingleSourcePathNode<T> node = vertex.getSspNode();
            node.setDistance(Integer.MIN_VALUE);
            node.setPredecessor(Optional.empty());
        });
        start.getSspNode().setDistance(0);
    }

    private void relax(Vertex<T> u, Vertex<T> v) {
        SingleSourcePathNode<T> uNode = u.getSspNode();
        SingleSourcePathNode<T> vNode = v.getSspNode();

        if (vNode.getDistance() < uNode.getDistance() + 1) {
            vNode.setDistance(uNode.getDistance() + 1);
            vNode.setPredecessor(Optional.of(u));
        }
    }

    public void getPath(Vertex<T> start, Vertex<T> finish, ArrayList<Vertex<T>> path) {
        if (finish == start) {
            path.add(start);
        } else if (finish.getSspNode().getPredecessor().isEmpty()) {
            throw new RuntimeException("No path from " + start.getId() + " to " + finish.getId());
        } else {
            getPath(start, finish.getSspNode().getPredecessor().get(), path);
            path.add(finish);
        }
    }

    public void printPath(T startId, T finishId) {
        Vertex<T> start = getVertices().stream().filter(v -> v.getId().equals(startId)).findFirst().get();
        Vertex<T> finish = getVertices().stream().filter(v -> v.getId().equals(finishId)).findFirst().get();
        printPath(start, finish);
    }

    public void printPath(Vertex<T> start, Vertex<T> finish) {
        if (finish == start) {
            System.out.println(start.getId() + " distance " + start.getSspNode().getDistance());
        } else if (finish.getSspNode().getPredecessor().isEmpty()) {
            System.out.println("No path from " + start.getId() + " to " + finish.getId());
        } else {
            printPath(start, finish.getSspNode().getPredecessor().get());
            System.out.println(finish.getId() + " distance " + finish.getSspNode().getDistance());
        }
    }
}
