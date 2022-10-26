package jmap.graphing.paths;

import jmap.graphing.SolarSystemItem;

public record ShortestPathResult(Iterable<SolarSystemItem> path, long totalCost) {
}
