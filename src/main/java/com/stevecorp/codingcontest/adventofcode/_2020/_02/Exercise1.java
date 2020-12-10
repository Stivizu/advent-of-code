package com.stevecorp.codingcontest.adventofcode._2020._02;

import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static java.lang.Integer.parseInt;

public class Exercise1 {

    private static final Function<String, Boolean> MAPPER = inputLine -> {
        final String[] inputLineEntries = inputLine.split("[\\W]");
        final int minOccurrences = Integer.parseInt(inputLineEntries[0]);
        final int maxOccurrences = Integer.parseInt(inputLineEntries[1]);
        final char targetLetter = inputLineEntries[2].charAt(0);
        final long letterCount = inputLineEntries[4].chars().filter(letter -> letter == targetLetter).count();
        return letterCount >= minOccurrences && letterCount <= maxOccurrences;
    };

    public static void main(final String... args) {
        final long numberOfValidPasswords = parseFile("2020/02/input.txt", MAPPER).stream()
                .filter(in -> in)
                .count();
        System.out.println("Number of valid passwords: " + numberOfValidPasswords);
    }
}