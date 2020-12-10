package com.stevecorp.codingcontest.adventofcode._2020._03;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final char TREE = '#';

    public static void main(final String... args) {
        final List<String> input = parseFile("2020/03/input.txt", String::valueOf);
        final int inputLineLength = input.get(0).length();
        final int[][] slopes =  new int[][] {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        final int[] numberOfTreesEncounteredPerSlope = new int[slopes.length];
        for (int slopeIndex = 0; slopeIndex < slopes.length; slopeIndex++) {
            final int horizontalStepSize = slopes[slopeIndex][0];
            final int verticalStepSize = slopes[slopeIndex][1];
            int currentHorizontalPosition = 0;
            int currentVerticalposition = 0;
            int numberOfEncounteredTrees = 0;
            do {
                final char currentPosition = input.get(currentVerticalposition).charAt(currentHorizontalPosition);
                if (currentPosition == TREE) {
                    numberOfEncounteredTrees++;
                }
                currentHorizontalPosition += horizontalStepSize;
                currentVerticalposition += verticalStepSize;
                final boolean isHorizontalPositionOutOfBounds = currentHorizontalPosition >= inputLineLength;
                if (isHorizontalPositionOutOfBounds) {
                    currentHorizontalPosition = currentHorizontalPosition - inputLineLength;
                }
            } while (currentVerticalposition < input.size());
            numberOfTreesEncounteredPerSlope[slopeIndex] = numberOfEncounteredTrees;
        }
        long slopeMultiplication = 1;
        for (int numberOfTreesEncountered : numberOfTreesEncounteredPerSlope) {
            slopeMultiplication *= numberOfTreesEncountered;
        }
        System.out.println("Number of encountered trees per slope multiplied: " + slopeMultiplication);
    }
}
