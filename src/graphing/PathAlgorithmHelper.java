package graphing;

import java.util.HashMap;
import java.util.HashSet;

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
}
