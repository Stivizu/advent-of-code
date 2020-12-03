package com.stevecorp.codingcontest.adventofcode._001;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        final List<Integer> in = parseFile("challenges/001/input.txt", Integer::parseInt);
        for (int i = 0; i < in.size(); i++) {
            for (int j = 0; j < in.size(); j++) {
                final int s = in.get(i) + in.get(j);
                if (s == 2020) {
                    System.out.println(in.get(i) * in.get(j));
                    System.exit(1);
                }
            }
        }
    }
}
