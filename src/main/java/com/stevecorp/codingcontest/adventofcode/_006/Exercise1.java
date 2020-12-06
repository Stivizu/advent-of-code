package com.stevecorp.codingcontest.adventofcode._006;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.group;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, List<Integer>> MAPPER = s -> s.chars().boxed().collect(Collectors.toList());

    public static void main(final String... args) {
        System.out.println(group(parseFile("challenges/006/input.txt", MAPPER), List::isEmpty).stream()
                .map(g -> new HashSet<>(g).stream().flatMap(Collection::stream).collect(Collectors.toSet()))
                .mapToInt(Set::size).sum());
    }
}