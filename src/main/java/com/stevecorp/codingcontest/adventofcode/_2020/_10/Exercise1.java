package com.stevecorp.codingcontest.adventofcode._2020._10;

import java.util.List;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        final List<Integer> input = parseFile("2020/10/input.txt", Integer::parseInt).stream()
                .sorted()
                .collect(Collectors.toList());
        input.add(0, 0);
        input.add(input.get(input.size() - 1) + 3);
        int oneJoltDifferences = 0;
        int threeJoltDifferences = 0;
        for (int inputIndex = 0; inputIndex < input.size() - 1; inputIndex++) {
            final int joltageDifference = input.get(inputIndex + 1) - input.get(inputIndex);
            if (joltageDifference == 1) {
                oneJoltDifferences++;
            }
            if (joltageDifference == 3) {
                threeJoltDifferences++;
            }
        }
        System.out.println(oneJoltDifferences * threeJoltDifferences);
    }
}