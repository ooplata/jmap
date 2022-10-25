package graphing;

import java.util.ArrayList;

public final class ShortestPathHelper {
    public static ShortestPathResult getShortestPath(ShortestPathAlgorithm algorithm, Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem end) {
        return switch (algorithm) {
            case DIJKSTRA -> getShortestPathDijkstra(graph, start, end);
            case FLOYD_WARSHALL -> getShortestPathFloydWarshall(graph, start, end);
        };
    }

    private static ShortestPathResult getShortestPathDijkstra(Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem end) {
        var result = DijkstraHelper.getResultForGraph(graph, start);
        var items = new ArrayList<SolarSystemItem>();

        long distance = 0;
        var curr = end;
        while (curr != null) {
            items.add(0, curr);

            distance += result.distances().get(curr);
            curr = result.items().get(curr);
        }

        return new ShortestPathResult(items, distance);
    }

    private static ShortestPathResult getShortestPathFloydWarshall(Iterable<SolarSystemItem> graph, SolarSystemItem start, SolarSystemItem end) {
        //TODO: Make this actually use the Floyd Warshall algorithm
        return getShortestPathDijkstra(graph, start, end);
    }
}
