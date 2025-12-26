package gov.dmr.interview.dag;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LargeDirectedAcyclicGraphTest extends BaseTest {

    @Test
    public void testLargeLinearDag() {
        /*
         * This test creates a large linear directed acyclic graph with 1000 vertices and
         * verifies that the longest path algorithm correctly identifies the longest path.
         * The stack blows out with 10000 vertices. Algorithms need to be rewritten to user
         * an iterative approach.
         */
        int largeSize = 1000;
        DirectedAcyclicGraph<Integer> graph = graphCreator.createLinearGraph(largeSize);

        List<Vertex<Integer>> longestPath = graph.dagLongestPath(graph.getVertexById(0).get());

        List<Integer> expectedLongestPathIds = new ArrayList<>();
        for (int i = 0; i < largeSize; i++) {
            expectedLongestPathIds.add(i);
        }
        assertPath(expectedLongestPathIds, longestPath);
    }

    @Test
    public void testLargeWideDag() {
        int largeSize = 1000000;
        DirectedAcyclicGraph<Integer> graph = graphCreator.createWideGraph(largeSize);

        List<Vertex<Integer>> longestPath = graph.dagLongestPath(graph.getVertexById(0).get());

        List<Integer> expectedLongestPathIds = List.of(0, graph.getVertexById(largeSize - 1).get().getId());
        assertPath(expectedLongestPathIds, longestPath);
    }
}
