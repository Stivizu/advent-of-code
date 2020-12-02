package com.stevecorp.codingcontest.adventofcode._002;

import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static java.lang.Integer.parseInt;

public class Exercise2 {

    private static final Function<String, Boolean> MAPPER = s -> {
        final String[] pa = s.split("[\\W]");
        final char po = pa[2].charAt(0);
        final String in = pa[4];
        return in.charAt(parseInt(pa[0]) - 1) == po ^ in.charAt(parseInt(pa[1]) - 1) == po;
    };

    public static void main(final String... args) {
        System.out.println(parseFile("challenges/002/input.txt", MAPPER).stream()
                .filter(in -> in)
                .count());
    }
}