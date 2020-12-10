package com.stevecorp.codingcontest.adventofcode._2020._05;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, Deque<Boolean>[]> MAPPER = inputLine -> new Deque[]{
            inputLine.substring(0, 7).chars()
                    .mapToObj(instructionCharacter -> instructionCharacter == 'F')
                    .collect(Collectors.toCollection(ArrayDeque::new)),
            inputLine.substring(7).chars()
                    .mapToObj(instructionCharacter -> instructionCharacter == 'L')
                    .collect(Collectors.toCollection(ArrayDeque::new))
    };

    public static void main(final String... args) {
        System.out.println(parseFile("2020/05/input.txt", MAPPER).stream()
                .mapToInt(instructionSets -> {
                    final int frontBackPosition = binarySearch(0, 127, instructionSets[0]);
                    final int leftRightPosition = binarySearch(0, 7, instructionSets[1]);
                    return frontBackPosition * 8 + leftRightPosition;
                })
                .max().orElseThrow());
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