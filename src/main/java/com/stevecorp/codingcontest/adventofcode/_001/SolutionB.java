package com.stevecorp.codingcontest.adventofcode._001;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class SolutionB {

    public static void main(final String... args) {
        final int goal = 2020;
        final List<Integer> input = parseFile("challenges/001/input.txt", Integer::parseInt);
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                for (int k = 0; k < input.size(); k++) {
                    final int sum = input.get(i) + input.get(j) + input.get(k);
                    if (sum == goal) {
                        System.out.println(input.get(i) + " * " + input.get(j) + " * " + input.get(k) + " = " + (input.get(i) * input.get(j) * input.get(k)));
                        System.exit(1);
                    }
                }
            }
        }
    }
}
