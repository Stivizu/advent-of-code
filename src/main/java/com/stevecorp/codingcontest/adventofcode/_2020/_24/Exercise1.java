package com.stevecorp.codingcontest.adventofcode._2020._24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Predicate<Character> IS_TWO_CHAR_COMMAND = character -> character == 's' || character == 'n';

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

        final Map<String, Integer> tileFlips = new HashMap<>();

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
            final String tileCoordinate = String.join(",", String.valueOf(cA), String.valueOf(cB));
            tileFlips.merge(tileCoordinate, 1, Integer::sum);
        }

        final long numberOfBlackTiles = tileFlips.entrySet().stream()
                .filter(tileFlip -> tileFlip.getValue() % 2 > 0)
                .count();

        System.out.println("The number of tiles with the black side up is: " + numberOfBlackTiles);
    }

    private enum Command {
        e, se, sw, w, nw, ne
    }

}
