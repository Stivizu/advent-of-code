package com.stevecorp.codingcontest.adventofcode._2020._12;

import java.util.List;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, String[]> MAPPER =
            inputLine -> inputLine.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    public static void main(final String... args) {
        final List<String[]> input = parseFile("2020/12/input.txt", MAPPER);

        int waypointX = 10;
        int waypointY = 1;
        int shipX = 0;
        int shipY = 0;

        for (final String[] instruction : input) {

            final String key = instruction[0];
            final int value = Integer.parseInt(instruction[1]);

            switch (key) {
                case "F" -> {
                    shipX += value * waypointX;
                    shipY += value * waypointY;
                }
                case "N" -> waypointY += value;
                case "E" -> waypointX += value;
                case "S" -> waypointY -= value;
                case "W" -> waypointX -= value;
                case "L" -> {
                    for (int rep = 0; rep < value / 90; rep++) {
                        final int tmp = waypointX;
                        waypointX = waypointY;
                        waypointY = tmp;
                        waypointX = - waypointX;
                    }
                }
                case "R" -> {
                    for (int rep = 0; rep < value / 90; rep++) {
                        final int tmp = waypointX;
                        waypointX = waypointY;
                        waypointY = tmp;
                        waypointY = - waypointY;
                    }
                }
            }
        }

        System.out.println(Math.abs(shipX) + Math.abs(shipY));
    }
}