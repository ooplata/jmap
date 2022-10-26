package jmap.graphing.paths;

import jmap.graphing.Graph;
import jmap.graphing.SolarSystemItem;

import java.util.ArrayList;

public final class ShortestPathHelper {
    public static ShortestPathResult getShortestPath(Graph graph, SolarSystemItem start, SolarSystemItem destination) {
        return getShortestPathFloydWarshall(graph, start, destination);
    }

    private static ShortestPathResult getShortestPathFloydWarshall(Graph graph, SolarSystemItem start, SolarSystemItem destination) {
        var result = PathAlgorithmHelper.getFloydWarshallResult(graph);
        var items = new ArrayList<SolarSystemItem>();

        var costs = result.costMatrix();
        var path = result.pathMatrix();

        long cost = 0;

        int startIndex = graph.vertices.indexOf(start);
        int destIndex = graph.vertices.indexOf(destination);

        if (path[startIndex][destIndex] != null) {
            var curr = start;
            items.add(curr);
            cost = costs[startIndex][destIndex];

            while (curr != destination) {
                int index = graph.vertices.indexOf(curr);
                curr = path[index][destIndex];

                items.add(curr);
            }
        }

        return new ShortestPathResult(items, cost);
    }
}
