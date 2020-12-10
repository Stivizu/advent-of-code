package com.stevecorp.codingcontest.adventofcode._2020._05;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, Deque<Boolean>[]> MAPPER = inputLine -> new Deque[]{
            inputLine.substring(0, 7).chars()
                    .mapToObj(instructionCharacter -> instructionCharacter == 'F')
                    .collect(Collectors.toCollection(ArrayDeque::new)),
            inputLine.substring(7).chars()
                    .mapToObj(instructionCharacter -> instructionCharacter == 'L')
                    .collect(Collectors.toCollection(ArrayDeque::new))
    };

    public static void main(final String... args) {
        final List<Deque<Boolean>[]> input = parseFile("2020/05/input.txt", MAPPER);
        final List<Integer> allSeatIds = input.stream()
                .map(instructionSets -> {
                    final int frontBackPosition = binarySearch(0, 127, instructionSets[0]);
                    final int leftRightPosition = binarySearch(0, 7, instructionSets[1]);
                    return frontBackPosition * 8 + leftRightPosition;
                })
                .sorted()
                .collect(Collectors.toList());
        final int lowestSeatId = allSeatIds.get(0);
        final int highestSeatId = allSeatIds.get(allSeatIds.size() - 1);
        final int triangularNumberLowestSeatId = lowestSeatId * (lowestSeatId - 1) / 2;
        final int triangularNumberHighestSeatId = highestSeatId * (highestSeatId + 1) / 2;
        final int triangularNumberForAllSeatsCombined = triangularNumberHighestSeatId - triangularNumberLowestSeatId;
        final int allSeatIdsSum = allSeatIds.stream().mapToInt(seatId -> seatId).sum();
        final int missingSeatId = triangularNumberForAllSeatsCombined - allSeatIdsSum;
        System.out.println("your seat ID is: " + missingSeatId);
    }

    private static int binarySearch(final int lowerBound, final int upperBound, final Deque<Boolean> instructions) {
        if (instructions.isEmpty()) {
            return lowerBound;
        }
        final boolean instructionForwardOrLeft = instructions.pop();
        if (instructionForwardOrLeft) {
            return binarySearch(lowerBound, upperBound - (Math.abs(lowerBound - upperBound) + 1) / 2, instructions);
        } else {
            return binarySearch(lowerBound + (Math.abs(lowerBound - upperBound) + 1) / 2, upperBound, instructions);
        }
    }
}
