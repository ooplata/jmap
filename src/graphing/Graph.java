package graphing;

import java.util.ArrayList;

public final class Graph {
    public final ArrayList<SolarSystemItem> vertices = new ArrayList<>();
    public final ArrayList<Edge<SolarSystemItem>> edges = new ArrayList<>();

    public SolarSystemItem addConnection(SolarSystemItem parent, SolarSystemItem vertex) {
        if (parent != null) {
            parent.addConnection(vertex);

            long distance = vertex.getDistanceToItem(parent.index);
            var edge = new Edge<>(parent, vertex, distance);

            if (!edges.contains(edge)) {
                edges.add(edge);
            }
        }

        int listIndex = vertices.indexOf(vertex);
        if (listIndex != -1) {
            return vertices.get(listIndex);
        } else {
            vertices.add(vertex);
        }
        return vertex;
    }

    public void reset() {
        vertices.clear();
        edges.clear();
    }
}
