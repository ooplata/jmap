package graphing;

import java.util.ArrayList;

public final class ShortestPathHelper {
    public static ShortestPathResult getShortestPath(ShortestPathAlgorithm algorithm, Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem destination) {
        return switch (algorithm) {
            case DIJKSTRA -> getShortestPathDijkstra(graph, start, destination);
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

    private static ShortestPathResult getShortestPathFloydWarshall(Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem destination) {
        //TODO: Make this actually use the Floyd Warshall algorithm
        return getShortestPathDijkstra(graph, start, destination);
    }
}
