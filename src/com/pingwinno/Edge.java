package com.pingwinno;

import java.util.Objects;


// The BellmanFordSP class represents a data type for storing directed edge.
public class Edge {
    private final int v;
    private final int w;
    private final int weight;

    ///Initialize edge with params
    public Edge(int v, int w, int weight) {
        if (v < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    // return the tail of the edge
    public int from() {
        return v;
    }

    //return the head vertex of the edge
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the weight of the edge
     */
    public int weight() {
        return weight;
    }

    //Override of equals method for using edge object in sets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge that = (Edge) o;
        return v == that.v &&
                w == that.w &&
                weight == that.weight;
    }
    //Override of hashCode method for using edge object in hash sets
    @Override
    public int hashCode() {
        return Objects.hash(v, w, weight);
    }

}
