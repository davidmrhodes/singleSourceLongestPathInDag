package gov.dmr.interview.dag;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DirectedAcyclicGraphTest extends BaseTest {

    @Test
    public void testTopologicalSortClothingGraph() {

        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        List<Vertex<String>> sortedVertices = graph.sort();

        List<String> expectedSortedIds = List.of("socks", "undershorts", "pants", "shoes", "watch", "shirt", "belt", "tie", "jacket");
        assertPath(expectedSortedIds, sortedVertices);
    }

    @Test
    public void testTopologicalSampleGraph() {
        DirectedAcyclicGraph<String> graph = graphCreator.createSimpleSampleDag();
        List<Vertex<String>> sortedVertices = graph.sort();

        List<String> expectedSortedIds = List.of("r", "s", "t", "u", "v",  "x");
        assertPath(expectedSortedIds, sortedVertices);
    }

    @Test
    public void testLongestPathSampleGraphAtStartVertex() {
        DirectedAcyclicGraph<String> graph = graphCreator.createSimpleSampleDag();
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("s").get());

        List<String> expectedLongestPathIds = List.of("s", "t", "u", "v",  "x");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathClothingGraphAtStartVertex() {
        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("undershorts").get());

        List<String> expectedLongestPathIds = List.of("undershorts", "pants", "belt", "jacket");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathClothingGraphAtMiddleVertex() {
        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("pants").get());

        List<String> expectedLongestPathIds = List.of("pants", "belt", "jacket");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathClothingGraphAtLeafVertex() {
        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("jacket").get());

        List<String> expectedLongestPathIds = List.of("jacket");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathClothingGraphAtIsolatedVertex() {
        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("watch").get());

        List<String> expectedLongestPathIds = List.of("watch");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathWith2DisconnectedSubgraphs() {
        DirectedAcyclicGraph<String> graph1 = graphCreator.createClothingDag();
        DirectedAcyclicGraph<String> graph2 = graphCreator.createSimpleSampleDag();
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        vertices.addAll(graph1.getVertices());
        vertices.addAll(graph2.getVertices());
        DirectedAcyclicGraph<String> graph = new DirectedAcyclicGraph<>(vertices);
        List<Vertex<String>> longestPath = graph.dagLongestPath(graph.getVertexById("undershorts").get());

        List<String> expectedLongestPathIds = List.of("undershorts", "pants", "belt", "jacket");
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLongestPathLinearGraph() {
        DirectedAcyclicGraph<Integer> graph = graphCreator.createLinearGraph(10);

        List<Vertex<Integer>> longestPath = graph.dagLongestPath(graph.getVertexById(0).get());

        List<Integer> expectedLongestPathIds = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            expectedLongestPathIds.add(i);
        }
        assertPath(expectedLongestPathIds, longestPath);
    }


    @Test
    public void testLongestPathWithEmptyGraph() {
        DirectedAcyclicGraph<String> graph = new DirectedAcyclicGraph<>(new ArrayList<>());
        try {
            List<Vertex<String>> longestPath = graph.dagLongestPath(new Vertex<>("test"));
            fail("Expected dagLongestPath to throw exception if graph is empty");
        } catch (RuntimeException e) {
            assertEquals("No vertex with id test", e.getMessage());
        }
    }

    @Test
    public void testLongestPathClothingGraphVertexNotInGraph() {
        DirectedAcyclicGraph<String> graph = graphCreator.createClothingDag();
        try {
           List<Vertex<String>> longestPath = graph.dagLongestPath(new Vertex<>("nonexistent"));
           fail("Expected dagLongestPath to throw exception if vertex not in graph");
        } catch (RuntimeException e) {
           assertEquals("No vertex with id nonexistent", e.getMessage());
        }
    }

    @Test
    public void testLongestPathWithCyclesInGraph() {
        Graph<String> graph = graphCreator.createSampleGraphWithCycles();
        DirectedAcyclicGraph<String> dag = new DirectedAcyclicGraph<>(graph.getVertices());

        try {
            List<Vertex<String>> longestPath = dag.dagLongestPath(dag.getVertexById("u").get());
            fail("Expected dagLongestPath to throw exception if cycles in graph");
        } catch (RuntimeException e) {
            assertEquals("Graph is cyclic", e.getMessage());
        }
    }

}