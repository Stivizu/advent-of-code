package com.stevecorp.codingcontest.adventofcode._2020._13;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    // http://homepages.math.uic.edu/~leon/mcs425-s08/handouts/chinese_remainder.pdf
    public static void main(final String... args) {
        final List<String> rawInput = parseFileRaw("2020/13/input.txt");
        final String[] timeTable = rawInput.get(1).split(",");
        final List<Long[]> busInput = IntStream.range(0, timeTable.length)
                .filter(busInputIndex -> !timeTable[busInputIndex].equals("x"))
                .mapToObj(busInputIndex -> new Long[] {(long) busInputIndex, Long.parseLong(timeTable[busInputIndex])})
                .collect(Collectors.toList());

        final long smallestCommonDenominator = busInput.stream()
                .mapToLong(inputElement -> inputElement[1])
                .reduce(1, (a, b) -> a * b);

        final Long[] z = new Long[busInput.size()];
        final Long[] y = new Long[busInput.size()];
        final Long[] w = new Long[busInput.size()];

        final long chineseRemainderTheoremValue = IntStream.range(0, busInput.size())
                .peek(inputIndex -> z[inputIndex] = smallestCommonDenominator / busInput.get(inputIndex)[1])
                .peek(inputIndex -> y[inputIndex] = modularInverse(z[inputIndex], busInput.get(inputIndex)[1]))
                .peek(inputIndex -> w[inputIndex] = y[inputIndex] * z[inputIndex])
                .mapToLong(inputIndex -> busInput.get(inputIndex)[0] * w[inputIndex])
                .sum() % smallestCommonDenominator;

        final long solution = smallestCommonDenominator - chineseRemainderTheoremValue;

        System.out.println("The earliest timestamp is: " + solution);
    }

    private static long modularInverse(final long number, final long mod) {
        final long base = number % mod;
        long calc = base;
        int numberOfIterations = 1;
        while (calc != 1) {
            calc = (calc + base) % mod;
            numberOfIterations++;
        }
        return numberOfIterations;
    }
}