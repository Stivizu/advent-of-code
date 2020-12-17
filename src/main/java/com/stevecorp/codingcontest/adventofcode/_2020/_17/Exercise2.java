package com.stevecorp.codingcontest.adventofcode._2020._17;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final int NO_OF_TURNS = 6;

    private static final Function<String, Boolean[]> MAPPER = inputLine ->
            inputLine.chars()
                    .mapToObj(inputLineCharacter -> inputLineCharacter == '#')
                    .toArray(Boolean[]::new);

    public static void main(final String... args) {
        final List<Boolean[]> input = parseFile("2020/17/input.txt", MAPPER);

        final boolean[][][][] tesseract = new boolean
                [input.get(0).length + 2 * NO_OF_TURNS]
                [input.size() + 2 * NO_OF_TURNS]
                [1 + 2 * NO_OF_TURNS]
                [1 + 2 * NO_OF_TURNS];

        for (int x = 0; x < input.get(0).length; x++) {
            for (int y = 0; y < input.size(); y++) {
                tesseract[x + NO_OF_TURNS][y + NO_OF_TURNS][6][6] = input.get(y)[x];
            }
        }

        for (int iteration = 0; iteration < 6; iteration++) {

            final List<Integer[]> toFlip = new ArrayList<>();

            for (int w = 0; w < tesseract[0][0][0].length; w++) {
                for (int z = 0; z < tesseract[0][0].length; z++) {
                    for (int y = 0; y < tesseract[0].length; y++) {
                        for (int x = 0; x < tesseract.length; x++) {

                            final int numberOfAdjacentCells = getNumberOfAdjacentActiveCells(tesseract, x, y, z, w);
                            final boolean currentCellState = tesseract[x][y][z][w];
                            if (currentCellState && !(numberOfAdjacentCells == 2 || numberOfAdjacentCells == 3)) {
                                toFlip.add(new Integer[]{x, y, z, w});
                            } else if (!currentCellState && numberOfAdjacentCells == 3) {
                                toFlip.add(new Integer[]{x, y, z, w});
                            }

                        }
                    }
                }
            }

            toFlip.forEach(tf -> tesseract[tf[0]][tf[1]][tf[2]][tf[3]] =  !tesseract[tf[0]][tf[1]][tf[2]][tf[3]]);

        }

        final int numberOfActiveCells = getNumberOfActiveCells(tesseract);
        System.out.println("The number of active cells is: " + numberOfActiveCells);

    }

    private static int getNumberOfAdjacentActiveCells(final boolean[][][][] tesseract, final int baseX, final int baseY, final int baseZ, final int baseW) {
        int numberOfAdjacentActiveCells = 0;
        for (int w = baseW - 1; w <= baseW + 1; w++) {
            for (int z = baseZ - 1; z <= baseZ + 1; z++) {
                for (int y = baseY - 1; y <= baseY + 1; y++) {
                    for (int x = baseX - 1; x <= baseX + 1; x++) {
                        if (baseX == 7 && baseY == 6 && baseZ == 6 && x == 7 && y == 6 && z == 6) {
                            System.out.println();
                        }
                        if (x > 0 && x < tesseract.length
                                && y > 0 && y < tesseract[0].length
                                && z > 0 && z < tesseract[0][0].length
                                && w > 0 && w < tesseract[0][0][0].length
                                && !(x == baseX && y == baseY && z == baseZ && w == baseW)
                                && tesseract[x][y][z][w]) {
                            numberOfAdjacentActiveCells++;
                            if (numberOfAdjacentActiveCells == 4) {
                                return numberOfAdjacentActiveCells;
                            }
                        }
                    }
                }
            }
        }
        return numberOfAdjacentActiveCells;
    }

    private static int getNumberOfActiveCells(final boolean[][][][] tesseract) {
        int numberOfActiveCells = 0;
        for (int w = 0; w < tesseract[0][0][0].length; w++) {
            for (int z = 0; z < tesseract[0][0].length; z++) {
                for (int y = 0; y < tesseract[0].length; y++) {
                    for (int x = 0; x < tesseract.length; x++) {
                        if (tesseract[x][y][z][w]) {
                            numberOfActiveCells++;
                        }
                    }
                }
            }
        }
        return numberOfActiveCells;
    }
}
