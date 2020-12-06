package com.stevecorp.codingcontest.adventofcode._006;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.group;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, List<Integer>> MAPPER = s -> s.chars().boxed().collect(Collectors.toList());

    public static void main(final String... args) {
        System.out.println(group(parseFile("challenges/006/input.txt", MAPPER), List::isEmpty).stream()
                .peek(g -> g.forEach(x -> g.get(0).retainAll(x))).map(x -> x.get(0))
                .mapToInt(List::size).sum());
    }
}