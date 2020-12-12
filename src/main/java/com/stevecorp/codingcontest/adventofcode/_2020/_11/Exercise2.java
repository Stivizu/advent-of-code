package com.stevecorp.codingcontest.adventofcode._2020._11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final List<Integer[]> SEAT_DIRECTION_POSSIBILITIES = List.of(
            new Integer[]{-1, 0},
            new Integer[]{-1, 1},
            new Integer[]{0, 1},
            new Integer[]{1, 1},
            new Integer[]{1, 0},
            new Integer[]{1, -1},
            new Integer[]{0, -1},
            new Integer[]{-1, -1});

    private static final Function<String, Boolean[]> MAPPER = inputLine ->
            inputLine.chars().mapToObj(inputCharacter -> inputCharacter == 46 ? null : false).toArray(Boolean[]::new);

    public static void main(final String... args) {
        final Boolean[][] seatMatrix = parseFile("2020/11/input.txt", MAPPER).toArray(Boolean[][]::new);

        Set<Integer[]> toFlip;
        do {
            toFlip = new HashSet<>();
            for (int row = 0; row < seatMatrix.length; row++) {
                for (int col = 0; col < seatMatrix[0].length; col++) {
                    final Boolean currentState = seatMatrix[row][col];
                    if (currentState != null) {
                        final long numberOfVisibleOccupiedSeats = numberOfVisibleOccupiedSeats(seatMatrix, row, col);
                        if (!currentState && numberOfVisibleOccupiedSeats == 0) {
                            toFlip.add(new Integer[]{row, col});
                        }
                        if (currentState && numberOfVisibleOccupiedSeats >= 5) {
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

    private static long numberOfVisibleOccupiedSeats(final Boolean[][] seatMatrix, final int baseRow, final int baseCol) {
        return SEAT_DIRECTION_POSSIBILITIES.stream()
                .map(possibility -> findFirstSeatStatusInDirection(seatMatrix, baseRow, baseCol, possibility[0], possibility[1]))
                .filter(status -> status)
                .count();
    }

    private static Boolean findFirstSeatStatusInDirection(final Boolean[][] seatMatrix, final int baseRow, final int baseCol,
            final int rowDirection, final int colDirection) {
        int row = baseRow + rowDirection;
        int col = baseCol + colDirection;
        while ((row >= 0 && row < seatMatrix.length) && (col >= 0 && col < seatMatrix[0].length)) {
            final Boolean seatStatus = seatMatrix[row][col];
            if (seatStatus != null) {
                return seatStatus;
            }
            row += rowDirection;
            col += colDirection;
        }
        return false;
    }
}