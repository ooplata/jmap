package jmap.graphing;

import java.util.Objects;

public record Edge<T>(T start, T destination, long cost) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;

        boolean sameEdge = start.equals(edge.start) && destination.equals(edge.destination);
        boolean isInverted = start.equals(edge.destination) && destination.equals(edge.start);

        return sameEdge || isInverted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, destination);
    }
}
