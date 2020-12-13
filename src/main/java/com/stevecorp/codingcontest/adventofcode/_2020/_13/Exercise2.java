package com.stevecorp.codingcontest.adventofcode._2020._13;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    public static void main(final String... args) {
//        final List<String> input = parseFileRaw("2020/13/input.txt");

        // a, m
        final List<Long[]> input = List.of(
                new Long[] {0L, 7L},
                new Long[] {1L, 13L},
                new Long[] {4L, 59L},
                new Long[] {6L, 31L},
                new Long[] {7L, 19L}
        );

        final long m = input.stream()
                .mapToLong(inputElement -> inputElement[1])
                .reduce(1, (a, b) -> a * b);

        final Long[] z = new Long[input.size()];
        IntStream.range(0, input.size())
                .forEach(inputIndex -> z[inputIndex] = m / input.get(inputIndex)[1]);

        final Long[] y = new Long[input.size()];
        IntStream.range(0, input.size())
                .forEach(inputIndex -> y[inputIndex] = modularInverse(z[inputIndex], input.get(inputIndex)[1]));

        final Long[] w = new Long[input.size()];
        IntStream.range(0, input.size())
                .forEach(inputIndex -> w[inputIndex] = y[inputIndex] * z[inputIndex]);

        final long solution = IntStream.range(0, input.size())
                .mapToLong(inputIndex -> input.get(inputIndex)[0] * w[inputIndex])
                .sum() % m;

        System.out.println(solution);
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