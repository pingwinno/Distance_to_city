package com.pingwinno;

import java.util.LinkedList;
import java.util.Queue;

//The BellmanFordSP class represents a data type for finding shortest path. It uses Queue-based Bellman-Ford algorithm.
public class BellmanFordSP {
    private long[] distTo;               // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;         // edgeTo[v] = last edge on shortest s->v path
    private boolean[] onQueue;             // onQueue[v] = is v currently on the queue?
    private Queue<Integer> queue;          // queue of vertices to relax
    private int cost;                      // number of calls to relax()
    private Iterable<Edge> cycle;  // negative cycle (or null if no such cycle)

    //Computes a shortest paths from to every other vertex the graph
    public BellmanFordSP(Graph graph, int sourceVertex) {
        distTo = new long[graph.getVerticesNum()];
        edgeTo = new Edge[graph.getVerticesNum()];
        onQueue = new boolean[graph.getVerticesNum()];
        /*init the graph
        Long.MAX_VALUE used for mark non processed units yet
        Source vertex inits with 0
         */
        for (int v = 0; v < graph.getVerticesNum(); v++)
            distTo[v] = Long.MAX_VALUE / 2;
        distTo[sourceVertex] = 0;

        // Bellman-Ford algorithm
        queue = new LinkedList<>();
        queue.add(sourceVertex);
        onQueue[sourceVertex] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.poll();
            onQueue[v] = false;
            relax(graph, v);
        }

        assert check(graph, sourceVertex);
    }

    // relax vertex v and put other endpoints on queue if changed
    private void relax(Graph graph, int v) {

        for (Edge e : graph.adj(v)) {
            int w = e.to();
            //if path from s to w > path from s to v + e, so uses last one
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.add(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % graph.getVerticesNum() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;  // found a negative cycle
            }
        }
    }

    /*
     return true if there is a negative cycle reachable from the
      source vertex sourceVertex, and  false otherwise
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /*
      Returns a negative cycle reachable from the source vertex or null
      if there is no such cycle.
     */
    public Iterable<Edge> negativeCycle() {
        return cycle;
    }

    // finding a cycle in predecessor graph
    private void findNegativeCycle() {
        int V = edgeTo.length;
        Graph shortestPathsTree = new Graph(V);
        for (Edge edge : edgeTo)
            if (edge != null)
                shortestPathsTree.addEdge(edge);

        CyclesFinder finder = new CyclesFinder(shortestPathsTree);
        cycle = finder.cycle();
    }

    /*
      Returns the length of a shortest path from the source vertex to vertex v.
     the destination vertex
     */
    public long distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }

    /*
     check optimality conditions:
      there exists a negative cycle reachable from s
         or
      for all edges e = v->w:            distTo[w] <= distTo[v] + e.weight()
      for all edges e = v->w on the shortest-paths tree: distTo[w] == distTo[v] + e.weight()

    */
    private boolean check(Graph G, int s) {

        // has a negative cycle
        if (hasNegativeCycle()) {
            double weight = 0.0;
            for (Edge e : negativeCycle()) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }

        // no negative cycle reachable from source
        else {

            // check that distTo[v] and edgeTo[v] are consistent
            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.getVerticesNum(); v++) {
                if (v == s) continue;
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distTo[] and edgeTo[] inconsistent");
                    return false;
                }
            }

            // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
            for (int v = 0; v < G.getVerticesNum(); v++) {
                for (Edge e : G.adj(v)) {
                    int w = e.to();
                    if (distTo[v] + e.weight() < distTo[w]) {
                        System.err.println("edge " + e + " not relaxed");
                        return false;
                    }
                }
            }

            // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
            for (int w = 0; w < G.getVerticesNum(); w++) {
                if (edgeTo[w] == null) continue;
                Edge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) return false;
                if (distTo[v] + e.weight() != distTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
        }

        System.out.println("Satisfies optimality conditions");
        System.out.println();
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < getVerticesNum}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }


}