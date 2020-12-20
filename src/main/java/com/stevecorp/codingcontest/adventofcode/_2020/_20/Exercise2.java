package com.stevecorp.codingcontest.adventofcode._2020._20;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    private static final char UP = 'U';
    private static final char RIGHT = 'R';
    private static final char DOWN = 'D';
    private static final char LEFT = 'L';

    private static final char HORIZONTAL = 'H';
    private static final char VERTICAL = 'V';

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
        final List<Tile> edgePieces = new ArrayList<>();
        final List<Tile> centerPieces = new ArrayList<>();

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

            if (matchCounter == 2) {
                edgePieces.add(tile);
            } else if (matchCounter == 4) {
                cornerPieces.add(tile);
            } else {
                centerPieces.add(tile);
            }
        }

        final int puzzleSizeTiles = (int) Math.sqrt(tiles.size());

        final Set<Integer> usedIds = new HashSet<>();
        final Tile[][] puzzle = new Tile[puzzleSizeTiles][puzzleSizeTiles];
        puzzle[0][0] = fitTileForTopLeftOfPuzzle(cornerPieces.get(0), uniqueEdges, usedIds);

        // TOP ROW
        for (int colIndex = 1; colIndex < puzzleSizeTiles - 1; colIndex++) {
            final Boolean[] edgeToCheck = puzzle[0][colIndex - 1].getEdge(RIGHT);
            puzzle[0][colIndex] = findMatchingPiece(edgeToCheck, edgePieces, usedIds, LEFT);
        }

        // TOP RIGHT CORNER
        final Boolean[] trEdgeToCheck = puzzle[0][puzzleSizeTiles - 2].getEdge(RIGHT);
        puzzle[0][puzzleSizeTiles - 1] =  findMatchingPiece(trEdgeToCheck, cornerPieces, usedIds, LEFT);

        // LEFT COL
        for (int rowIndex = 1; rowIndex < puzzleSizeTiles - 1; rowIndex++) {
            final Boolean[] edgeToCheck = puzzle[rowIndex - 1][0].getEdge(DOWN);
            puzzle[rowIndex][0] = findMatchingPiece(edgeToCheck, edgePieces, usedIds, UP);
        }

        // BOTTOM LEFT CORNER
        final Boolean[] blEdgeToCheck = puzzle[puzzleSizeTiles - 2][0].getEdge(DOWN);
        puzzle[puzzleSizeTiles - 1][0] =  findMatchingPiece(blEdgeToCheck, cornerPieces, usedIds, UP);

        // RIGHT COL
        for (int rowIndex = 1; rowIndex < puzzleSizeTiles - 1; rowIndex++) {
            final Boolean[] edgeToCheck = puzzle[rowIndex - 1][puzzleSizeTiles - 1].getEdge(DOWN);
            puzzle[rowIndex][puzzleSizeTiles - 1] = findMatchingPiece(edgeToCheck, edgePieces, usedIds, UP);
        }

        // BOTTOM RIGHT CORNER
        final Boolean[] brEdgeToCheck = puzzle[puzzleSizeTiles - 2][puzzleSizeTiles - 1].getEdge(DOWN);
        puzzle[puzzleSizeTiles - 1][puzzleSizeTiles - 1] =  findMatchingPiece(brEdgeToCheck, cornerPieces, usedIds, UP);

        // BOTTOM ROW
        for (int colIndex = 1; colIndex < puzzleSizeTiles - 1; colIndex++) {
            final Boolean[] edgeToCheck = puzzle[puzzleSizeTiles - 1][colIndex - 1].getEdge(RIGHT);
            puzzle[puzzleSizeTiles - 1][colIndex] = findMatchingPiece(edgeToCheck, edgePieces, usedIds, LEFT);
        }

        // CENTER
        for (int rowIndex = 1; rowIndex < puzzleSizeTiles - 1; rowIndex++) {
            for (int colIndex = 1; colIndex < puzzleSizeTiles - 1; colIndex++) {
                final Boolean[] edgeToCheck = puzzle[rowIndex][colIndex - 1].getEdge(RIGHT);
                puzzle[rowIndex][colIndex] = findMatchingPiece(edgeToCheck, centerPieces, usedIds, LEFT);
            }
        }

        tiles.forEach(Tile::removeBorder);

        final int tileSizeBits = tiles.get(0).grid.length;
        final int puzzleSizeBits = puzzleSizeTiles * tileSizeBits;

        Boolean[][] actualPuzzle = new Boolean[puzzleSizeBits][puzzleSizeBits];
        for (int rowIndex = 0; rowIndex < puzzleSizeBits; rowIndex++) {
            for (int colIndex = 0; colIndex < puzzleSizeBits; colIndex++) {
                actualPuzzle[rowIndex][colIndex] = puzzle[rowIndex / tileSizeBits][colIndex / tileSizeBits]
                        .grid[rowIndex % tileSizeBits][colIndex % tileSizeBits];
            }
        }

        final List<String> patternString = List.of(
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   ");
        final Boolean[][] patternGrid = patternString.stream()
                .map(patternLine -> patternLine.chars().mapToObj(patternChar -> '#' == patternChar).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        final Boolean[][] adjustedPuzzle = adjustPuzzleTomatchPattern(actualPuzzle, patternGrid);

        // TODO: puzzle is finished but sea monster is not found???

        System.out.println();

    }

    private static String toString(final Boolean[] input) {
        return Arrays.stream(input).map(inputElement -> inputElement ? "1" : "0").collect(Collectors.joining());
    }

    private static Tile fitTileForTopLeftOfPuzzle(final Tile tileToFit, final Set<String> uniqueEdges, final Set<Integer> usedIds) {
        usedIds.add(tileToFit.id);
        int validEdges;
        do {
            validEdges = 0;
            tileToFit.rotate();
            validEdges += uniqueEdges.contains(toString(tileToFit.getEdge(UP))) ? 1 : 0;
            validEdges += uniqueEdges.contains(toString(tileToFit.getEdge(LEFT))) ? 1 : 0;
        } while (validEdges != 2);
        return tileToFit;
    }

    private static Tile findMatchingPiece(final Boolean[] edgeToCheck, final List<Tile> input, final Set<Integer> usedIds, final char sideToVerify) {
        for (final Tile edgePiece : input) {
            boolean match = false;
            for (final Boolean[] edgePossibility : edgePiece.getAllEdgePossibilities()) {
                if (!usedIds.contains(edgePiece.id) && Objects.deepEquals(edgePossibility, edgeToCheck)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                usedIds.add(edgePiece.id);
                boolean stop = false;
                for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
                    edgePiece.rotate();
                    if (Objects.deepEquals(edgePiece.getEdge(sideToVerify), edgeToCheck)) {
                        stop = true;
                        break;
                    }
                }
                if (!stop) {
                    edgePiece.flip(HORIZONTAL);
                    for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
                        edgePiece.rotate();
                        if (Objects.deepEquals(edgePiece.getEdge(sideToVerify), edgeToCheck)) {
                            stop = true;
                            break;
                        }
                    }
                    if (!stop) {
                        edgePiece.flip(HORIZONTAL).flip(VERTICAL);
                        for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
                            edgePiece.rotate();
                            if (Objects.deepEquals(edgePiece.getEdge(sideToVerify), edgeToCheck)) {
                                break;
                            }
                        }
                    }
                }
                return edgePiece;
            }
        }
        throw new IllegalStateException();
    }

    private static Boolean[][] adjustPuzzleTomatchPattern(final Boolean[][] puzzle, final Boolean[][] pattern) {
        Boolean[][] puzzleModified = rotate(puzzle);
        for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
            puzzleModified = rotate(puzzleModified);
            if (patternIsPresentSlidingWindow(puzzleModified, pattern)) {
                return puzzleModified;
            }
        }
        puzzleModified = flip(puzzle, HORIZONTAL);
        for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
            puzzleModified = rotate(puzzleModified);
            if (patternIsPresentSlidingWindow(puzzleModified, pattern)) {
                return puzzleModified;
            }
        }
        puzzleModified = flip(puzzle, VERTICAL);
        for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
            puzzleModified = rotate(puzzleModified);
            if (patternIsPresentSlidingWindow(puzzleModified, pattern)) {
                return puzzleModified;
            }
        }
        puzzleModified = flip(puzzle, VERTICAL);
        puzzleModified = flip(puzzleModified, HORIZONTAL);
        for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
            puzzleModified = rotate(puzzleModified);
            if (patternIsPresentSlidingWindow(puzzleModified, pattern)) {
                return puzzleModified;
            }
        }
        throw new RuntimeException();
    }

    private static Boolean[][] rotate(final Boolean[][] input) {
        final Boolean[][] rotatedInput = new Boolean[input[0].length][input.length];
        for (int rowIndex = 0; rowIndex < rotatedInput.length; ++rowIndex) {
            for (int colIndex = 0; colIndex < rotatedInput[0].length; ++colIndex) {
                rotatedInput[rowIndex][colIndex] = input[input.length - colIndex - 1][rowIndex];
            }
        }
        return rotatedInput;
    }

    private static Boolean[][] flip(final Boolean[][] input, char direction) {
        final Boolean[][] flippedInput = new Boolean[input.length][input[0].length];
        if (direction == HORIZONTAL) {
            for (int rowIndex = 0; rowIndex < input.length; rowIndex++) {
                for (int colIndex = 0; colIndex < input[0].length; colIndex++) {
                    flippedInput[rowIndex][colIndex] = input[rowIndex][input[0].length - 1 - colIndex];
                }
            }
        } else {
            for (int colIndex = 0; colIndex < input[0].length; colIndex++) {
                for (int rowIndex = 0; rowIndex < input.length; rowIndex++) {
                    flippedInput[rowIndex][colIndex] = input[input.length - 1 - rowIndex][colIndex];
                }
            }
        }
        return flippedInput;
    }

    private static boolean patternIsPresentSlidingWindow(final Boolean[][] puzzle, final Boolean[][] pattern) {
        for (int rowIndex = 0; rowIndex < puzzle.length - pattern.length; rowIndex++) {
            for (int colIndex = 0; colIndex < puzzle.length - pattern[0].length; colIndex++) {
                for (int patternRowIndex = 0; patternRowIndex < pattern.length; patternRowIndex++) {
                    for (int patternColIndex = 0; patternColIndex < pattern[0].length; patternColIndex++) {
                        if (pattern[patternRowIndex][patternColIndex] && !puzzle[rowIndex + patternRowIndex][colIndex + patternColIndex]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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

        private void rotate() {
            final Boolean[][] rotatedGrid = new Boolean[grid.length][grid.length];
            for (int rowIndex = 0; rowIndex < grid.length; ++rowIndex) {
                for (int colIndex = 0; colIndex < grid.length; ++colIndex) {
                    rotatedGrid[rowIndex][colIndex] = grid[grid.length - colIndex - 1][rowIndex];
                }
            }
            this.grid = rotatedGrid;
        }

        private Tile flip(final char direction) {
            if (direction == HORIZONTAL) {
                for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
                    for (int colIndex = 0; colIndex < grid.length / 2; colIndex++) {
                        final Boolean tmp = grid[rowIndex][colIndex];
                        grid[rowIndex][colIndex] = grid[rowIndex][grid.length - 1 - colIndex];
                        grid[rowIndex][grid.length - 1 - colIndex] = tmp;
                    }
                }
            } else {
                for (int colIndex = 0; colIndex < grid.length; colIndex++) {
                    for (int rowIndex = 0; rowIndex < grid.length / 2; rowIndex++) {
                        final Boolean tmp = grid[rowIndex][colIndex];
                        grid[rowIndex][colIndex] = grid[grid.length - 1 - rowIndex][colIndex];
                        grid[grid.length - 1 - rowIndex][colIndex] = tmp;
                    }
                }
            }
            return this;
        }

        private void removeBorder() {
            final Boolean[][] gridWithoutBorder = new Boolean[grid.length - 2][grid.length - 2];
            for (int rowIndex = 1; rowIndex < grid.length - 1; rowIndex++) {
                for (int colIndex = 1; colIndex < grid.length - 1; colIndex++) {
                    gridWithoutBorder[rowIndex - 1][colIndex - 1] = grid[rowIndex][colIndex];
                }
            }
            this.grid = gridWithoutBorder;
        }
    }
}
