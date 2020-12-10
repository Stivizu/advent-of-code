package com.stevecorp.codingcontest.adventofcode._2020._01;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        final List<Integer> input = parseFile("2020/01/input.txt", Integer::parseInt);
        for (int element1Index = 0; element1Index < input.size() - 1; element1Index++) {
            for (int element2Index = element1Index + 1; element2Index < input.size(); element2Index++) {
                final int element1 = input.get(element1Index);
                final int element2 = input.get(element2Index);
                final int elementSum = element1 + element2;
                if (elementSum == 2020) {
                    final int elementMultiplication = element1 * element2;
                    System.out.println("The two elements (" + element1 + ", " +  element2 + ") multiplied give as result: " + elementMultiplication);
                    System.exit(1);
                }
            }
        }
    }
}
