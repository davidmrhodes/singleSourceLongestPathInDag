# Introduction

This repository implements a simple class DirectedAcyclicGraph (DAG) that has a function to 
calculate the longest path in a DAG from a given vertex.

This is a variation on the classic problem of single source shortest path.  Of course in this case we want the longest path. 
The implementation of this algorithm is based on the description in the book "Introduction to Algorithms" by Cormen et al. 

The algorithm takes advantage of the fact that a DAG can be topologically sorted. After the topological sort relaxation can be 
used to calculate the longest path.  The algorithm is O(V+E) complexity.

# Running the tests

Code was developed on a Rocky Linux 9.3 system but should work on any system that has maven (version 3.8.6) and java (version 17) 
installed. To build and run the tests perform the following command:

```bash
mvn clean install
```

# Code

The algorithm uses Depth First Search (DFS) to perform a topological sort of the DAG.  DFS is implemented in the class DepthFirstSearch.
The vertex information needed to perform the DSF is stored in the class Vertex.  That member is in the Vertex class is  dfsNode.

The DepthFirstSearchNode class stores information needed to perform the DFS.  It stores the following

* Color - the color of the node in the DFS tree
* Parent - the parent of the node in the DFS tree
* Distance - the distance from the source node to the node in the DFS tree

It also classifies the edges as tree, back or forward/cross edges. If the graph contains back edges then the graph is not acyclic. 
The topological sort will throw an exception if DFS contains back edges.

The DirectedAcyclicGraph class stores the graph information.  It stores the following

* Vertices - the vertices in the graph
* Edges - the edges in the graph
* Source - the source vertex for the longest path calculation

It uses an adjacency list representation of the graph.

It also contains a function to calculate the longest path from the source vertex.
The LongestPath class calculates the longest path in a DAG from a given node.  It uses the 
DepthFirstSearch class to perform the topological sort.  It then uses the DFS information to calculate the longest path 
using relaxation.
