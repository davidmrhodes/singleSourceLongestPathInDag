package gov.dmr.interview.dag;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    protected TestGraphCreator graphCreator;

    public BaseTest() {
        this.graphCreator = new TestGraphCreator();
    }

    protected <T> void assertPath(List<T> expectedSortedIds, List<Vertex<T>> sortedVertices) {
        assertEquals(expectedSortedIds.size(), sortedVertices.size());
        List<T> sortedIds = sortedVertices.stream().map(Vertex::getId).collect(Collectors.toList());
        assertEquals(expectedSortedIds, sortedIds);
    }
}
