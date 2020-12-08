package com.stevecorp.codingcontest.adventofcode._008;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static com.stevecorp.codingcontest.adventofcode._008.Exercise1.exercise1;

public class Exercise2 {

    private static final int ACC = 'a';
    private static final int JMP = 'j';
    private static final int NOP = 'n';

    private static final List<Integer> JMP_OR_NOP = List.of(JMP, NOP);

    private static final Function<String, Integer[]> MAPPER = inputLine -> {
        final String[] splitInput = inputLine.split(" ");
        return new Integer[] {
                (int) splitInput[0].charAt(0),
                Integer.parseInt(splitInput[1])
        };
    };

    public static void main(final String... args) {
        final List<Integer[]> input = parseFile("challenges/008/input.txt", MAPPER);
        final List<Integer> jmpsAndNopsFromExercise1Path = exercise1(input, false).stream()
                .filter(index -> JMP_OR_NOP.contains(input.get(index)[0]))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("Indexes of JMP and NOP operations from exercise 1 (reverse numerically sorted):" + jmpsAndNopsFromExercise1Path);
        final Integer indexToFlip = jmpsAndNopsFromExercise1Path.stream()
                .filter(index -> canReachTheEndWithFlippedNopOrJmp(input, index))
                .findFirst().orElseThrow();
        final Integer[] elementToFlip = input.get(indexToFlip);
        System.out.println("Element to flip (at index " + indexToFlip + "): " + elementToFlip[0] + ", " + elementToFlip[1]);
        calculateAccumulatorValue(input, indexToFlip);
    }

    public static boolean canReachTheEndWithFlippedNopOrJmp(final List<Integer[]> input, final int startingIndex) {
        final Set<Integer> visitedIndexes = new HashSet<>();
        int currentIndex = startingIndex;
        while (!visitedIndexes.contains(currentIndex)) {
            visitedIndexes.add(currentIndex);
            final Integer[] currentElement = input.get(currentIndex);
            if ((currentIndex == startingIndex && currentElement[0] == NOP) || (currentIndex != startingIndex && currentElement[0] == JMP)) {
                currentIndex += currentElement[1] - 1;
            }
            currentIndex++;
            if (currentIndex >= input.size()) {
                return true;
            }
        }
        return false;
    }

    public static void calculateAccumulatorValue(final List<Integer[]> input, final Integer flippedIndex) {
        final Set<Integer> visitedIndexes = new HashSet<>();
        int accumulatorValue = 0;
        int currentIndex = 0;
        while (!visitedIndexes.contains(currentIndex)) {
            visitedIndexes.add(currentIndex);
            final Integer[] currentElement = input.get(currentIndex);
            switch (currentElement[0]) {
                case ACC -> accumulatorValue += currentElement[1];
                case JMP -> {
                    if (currentIndex != flippedIndex) {
                        currentIndex += currentElement[1] - 1;
                    }
                }
                case NOP -> {
                    if (currentIndex == flippedIndex) {
                        currentIndex += currentElement[1] - 1;
                    }
                }
            }
            currentIndex++;
            if (currentIndex >= input.size()) {
                break;
            }
        }
        System.out.println("Accumulator value: " + accumulatorValue);
    }
}