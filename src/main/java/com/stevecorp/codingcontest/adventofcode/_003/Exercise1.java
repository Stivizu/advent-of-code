package com.stevecorp.codingcontest.adventofcode._003;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        int cy = 0;
        int st = 3;
        int so = 0;
        final List<String> in = parseFile("challenges/003/input.txt", String::valueOf);
        for (final String s : in) {
            so += s.charAt(cy) == '#' ? 1 : 0;
            cy += st;
            cy = cy >= s.length() ? cy - s.length() : cy;
        }
        System.out.println(so);
    }
}