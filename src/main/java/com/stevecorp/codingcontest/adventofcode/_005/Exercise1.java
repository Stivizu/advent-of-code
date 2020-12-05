package com.stevecorp.codingcontest.adventofcode._005;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, Deque<Boolean>[]> MAPPER = s -> new Deque[]{
            s.substring(0, 7).chars().mapToObj(c -> c == 'F').collect(Collectors.toCollection(ArrayDeque::new)),
            s.substring(7).chars().mapToObj(c -> c == 'L').collect(Collectors.toCollection(ArrayDeque::new))
    };

    public static void main(final String... args) {
        System.out.println(parseFile("challenges/005/input.txt", MAPPER).stream()
                .mapToInt(bp -> bs(0, 127, bp[0]) * 8 + bs(0, 7, bp[1]))
                .max().orElseThrow());
    }

    private static int bs(final int l, final int u, final Deque<Boolean> ins) {
        return ins.isEmpty() ? l : ins.pop() ? bs(l, u - (Math.abs(l - u) + 1) / 2, ins) : bs(l + (Math.abs(l - u) + 1) / 2, u, ins);
    }
}