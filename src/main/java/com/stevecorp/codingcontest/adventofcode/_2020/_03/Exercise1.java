package com.stevecorp.codingcontest.adventofcode._2020._03;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final char TREE = '#';

    public static void main(final String... args) {
        final List<String> input = parseFile("2020/03/input.txt", String::valueOf);
        int currentHorizontalPosition = 0;
        int horizontalStepSize = 3;
        int numberOfEncounteredTrees = 0;
        for (final String inputLine : input) {
            final char currentPosition = inputLine.charAt(currentHorizontalPosition);
            if (currentPosition == TREE) {
                numberOfEncounteredTrees++;
            }
            currentHorizontalPosition += horizontalStepSize;
            final boolean isHorizontalPositionOutOfBounds = currentHorizontalPosition >= inputLine.length();
            if (isHorizontalPositionOutOfBounds) {
                currentHorizontalPosition = currentHorizontalPosition - inputLine.length();
            }
        }
        System.out.println("Number of encountered trees: " + numberOfEncounteredTrees);
    }
}