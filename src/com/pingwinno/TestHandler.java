package com.pingwinno;

import java.util.*;

public class TestHandler {

    public static List<Long> start(Scanner in){
        //test cases cycle
        List<Long> results = new LinkedList<>();
        int testsNum = in.nextInt();
        for (int testNum = 0; testNum < testsNum; testNum++) {
            if (testNum > 0) {
                in.nextLine();
            }
            Map<String, Integer> cities = new HashMap<>();
            List<Edge> edges = new ArrayList<>();
            int citiesNum = in.nextInt();
            //Parse cities and distances to graph.
            for (int cityNum = 1; cityNum <= citiesNum; cityNum++) {
                cities.put(in.next(), cityNum);
                int pathsNum = in.nextInt();
                for (int pathNum = 0; pathNum < pathsNum; pathNum++) {
                    edges.add(new Edge(cityNum, in.nextInt(), in.nextInt()));
                }
            }
            //Get number of required paths and find it.
            int pathsToFind = in.nextInt();
            for (int pathNum = 0; pathNum < pathsToFind; pathNum++) {
                Graph weightedDigraph = new Graph(edges.size());
                weightedDigraph.addEdges(edges);
                BellmanFordSP bellmanFordSP = new BellmanFordSP(weightedDigraph, cities.get(in.next()));
                results.add(bellmanFordSP.distTo(cities.get(in.next())));
            }
        }
        return results;
    }

}
