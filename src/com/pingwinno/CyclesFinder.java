package com.pingwinno;

import java.util.Stack;

/*
  The CyclesFinder class represents a data type for
  determining whether an edge-weighted digraph has a directed cycle.
 */
public class CyclesFinder {

    private boolean[] marked;             // marked[v] = has vertex v been marked?
    private Edge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<Edge> cycle;    // directed cycle (or null if no such cycle)

    // Determines what the edge-weighted digraph has a directed cycle and finds one if cycle exists.

    public CyclesFinder(Graph G) {
        marked = new boolean[G.getVerticesNum()];
        onStack = new boolean[G.getVerticesNum()];
        edgeTo = new Edge[G.getVerticesNum()];
        for (int v = 0; v < G.getVerticesNum(); v++)
            if (!marked[v]) dfs(G, v);

        // check that digraph has a cycle
        assert check();
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(Graph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.to();

            // short circuit if directed cycle found
            if (cycle != null) return;

                // if found new vertex continue
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<>();

                Edge f = e;
                while (f.from() != w) {
                    cycle.push(f);
                    f = edgeTo[f.from()];
                }
                cycle.push(f);

                return;
            }
        }

        onStack[v] = false;
    }

    //true if the edge-weighted digraph has one. false otherwise
    public boolean hasCycle() {
        return cycle != null;
    }

    //Returns a directed cycle if the graph has one.
    public Iterable<Edge> cycle() {
        return cycle;
    }


    // Confirm that graph is acyclic or has a directed cycle
    private boolean check() {

        // graph is cyclic
        if (hasCycle()) {
            // verify cycle
            Edge first = null, last = null;
            for (Edge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.to() != e.from()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }


}