package graphing;

import assets.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class SolarSystemHelper {
    private static volatile String[] lines;

    public static long getDistanceBetweenItems(int startIndex, int destinationIndex) {
        if (lines == null) getLines();

        var split = lines[startIndex].split(",");
        return Long.parseLong(split[destinationIndex]);
    }

    private static void getLines() {
        try (var stream = Resources.getResourceAsStream("Planets.txt")) {
            assert stream != null;

            var streamReader = new BufferedReader(new InputStreamReader(stream));
            lines = (String[]) streamReader.lines().toArray();
        } catch (IOException ignored) {
        }
    }
}
