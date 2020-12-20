package com.stevecorp.codingcontest.adventofcode._2020._20;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    private static final char UP = 'U';
    private static final char RIGHT = 'R';
    private static final char DOWN = 'D';
    private static final char LEFT = 'L';

    public static void main(String[] args) {
        final List<String> input = parseFileRaw("2020/20/input.txt");

        input.add(0, "");
        input.add(input.size(), "");
        final List<Integer> tileSeparationIndexes = IntStream.range(0, input.size())
                .filter(index -> "".equals(input.get(index)))
                .boxed()
                .collect(Collectors.toList());

        final List<Tile> tiles = new ArrayList<>();
        for (int tileSeparationIndex = 1; tileSeparationIndex < tileSeparationIndexes.size(); tileSeparationIndex++) {
            tiles.add(new Tile(input.subList(tileSeparationIndexes.get(tileSeparationIndex - 1) + 1, tileSeparationIndexes.get(tileSeparationIndex))));
        }

        final Map<String, Integer> edgeOccurrenceCounter = new HashMap<>();
        for (final Tile tile : tiles) {
            for (final Boolean[] edgePossibility : tile.getAllEdgePossibilities()) {
                final String edgePossibilityAsString = toString(edgePossibility);
                edgeOccurrenceCounter.putIfAbsent(edgePossibilityAsString, 0);
                edgeOccurrenceCounter.computeIfPresent(edgePossibilityAsString, (key, value) -> value + 1);
            }
        }
        
        final Set<String> uniqueEdges = edgeOccurrenceCounter.entrySet().stream()
                .filter(edgeOccurrence -> edgeOccurrence.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        final List<Tile> cornerPieces = new ArrayList<>();
        for (final Tile tile : tiles) {
            final Boolean[] upEdge = tile.getEdge(UP);
            final Boolean[] rightEdge = tile.getEdge(RIGHT);
            final Boolean[] downEdge = tile.getEdge(DOWN);
            final Boolean[] leftEdge = tile.getEdge(LEFT);

            int matchCounter = 0;
            matchCounter += uniqueEdges.contains(toString(upEdge)) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(tile.reverseEdge(upEdge))) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(rightEdge)) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(tile.reverseEdge(rightEdge))) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(downEdge)) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(tile.reverseEdge(downEdge))) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(leftEdge)) ? 1 : 0;
            matchCounter += uniqueEdges.contains(toString(tile.reverseEdge(leftEdge))) ? 1 : 0;

            if (matchCounter == 4) {
                cornerPieces.add(tile);
            }
        }

        final long solution = cornerPieces.stream()
                .map(tile -> (long) tile.id)
                .reduce(1L, (a, b) -> a * b);
        System.out.println("The multiplication result of the corner pieces is: " + solution);

    }

    private static String toString(final Boolean[] input) {
        return Arrays.stream(input).map(inputElement -> inputElement ? "1" : "0").collect(Collectors.joining());
    }

    private static final class Tile {
        final int id;
        Boolean[][] grid;

        public Tile(final List<String> inputEntries) {
            this.id = Integer.parseInt(inputEntries.get(0).replaceAll("[^\\d]", ""));
            this.grid = inputEntries.subList(1, inputEntries.size()).stream()
                    .map(inputEntry -> inputEntry.chars().mapToObj(inputChar -> '#' == inputChar).toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new);
        }

        public List<Boolean[]> getAllEdgePossibilities() {
            final Boolean[] upEdge = getEdge(UP);
            final Boolean[] rightEdge = getEdge(RIGHT);
            final Boolean[] downEdge = getEdge(DOWN);
            final Boolean[] leftEdge = getEdge(LEFT);
            return List.of(upEdge, reverseEdge(upEdge), rightEdge, reverseEdge(rightEdge), downEdge, reverseEdge(downEdge), leftEdge, reverseEdge(leftEdge));
        }

        private Boolean[] getEdge(final char side) {
            return switch (side) {
                case UP -> grid[0];
                case RIGHT -> {
                    final Boolean[] output = new Boolean[grid.length];
                    for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
                        output[rowIndex] = grid[rowIndex][grid.length - 1];
                    }
                    yield output;
                }
                case DOWN -> grid[grid.length - 1];
                case LEFT -> {
                    final Boolean[] output = new Boolean[grid.length];
                    for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
                        output[rowIndex] = grid[rowIndex][0];
                    }
                    yield output;
                }
                default -> throw new IllegalStateException();
            };
        }

        private Boolean[] reverseEdge(final Boolean[] edge) {
            final Boolean[] output = ArrayUtils.clone(edge);
            ArrayUtils.reverse(output);
            return output;
        }
    }
}
