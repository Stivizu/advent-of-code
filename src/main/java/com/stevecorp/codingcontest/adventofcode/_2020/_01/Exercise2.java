package com.stevecorp.codingcontest.adventofcode._2020._01;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    public static void main(final String... args) {
        final List<Integer> input = parseFile("2020/01/input.txt", Integer::parseInt);
        for (int element1Index = 0; element1Index < input.size() - 2; element1Index++) {
            for (int element2Index = element1Index + 1; element2Index < input.size() - 1; element2Index++) {
                for (int element3Index = element2Index + 1; element3Index < input.size(); element3Index++) {
                    final int elementSum = input.get(element1Index) + input.get(element2Index) + input.get(element3Index);
                    if (elementSum == 2020) {
                        System.out.println(input.get(element1Index) * input.get(element2Index) * input.get(element3Index));
                        System.exit(1);
                    }
                }
            }
        }
    }
}
