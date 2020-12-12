package com.stevecorp.codingcontest.adventofcode._2020._11;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, Boolean[]> MAPPER = inputLine ->
            inputLine.chars()
                    .mapToObj(inputCharacter -> inputCharacter == 46 ? null : false)
                    .toArray(Boolean[]::new);

    public static void main(final String... args) {
        final Boolean[][] seatMatrix = parseFile("2020/11/input.txt", MAPPER)
                .toArray(Boolean[][]::new);

        Set<Integer[]> toFlip;
        do {
            toFlip = new HashSet<>();
            for (int row = 0; row < seatMatrix.length; row++) {
                for (int col = 0; col < seatMatrix[0].length; col++) {
                    final Boolean currentState = seatMatrix[row][col];
                    if (currentState != null) {
                        final int numberOfVisibleOccupiedSeats = numberOfVisibleOccupiedSeats(seatMatrix, row, col);
                        if ((!currentState && numberOfVisibleOccupiedSeats == 0)
                                || (currentState && numberOfVisibleOccupiedSeats >= 4)) {
                            toFlip.add(new Integer[]{row, col});
                        }
                    }
                }
            }
            for (final Integer[] flip : toFlip) {
                seatMatrix[flip[0]][flip[1]] = !seatMatrix[flip[0]][flip[1]];
            }
        } while (!toFlip.isEmpty());

        int numberOfOccupiedSeats = 0;
        for (int row = 0; row < seatMatrix.length; row++) {
            for (int col = 0; col < seatMatrix[0].length; col++) {
                final Boolean seatStatus = seatMatrix[row][col];
                if (seatStatus != null && seatStatus) {
                    numberOfOccupiedSeats++;
                }
            }
        }

        System.out.println("Number of occupied seats: " + numberOfOccupiedSeats);
    }

    private static int numberOfVisibleOccupiedSeats(final Boolean[][] seatMatrix, final int baseRow, final int baseCol) {
        int numberOfVisibleOccupiedSeats = 0;
        for (int row = baseRow - 1; row <= baseRow + 1; row++) {
            for (int col = baseCol - 1; col <= baseCol + 1; col++) {
                if ((row >= 0 && row < seatMatrix.length)
                        && (col >= 0 && col < seatMatrix[0].length)
                        && !(row == baseRow && col == baseCol)) {
                    final Boolean seatStatus = seatMatrix[row][col];
                    if (seatStatus != null && seatStatus) {
                        numberOfVisibleOccupiedSeats++;
                    }
                }
            }
        }
        return numberOfVisibleOccupiedSeats;
    }
}