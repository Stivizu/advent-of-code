package com.stevecorp.codingcontest.adventofcode._2020._10;

import java.util.List;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    public static void main(final String... args) {
        final List<Long> input = parseFile("2020/10/input.txt", Long::parseLong).stream()
                .sorted()
                .collect(Collectors.toList());
        input.add(0, 0L);
        input.add(input.get(input.size() - 1) + 3);
        int doublers = 0;
        int septuplers = 0;
        for (int inputIndex = 1; inputIndex < input.size() - 1; inputIndex++) {
            final Long elementThreeIndexesBack = (inputIndex >= 3)
                    ? input.get(inputIndex - 3)
                    : Long.MIN_VALUE;
            if (input.get(inputIndex + 1) - elementThreeIndexesBack == 4) {
                septuplers += 1;
                doublers -= 2;
            } else if (input.get(inputIndex + 1) - input.get(inputIndex - 1) == 2) {
                doublers += 1;
            }
        }
        final double numberOfPossibilities = Math.pow(2, doublers) * Math.pow(7, septuplers);
        System.out.printf("%.0f", numberOfPossibilities);
    }
}