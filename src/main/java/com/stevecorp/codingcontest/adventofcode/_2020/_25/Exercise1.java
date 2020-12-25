package com.stevecorp.codingcontest.adventofcode._2020._25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        final List<Long> input = parseFile("2020/25/input.txt", Long::parseLong);

        final long cardPUB = input.get(0);
        final long doorPUB = input.get(1);

        final long div = 20201227;

        long value = 1;
        int numberOfIterations = 0;
        do {
            numberOfIterations++;
            value = (value * 7) % div;
        } while (value != cardPUB);

        value = 1;
        for (int iteration = 0; iteration < numberOfIterations; iteration++) {
            value = (value * doorPUB) % div;
        }

        System.out.println("The encryption key is: " + value);

    }

}
