package gov.dmr.interview.dag.algorithms;

import gov.dmr.interview.dag.BaseTest;
import gov.dmr.interview.dag.Edge;
import gov.dmr.interview.dag.Graph;
import gov.dmr.interview.dag.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstSearchTest extends BaseTest {

    private Graph<String> sampleGraph;

    @BeforeEach
    public void setUp() {
        sampleGraph = graphCreator.createSampleGraphWithCycles();
    }

    @Test
    public void testWithSampleGraph() {
        DepthFirstSearch<String> depthFirstSearch = new DepthFirstSearch<>(sampleGraph);
        depthFirstSearch.depthFirstSearch();

        assertDepthFirstSearchNodes(sampleGraph, createSampleGraphExpectedNodes());
        assertDepthFirstSearchEdges(sampleGraph, createSampleGraphExpectedEdges());
    }

    private ArrayList<DepthFirstSearchNode<String>> createSampleGraphExpectedNodes() {
        ArrayList<DepthFirstSearchNode<String>> expectedNodes = new ArrayList<>(sampleGraph.getVertices().size());
        DepthFirstSearchNode<String> u = new DepthFirstSearchNode<>();
        u.setColor(Color.BLACK);
        u.setDiscoveryTime(1);
        u.setFinishTime(8);
        u.setPredecessor(Optional.empty());
        expectedNodes.add(u);

        DepthFirstSearchNode<String> v = new DepthFirstSearchNode<>();
        v.setColor(Color.BLACK);
        v.setDiscoveryTime(2);
        v.setFinishTime(7);
        Optional<Vertex<String>> predecessor = sampleGraph.getVertices().stream().filter(vertex -> vertex.getId().equals("u")).findFirst();
        v.setPredecessor(predecessor);
        expectedNodes.add(v);

        DepthFirstSearchNode<String> w = new DepthFirstSearchNode<>();
        w.setColor(Color.BLACK);
        w.setDiscoveryTime(9);
        w.setFinishTime(12);
        w.setPredecessor(Optional.empty());
        expectedNodes.add(w);

        DepthFirstSearchNode<String> x = new DepthFirstSearchNode<>();
        x.setColor(Color.BLACK);
        x.setDiscoveryTime(4);
        x.setFinishTime(5);
        predecessor = sampleGraph.getVertices().stream().filter(vertex -> vertex.getId().equals("y")).findFirst();
        x.setPredecessor(predecessor);
        expectedNodes.add(x);

        DepthFirstSearchNode<String> y = new DepthFirstSearchNode<>();
        y.setColor(Color.BLACK);
        y.setDiscoveryTime(3);
        y.setFinishTime(6);
        predecessor = sampleGraph.getVertices().stream().filter(vertex -> vertex.getId().equals("v")).findFirst();
        y.setPredecessor(predecessor);
        expectedNodes.add(y);

        DepthFirstSearchNode<String> z = new DepthFirstSearchNode<>();
        z.setColor(Color.BLACK);
        z.setDiscoveryTime(10);
        z.setFinishTime(11);
        predecessor = sampleGraph.getVertices().stream().filter(vertex -> vertex.getId().equals("w")).findFirst();
        z.setPredecessor(predecessor);
        expectedNodes.add(z);

        return expectedNodes;
    }

    private ArrayList<List<Edge<String>>> createSampleGraphExpectedEdges() {
        ArrayList<List<Edge<String>>> expectedEdges = new ArrayList<List<Edge<String>>>();

        Vertex<String> u = sampleGraph.getVertices().get(0);
        Vertex<String> v = sampleGraph.getVertices().get(1);
        Vertex<String> w = sampleGraph.getVertices().get(2);
        Vertex<String> x = sampleGraph.getVertices().get(3);
        Vertex<String> y = sampleGraph.getVertices().get(4);
        Vertex<String> z = sampleGraph.getVertices().get(5);

        List<Edge<String>> nodeEdges = new LinkedList<>();
        Edge<String> edge = new Edge<String>(u, v);
        edge.setClassification(EdgeClassification.TREE);
        nodeEdges.add(edge);
        edge = new Edge<>(u, x);
        edge.setClassification(EdgeClassification.FORWARD_OR_CROSS);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        nodeEdges = new LinkedList<>();
        edge = new Edge<>(v, y);
        edge.setClassification(EdgeClassification.TREE);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        nodeEdges = new LinkedList<>();
        edge = new Edge<>(w, z);
        edge.setClassification(EdgeClassification.TREE);
        nodeEdges.add(edge);
        edge = new Edge<>(w, y);
        edge.setClassification(EdgeClassification.FORWARD_OR_CROSS);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        nodeEdges = new LinkedList<>();
        edge = new Edge<>(x, v);
        edge.setClassification(EdgeClassification.BACK);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        nodeEdges = new LinkedList<>();
        edge = new Edge<>(y, x);
        edge.setClassification(EdgeClassification.TREE);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        nodeEdges = new LinkedList<>();
        edge = new Edge<>(z, z);
        edge.setClassification(EdgeClassification.BACK);
        nodeEdges.add(edge);
        expectedEdges.add(nodeEdges);

        return expectedEdges;
    }

    private void assertDepthFirstSearchNodes(Graph<String> graph, ArrayList<DepthFirstSearchNode<String>> expectedNodes) {
       assertEquals(expectedNodes.size(), graph.getVertices().size());
       for (int i = 0; i < expectedNodes.size(); i++) {
           assertEquals( expectedNodes.get(i), graph.getVertices().get(i).getDfsNode(), "DSF Node not equal for Vertex " + graph.getVertices().get(i).getId());
       }
    }

    private void assertDepthFirstSearchEdges(Graph<String> graph, ArrayList<List<Edge<String>>> expectedEdges) {
        assertEquals(expectedEdges.size(), graph.getVertices().size());
        for (int i = 0; i < expectedEdges.size(); i++) {
            List<Edge<String>> expectedEdgesForVertex = expectedEdges.get(i);
            List<Edge<String>> actualEdgesForVertex = graph.getVertices().get(i).getEdges();
            assertEquals(expectedEdgesForVertex.size(), actualEdgesForVertex.size(), "Edges not equal for Vertex " + graph.getVertices().get(i).getId());
            for(int j = 0; j < expectedEdgesForVertex.size(); j++) {
                Edge<String> expectedEdge = expectedEdgesForVertex.get(j);
                assertEquals(expectedEdge.getClassification(), actualEdgesForVertex.get(j).getClassification(),
                        "Edge classification not equal for edge (" + expectedEdge.getFrom().getId() + ", " + expectedEdge.getTo().getId() + ")");
            }
        }
    }
}