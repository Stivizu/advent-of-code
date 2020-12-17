package com.stevecorp.codingcontest.adventofcode._2020._17;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final int NO_OF_TURNS = 6;

    private static final Function<String, Boolean[]> MAPPER = inputLine ->
            inputLine.chars()
                    .mapToObj(inputLineCharacter -> inputLineCharacter == '#')
                    .toArray(Boolean[]::new);

    public static void main(final String... args) {
        final List<Boolean[]> input = parseFile("2020/17/input.txt", MAPPER);

        final boolean[][][] cube = new boolean
                [input.get(0).length + 2 * NO_OF_TURNS]
                [input.size() + 2 * NO_OF_TURNS]
                [1 + 2 * NO_OF_TURNS];

        for (int x = 0; x < input.get(0).length; x++) {
            for (int y = 0; y < input.size(); y++) {
                cube[x + NO_OF_TURNS][y + NO_OF_TURNS][6] = input.get(y)[x];
            }
        }

        for (int iteration = 0; iteration < 6; iteration++) {

            final List<Integer[]> toFlip = new ArrayList<>();

            for (int z = 0; z < cube[0][0].length; z++) {
                for (int y = 0; y < cube[0].length; y++) {
                    for (int x = 0; x < cube.length; x++) {

                        final int numberOfAdjacentCells = getNumberOfAdjacentActiveCells(cube, x, y, z);
                        final boolean currentCellState = cube[x][y][z];
                        if (currentCellState && !(numberOfAdjacentCells == 2 || numberOfAdjacentCells == 3)) {
                            toFlip.add(new Integer[]{x, y, z});
                        } else if (!currentCellState && numberOfAdjacentCells == 3) {
                            toFlip.add(new Integer[]{x, y, z});
                        }

                    }
                }
            }

            toFlip.forEach(tf -> cube[tf[0]][tf[1]][tf[2]] =  !cube[tf[0]][tf[1]][tf[2]]);

        }

        final int numberOfActiveCells = getNumberOfActiveCells(cube);
        System.out.println("The number of active cells is: " + numberOfActiveCells);

    }

    private static int getNumberOfAdjacentActiveCells(final boolean[][][] cube, final int baseX, final int baseY, final int baseZ) {
        int numberOfAdjacentActiveCells = 0;
        for (int z = baseZ - 1; z <= baseZ + 1; z++) {
            for (int y = baseY - 1; y <= baseY + 1; y++) {
                for (int x = baseX - 1; x <= baseX + 1; x++) {
                    if (baseX == 7 && baseY == 6 && baseZ == 6 && x == 7 && y == 6 && z == 6) {
                        System.out.println();
                    }
                    if (x > 0 && x < cube.length
                            && y > 0 && y < cube[0].length
                            && z > 0 && z < cube[0][0].length
                            && !(x == baseX && y == baseY && z == baseZ)
                            && cube[x][y][z]) {
                        numberOfAdjacentActiveCells++;
                        if (numberOfAdjacentActiveCells == 4) {
                            return numberOfAdjacentActiveCells;
                        }
                    }
                }
            }
        }
        return numberOfAdjacentActiveCells;
    }

    private static int getNumberOfActiveCells(final boolean[][][] cube) {
        int numberOfActiveCells = 0;
        for (int z = 0; z < cube[0][0].length; z++) {
            for (int y = 0; y < cube[0].length; y++) {
                for (int x = 0; x < cube.length; x++) {
                    if (cube[x][y][z]) {
                        numberOfActiveCells++;
                    }
                }
            }
        }
        return numberOfActiveCells;
    }
}
