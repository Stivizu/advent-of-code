package com.stevecorp.codingcontest.adventofcode._009;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    public static void main(final String... args) {
        System.out.println("First illegal number: " + exercise1(parseFile("challenges/009/input.txt", Long::parseLong)));
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