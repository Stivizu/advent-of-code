package com.stevecorp.codingcontest.adventofcode._2020._12;

import java.util.List;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, String[]> MAPPER =
            inputLine -> inputLine.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    public static void main(final String... args) {
        final List<String[]> input = parseFile("2020/12/input.txt", MAPPER);

        int shipDirection = 90;
        int shipX = 0;
        int shipY = 0;

        for (final String[] instruction : input) {

            final String key = instruction[0];
            final int value = Integer.parseInt(instruction[1]);

            switch (key) {
                case "F" -> {
                    switch (shipDirection) {
                        case 0 -> shipY += value;
                        case 90 -> shipX += value;
                        case 180 -> shipY -= value;
                        case 270 -> shipX -= value;
                        default -> throw new RuntimeException();
                    }
                }
                case "N" -> shipY += value;
                case "E" -> shipX += value;
                case "S" -> shipY -= value;
                case "W" -> shipX -= value;
                case "L" -> {
                    shipDirection -= value;
                    if (shipDirection < 0) {
                        shipDirection += 360;
                    }
                }
                case "R" -> {
                    shipDirection += value;
                    if (shipDirection >= 360) {
                        shipDirection -= 360;
                    }
                }
            }
        }

        System.out.println(Math.abs(shipX) + Math.abs(shipY));
    }
}