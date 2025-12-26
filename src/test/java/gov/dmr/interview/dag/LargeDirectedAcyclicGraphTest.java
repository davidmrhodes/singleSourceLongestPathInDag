package gov.dmr.interview.dag;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LargeDirectedAcyclicGraphTest extends BaseTest {

    @Test
    public void testLargeLinearDag() {
        int largeSize = 100000;
        DirectedAcyclicGraph<Integer> graph = graphCreator.createLinearGraph(largeSize);

        List<Vertex<Integer>> longestPath = graph.dagLongestPath(graph.getVertexById(0).get());

        List<Integer> expectedLongestPathIds = new ArrayList<>();
        for (int i = 0; i < largeSize; i++) {
            expectedLongestPathIds.add(i);
        }
        assertPath(expectedLongestPathIds, longestPath);
    }
}
