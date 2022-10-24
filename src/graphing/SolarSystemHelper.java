package graphing;

import assets.Resources;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class SolarSystemHelper {
    private static Object[] bounds;
    private static Object[] distances;

    public static Rectangle getItemBounds(int itemIndex) {
        if (bounds == null) getBounds();

        var split = bounds[itemIndex].toString().split(",");

        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        int width = Integer.parseInt(split[2]);
        int height = Integer.parseInt(split[3]);

        return new Rectangle(x, y, width, height);
    }

    public static long getDistanceBetweenItems(int startIndex, int destinationIndex) {
        if (distances == null) getDistances();

        var split = distances[startIndex].toString().split(",");
        return Long.parseLong(split[destinationIndex]);
    }

    private static void getBounds() {
        try (var stream = Resources.getResourceAsStream("PlanetBounds.txt")) {
            assert stream != null;

            var streamReader = new BufferedReader(new InputStreamReader(stream));
            bounds = streamReader.lines().toArray();
        } catch (IOException ignored) {
        }
    }

    private static void getDistances() {
        try (var stream = Resources.getResourceAsStream("Planets.txt")) {
            assert stream != null;

            var streamReader = new BufferedReader(new InputStreamReader(stream));
            distances = streamReader.lines().toArray();
        } catch (IOException ignored) {
        }
    }
}
