package graphing;

import java.util.ArrayList;
import java.util.Objects;

public class Vertex {
    public final ArrayList<Edge> edges;

    public final int x;
    public final int y;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}