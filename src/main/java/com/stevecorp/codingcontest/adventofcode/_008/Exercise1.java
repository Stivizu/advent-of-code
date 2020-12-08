package com.stevecorp.codingcontest.adventofcode._008;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final int ACC = 'a';
    private static final int JMP = 'j';

    private static final Function<String, Integer[]> MAPPER = inputLine -> {
        final String[] splitInput = inputLine.split(" ");
        return new Integer[] {
                (int) splitInput[0].charAt(0),
                Integer.parseInt(splitInput[1])
        };
    };

    public static void main(final String... args) {
        exercise1(parseFile("challenges/008/input.txt", MAPPER), true);
    }

    public static Set<Integer> exercise1(final List<Integer[]> input, final boolean printAccumulatorValue) {
        final Set<Integer> visitedIndexes = new HashSet<>();
        int accumulatorValue = 0;
        int currentIndex = 0;
        while (!visitedIndexes.contains(currentIndex)) {
            visitedIndexes.add(currentIndex);
            final Integer[] currentElement = input.get(currentIndex);
            switch (currentElement[0]) {
                case ACC -> accumulatorValue += currentElement[1];
                case JMP -> currentIndex += currentElement[1] - 1;
            }
            currentIndex++;
        }
        if (printAccumulatorValue) {
            System.out.println("Accumulator value: " + accumulatorValue);
        }
        return visitedIndexes;
    }
}