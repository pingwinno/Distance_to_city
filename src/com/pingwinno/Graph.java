package com.pingwinno;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

// The Graph class represents a data type for storing edge-weighted graph.
public class Graph {

    private final int VERTICES_NUM;                // number of vertices in this graph
    private int EDGES_NUM;                      // number of edges in this graph
    private Set<Edge>[] adjacencyList;    // adjacencyList[v] = adjacency list for vertex v

    //Initializes an empty graph with VERTICES_NUM vertices and 0 edges.

    @SuppressWarnings("unchecked")
    public Graph(int VERTICES_NUM) {
        if (VERTICES_NUM < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.VERTICES_NUM = VERTICES_NUM;
        this.EDGES_NUM = 0;

        adjacencyList = (Set<Edge>[]) new HashSet[VERTICES_NUM];
        for (int vertex = 0; vertex < VERTICES_NUM; vertex++)
            adjacencyList[vertex] = new HashSet<>();
    }

    public int getVerticesNum() {
        return VERTICES_NUM;
    }

    public int getEdgesNum() {
        return EDGES_NUM;
    }

    // throw an IllegalArgumentException unless 0 <= vertex < VERTICES_NUM
    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= VERTICES_NUM)
            throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (VERTICES_NUM - 1));
    }

    //Adds one edge to graph
    public void addEdge(Edge e) {

        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adjacencyList[v].add(e);
        EDGES_NUM++;
    }

    //Adds list of edges to graph
    public void addEdges(List<Edge> edgeList) {
        for (Edge edge : edgeList) {
            int v = edge.from();
            int w = edge.to();
            validateVertex(v);
            validateVertex(w);
            adjacencyList[v].add(edge);
            EDGES_NUM++;
        }
    }

    //Returns the directed edges incident from vertex.
    public Iterable<Edge> adj(int vertex) {
        validateVertex(vertex);
        return adjacencyList[vertex];
    }

}