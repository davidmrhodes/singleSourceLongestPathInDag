package gov.dmr.interview.dag;

import java.util.ArrayList;

public class TestGraphCreator {

    public DirectedAcyclicGraph<String> createSimpleSampleDag() {
        ArrayList<Vertex<String>> vertices = new ArrayList<>();

        Vertex<String> r = new Vertex<>("r");
        vertices.add(r);

        Vertex<String> t = new Vertex<>("t");
        vertices.add(t);

        Vertex<String> s = new Vertex<>("s");
        vertices.add(s);

        Vertex<String> v = new Vertex<>("v");
        vertices.add(v);

        Vertex<String> u = new Vertex<>("u");
        vertices.add(u);

        Vertex<String> x = new Vertex<>("x");
        vertices.add(x);

        r.addNeighbor(s);
        r.addNeighbor(t);

        t.addNeighbor(v);
        t.addNeighbor(u);

        s.addNeighbor(t);
        s.addNeighbor(u);

        u.addNeighbor(v);
        u.addNeighbor(x);

        v.addNeighbor(x);

        return new DirectedAcyclicGraph<>(vertices);
    }

    public DirectedAcyclicGraph<String> createClothingDag() {

        ArrayList<Vertex<String>> vertices = new ArrayList<>();

        Vertex<String> shirt = new Vertex<>("shirt");
        vertices.add(shirt);

        Vertex<String> tie = new Vertex<>("tie");
        vertices.add(tie);

        Vertex<String> jacket = new Vertex<>("jacket");
        vertices.add(jacket);

        Vertex<String> watch = new Vertex<>("watch");
        vertices.add(watch);

        Vertex<String> undershorts = new Vertex<>("undershorts");
        vertices.add(undershorts);

        Vertex<String> pants = new Vertex<>("pants");
        vertices.add(pants);

        Vertex<String> belt = new Vertex<>("belt");
        vertices.add(belt);

        Vertex<String> socks = new Vertex<>("socks");
        vertices.add(socks);

        Vertex<String> shoes = new Vertex<>("shoes");
        vertices.add(shoes);

        undershorts.addNeighbor(pants);
        undershorts.addNeighbor(shoes);

        pants.addNeighbor(belt);
        pants.addNeighbor(shoes);

        belt.addNeighbor(jacket);

        shirt.addNeighbor(tie);
        shirt.addNeighbor(belt);

        tie.addNeighbor(jacket);

        socks.addNeighbor(shoes);

        return new DirectedAcyclicGraph<>(vertices);
    }

    public Graph<String> createSampleGraphWithCycles() {
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        Vertex<String> u = new Vertex<>("u");
        vertices.add(u);
        Vertex<String> v = new Vertex<>("v");
        vertices.add(v);
        Vertex<String> w = new Vertex<>("w");
        vertices.add(w);
        Vertex<String> x = new Vertex<>("x");
        vertices.add(x);
        Vertex<String> y = new Vertex<>("y");
        vertices.add(y);
        Vertex<String> z = new Vertex<>("z");
        vertices.add(z);

        u.addNeighbor(v);
        u.addNeighbor(x);

        v.addNeighbor(y);

        w.addNeighbor(z);
        w.addNeighbor(y);

        x.addNeighbor(v);

        y.addNeighbor(x);

        z.addNeighbor(z);
        return new Graph<>(vertices);
    }

    public DirectedAcyclicGraph<Integer> createLinearGraph(int size) {
        ArrayList<Vertex<Integer>> vertices = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            vertices.add(new Vertex<>(i));
        }

        for(int i = 0; i < size-1; i++) {
            vertices.get(i).addNeighbor(vertices.get(i + 1));
        }
        return new DirectedAcyclicGraph<>(vertices);
    }

    public DirectedAcyclicGraph<Integer> createWideGraph(int size) {
        ArrayList<Vertex<Integer>> vertices = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            vertices.add(new Vertex<>(i));
        }

        Vertex<Integer> firstVertex = vertices.get(0);
        for(int i = 1; i < size; i++) {
            firstVertex.addNeighbor(vertices.get(i));
        }
        return new DirectedAcyclicGraph<>(vertices);
    }
}
