package com.stevecorp.codingcontest.adventofcode._2020._24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Predicate<Character> IS_TWO_CHAR_COMMAND = character -> character == 's' || character == 'n';

    private static final List<Integer[]> ADJACENT_TILE_DIRECTIONS = List.of(
            new Integer[] {1, 0},
            new Integer[] {0, 1},
            new Integer[] {-1, 1},
            new Integer[] {-1, 0},
            new Integer[] {0, -1},
            new Integer[] {1, -1}
    );

    private static final Function<String, List<Command>> MAPPER = inputLine -> {
        final List<Command> commands = new ArrayList<>();
        int index = 0;
        while (index < inputLine.length()) {
            final int intermediary = IS_TWO_CHAR_COMMAND.test(inputLine.charAt(index)) ? 2 : 1;
            commands.add(Command.valueOf(inputLine.substring(index, index + intermediary)));
            index += intermediary;
        }
        return commands;
    };

    public static void main(final String... args) {
        final List<List<Command>> input = parseFile("2020/24/input.txt", MAPPER);

        final Map<Coordinate, Integer> tileFlips = new HashMap<>();

        for (final List<Command> commands : input) {
            int cA = 0;
            int cB = 0;
            for (final Command command : commands) {
                switch (command) {
                    case e -> cA++;
                    case se -> cB++;
                    case sw -> { cA--; cB++; }
                    case w -> cA--;
                    case nw -> cB--;
                    case ne -> { cA++; cB--; }
                }
            }
            tileFlips.merge(new Coordinate(cA, cB), 1, Integer::sum);
        }

        Set<Coordinate> blackTiles = tileFlips.entrySet().stream()
                .filter(tileFlip -> tileFlip.getValue() % 2 > 0)
                .map(Entry::getKey)
                .collect(Collectors.toSet());

        for (int iteration = 0; iteration < 100; iteration++) {
            final Map<Coordinate, Integer> intermediary = new HashMap<>();
            blackTiles.stream()
                    .peek(blackTile -> intermediary.merge(blackTile, 0, (o, n) -> o))
                    .forEach(blackTile -> ADJACENT_TILE_DIRECTIONS
                            .forEach(direction -> intermediary.merge(new Coordinate(blackTile.cA + direction[0], blackTile.cB + direction[1]), 1, Integer::sum)));
            final Set<Coordinate> newBlackTiles = new HashSet<>();
            for (final Entry<Coordinate, Integer> intermediaryEntry : intermediary.entrySet()) {
                final Coordinate coordinate = intermediaryEntry.getKey();
                final int adjacencyCount = intermediaryEntry.getValue();
                if ((blackTiles.contains(coordinate) && (adjacencyCount == 1 || adjacencyCount == 2)) || adjacencyCount == 2) {
                    newBlackTiles.add(coordinate);
                }
            }
            blackTiles = newBlackTiles;
        }

        System.out.println(blackTiles.size());
    }

    private enum Command {
        e, se, sw, w, nw, ne
    }

    private static final class Coordinate {
        int cA;
        int cB;

        public Coordinate(final int cA, final int cB) {
            this.cA = cA;
            this.cB = cB;
        }

        @Override
        public boolean equals(Object obj) {
            final Coordinate comp = (Coordinate) obj;
            return this.cA == comp.cA && this.cB == comp.cB;
        }

        @Override
        public int hashCode() {
            return 31 * cA + cB;
        }
    }

}
