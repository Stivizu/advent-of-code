package com.stevecorp.codingcontest.adventofcode._2020._22;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    public static void main(String[] args) {
        final List<String> input = parseFileRaw("2020/22/input.txt");
        final int inputSeparationIndex = input.indexOf("");

        final LinkedList<Integer> playerCards = input.subList(1, inputSeparationIndex).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
        final LinkedList<Integer> opponentCards = input.subList(inputSeparationIndex + 2, input.size()).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!playerCards.isEmpty() && !opponentCards.isEmpty()) {

            final int playerCard = playerCards.removeFirst();
            final int opponentCard = opponentCards.removeFirst();

            final LinkedList<Integer> targetDeck = playerCard > opponentCard ? playerCards : opponentCards;
            final List<Integer> ordered = List.of(playerCard, opponentCard).stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            targetDeck.addLast(ordered.get(0));
            targetDeck.addLast(ordered.get(1));

        }

        final LinkedList<Integer> winningDeck = opponentCards.isEmpty() ? playerCards : opponentCards;
        final long score = IntStream.rangeClosed(1, 50)
                .mapToLong(index -> (long) index * winningDeck.removeLast())
                .sum();

        System.out.println("The winning player's score is: " + score);
    }
}
