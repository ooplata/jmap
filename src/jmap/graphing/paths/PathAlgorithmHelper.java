package jmap.graphing.paths;

import jmap.graphing.Graph;
import jmap.graphing.SolarSystemItem;

public final class PathAlgorithmHelper {
    public static FloydWarshallResult getFloydWarshallResult(Graph graph) {
        int size = graph.vertices.size();

        long[][] dist = new long[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                dist[x][y] = 1171602432000000L;
            }
        }

        var path = new SolarSystemItem[size][size];
        for (var edge : graph.edges) {
            int destIndex = graph.vertices.indexOf(edge.destination());
            int startIndex = graph.vertices.indexOf(edge.start());

            dist[startIndex][destIndex] = edge.cost();
            path[startIndex][destIndex] = edge.destination();
        }

        for (var vertex : graph.vertices) {
            int index = graph.vertices.indexOf(vertex);
            dist[index][index] = 0;
            path[index][index] = vertex;
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = path[i][k];
                    }
                }
            }
        }

        return new FloydWarshallResult(dist, path);
    }
}
