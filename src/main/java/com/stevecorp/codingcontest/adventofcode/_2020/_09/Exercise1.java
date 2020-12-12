package com.stevecorp.codingcontest.adventofcode._2020._09;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        final long firstIllegalNumber = exercise1(parseFile("2020/09/input.txt", Long::parseLong));
        System.out.println("The first illegal number is: " + firstIllegalNumber);
    }

    public static long exercise1(final List<Long> input) {
        final Deque<Long> window = new ArrayDeque<>(input.subList(0, 25));
        for (final long nextInput : input.subList(25, input.size())) {
            final Set<Long> windowUniqueElements = new HashSet<>(window);
            try {
                windowUniqueElements.stream()
                        .filter(windowElement -> 2 * windowElement != nextInput)
                        .filter(windowElement -> windowUniqueElements.contains(nextInput - windowElement))
                        .findFirst().orElseThrow();
            } catch (final Exception e) {
                return nextInput;
            }
            window.removeFirst();
            window.addLast(nextInput);
        }
        throw new RuntimeException();
    }
}