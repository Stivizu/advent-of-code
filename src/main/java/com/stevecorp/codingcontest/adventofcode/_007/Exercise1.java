package com.stevecorp.codingcontest.adventofcode._007;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.group;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileMap;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.reverseMap;

public class Exercise1 {

    private static final Function<String, Map.Entry<String, Set<String>>> MAPPER = s -> {
        final String[] s1 = s.replaceAll("\\d|\\.|bags|bag", "").split("contain");
        return new SimpleEntry<>(s1[0].trim(), Arrays.stream(s1[1].split(",")).map(String::trim).collect(Collectors.toSet()));
    };

    public static void main(final String... args) {
        final Map<String, Set<String>> in = reverseMap(parseFileMap("challenges/007/input.txt", MAPPER));
        System.out.println(rec(in.get("shiny gold"), in).size());
    }

    private static Set<String> rec(final Set<String> s, final Map<String, Set<String>> d) {
        final Set<String> p = new HashSet<>(s);
        s.stream().filter(d::containsKey).forEach(x -> p.addAll(rec(d.get(x), d)));
        return p;
    }
}