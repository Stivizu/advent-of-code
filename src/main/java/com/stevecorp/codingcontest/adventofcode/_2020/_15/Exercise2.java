package com.stevecorp.codingcontest.adventofcode._2020._15;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, Long[]> MAPPER = inputLine ->
            Arrays.stream(inputLine.split(","))
                    .map(Long::valueOf)
                    .toArray(Long[]::new);

    public static void main(final String... args) {
        final Long[] input = parseFile("2020/15/input.txt", MAPPER).get(0);

        final Map<Long, PreviousOccurrences> previousOccurrences = new HashMap<>();
        IntStream.range(0, input.length)
                .forEach(inputIndex -> previousOccurrences.put(input[inputIndex], new PreviousOccurrences(inputIndex + 1L)));

        long previousInput = input[input.length - 1];
        long currentIndex = input.length + 1;

        do {

            final long nextInput = previousOccurrences.get(previousInput).nextInput();
            if (previousOccurrences.containsKey(nextInput)) {
                previousOccurrences.get(nextInput).add(currentIndex);
            } else {
                previousOccurrences.put(nextInput, new PreviousOccurrences(currentIndex));
            }

            previousInput = nextInput;
            currentIndex++;

        } while (currentIndex <= 30000000);

        System.out.println("The 30000000th spoken number is: " + previousInput);

    }

    private static final class PreviousOccurrences {
        Deque<Long> occurrences = new ArrayDeque<>();

        public PreviousOccurrences(final Long initialInput) {
            occurrences.addFirst(initialInput);
        }

        public long nextInput() {
            return occurrences.size() < 2
                    ? 0
                    : occurrences.getFirst() - occurrences.getLast();
        }

        public void add(final Long newInput) {
            occurrences.addFirst(newInput);
            if (occurrences.size() > 2) {
                occurrences.removeLast();
            }
        }
    }
}