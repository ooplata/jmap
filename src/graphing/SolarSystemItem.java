package graphing;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class SolarSystemItem {
    public final Set<SolarSystemItem> connections = new HashSet<>();
    public final int index;


    public SolarSystemItem(int index) {
        this.index = index;
    }

    public boolean addConnection(SolarSystemItem item) {
        if (item.index == index) return false;

        // Prevent short-circuit on return statement
        boolean addedThis = item.connections.add(this);
        return connections.add(item) || addedThis;
    }

    public Rectangle getBounds() {
        return SolarSystemHelper.getItemBounds(index);
    }

    public long getDistanceToItem(int itemIndex) {
        return SolarSystemHelper.getDistanceBetweenItems(index, itemIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolarSystemItem that = (SolarSystemItem) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
