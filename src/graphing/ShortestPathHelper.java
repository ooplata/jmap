package graphing;

import java.util.ArrayList;

public final class ShortestPathHelper {
    public static ShortestPathResult getShortestPath(ShortestPathAlgorithm algorithm, Graph graph, SolarSystemItem start, SolarSystemItem destination) {
        return switch (algorithm) {
            case DIJKSTRA -> getShortestPathDijkstra(graph.vertices, start, destination);
            case FLOYD_WARSHALL -> getShortestPathFloydWarshall(graph, start, destination);
        };
    }

    public static ShortestPathResult getShortestPathFromResult(DijkstraResult result, SolarSystemItem destination) {
        var items = new ArrayList<SolarSystemItem>();
        long distance = 0;

        var curr = destination;
        while (curr != null) {
            items.add(0, curr);

            distance += result.distances().get(curr);
            curr = result.items().get(curr);
        }

        return new ShortestPathResult(items, distance);
    }

    private static ShortestPathResult getShortestPathDijkstra(Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem destination) {
        var result = PathAlgorithmHelper.getDijkstraResult(graph, start);
        return getShortestPathFromResult(result, destination);
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
