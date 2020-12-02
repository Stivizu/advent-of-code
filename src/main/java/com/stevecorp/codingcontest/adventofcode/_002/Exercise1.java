package com.stevecorp.codingcontest.adventofcode._002;

import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static java.lang.Integer.parseInt;

public class Exercise1 {

    private static final Function<String, Boolean> MAPPER = s -> {
        final String[] pa = s.split("[\\W]");
        final char po = pa[2].charAt(0);
        final long c = pa[4].chars().filter(in -> in == po).count();
        return c >= parseInt(pa[0]) && c <= parseInt(pa[1]);
    };

    public static void main(final String... args) {
        System.out.println(parseFile("challenges/002/input.txt", MAPPER).stream()
                .filter(in -> in)
                .count());
    }
}