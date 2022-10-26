package jmap.graphing.paths;

import jmap.graphing.SolarSystemItem;

public record FloydWarshallResult(long[][] costMatrix, SolarSystemItem[][] pathMatrix) {
}
