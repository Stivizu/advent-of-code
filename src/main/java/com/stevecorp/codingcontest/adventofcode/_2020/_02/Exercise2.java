package com.stevecorp.codingcontest.adventofcode._2020._02;

import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, Boolean> MAPPER = inputLine -> {
        final String[] inputLineEntries = inputLine.split("[\\W]");
        final int index1 = Integer.parseInt(inputLineEntries[0]) - 1;
        final int index2 = Integer.parseInt(inputLineEntries[1]) - 1;
        final char targetLetter = inputLineEntries[2].charAt(0);
        final String password = inputLineEntries[4];
        final boolean index1MatchesTargetLetter = password.charAt(index1) == targetLetter;
        final boolean index2MatchesTargetLetter = password.charAt(index2) == targetLetter;
        return index1MatchesTargetLetter ^ index2MatchesTargetLetter;
    };

    public static void main(final String... args) {
        final long numberOfValidPasswords = parseFile("2020/02/input.txt", MAPPER).stream()
                .filter(in -> in)
                .count();
        System.out.println("Number of valid passwords: " + numberOfValidPasswords);
    }
}