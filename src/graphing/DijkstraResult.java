package graphing;

import java.util.HashMap;

public record DijkstraResult(HashMap<SolarSystemItem, SolarSystemItem> items, HashMap<SolarSystemItem, Long> distances) {
}
