package graphing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class PathAlgorithmHelper {
    public static DijkstraResult getDijkstraResult(Iterable<SolarSystemItem> graph, SolarSystemItem start) {
        var distances = new HashMap<SolarSystemItem, Long>();
        var items = new HashMap<SolarSystemItem, SolarSystemItem>();

        var unmarked = new HashSet<SolarSystemItem>();
        for (var item : graph) {
            distances.put(item, Long.MAX_VALUE);
            items.put(item, null);
            unmarked.add(item);
        }

        distances.put(start, 0L);
        while (!unmarked.isEmpty()) {
            long min = Long.MAX_VALUE;
            SolarSystemItem parent = null;

            for (var item : unmarked) {
                if (distances.get(item) < min) {
                    parent = item;
                }
            }

            unmarked.remove(parent);
            assert parent != null;

            for (var item : parent.connections) {
                if (!unmarked.contains(item)) continue;

                long alt = distances.get(parent) + parent.getDistanceToItem(item.index);
                if (alt < distances.get(item)) {
                    distances.put(item, alt);
                    items.put(item, parent);
                }
            }
        }

        return new DijkstraResult(items, distances);
    }

    public static long[][] getDistanceMatrix(List<SolarSystemItem> graph) {
        int size = graph.size();
        long[][] dist = new long[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y) {
                    dist[x][y] = 0;
                } else {
                    dist[x][y] = Long.MAX_VALUE;
                }
            }
        }

        for (int index = 0; index < size; index++) {
            var origin = graph.get(index);
            for (var conn : origin.connections) {
                dist[index][graph.indexOf(conn)] = origin.getDistanceToItem(conn.index);
            }
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }
}