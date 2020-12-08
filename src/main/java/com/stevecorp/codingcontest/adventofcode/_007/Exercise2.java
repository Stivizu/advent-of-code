package com.stevecorp.codingcontest.adventofcode._007;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
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

public class Exercise2 {

    private static final Function<String, Map.Entry<String, Set<String>>> MAPPER = s -> {
        final String[] s1 = s.replaceAll("\\.|no other bags|bags|bag", "").split("contain");
        return new SimpleEntry<>(s1[0].trim(), Arrays.stream(s1[1].split(",")).map(String::trim).collect(Collectors.toSet()));
    };

    public static void main(final String... args) {
        final Map<String, Set<String>> in = parseFileMap("challenges/007/input.txt", MAPPER);
        System.out.println(rec(in.get("shiny gold"), in));
    }

    private static int rec(final Set<String> s, final Map<String, Set<String>> d) {
        return s.stream()
                .map(sx -> new SimpleEntry<>(Character.getNumericValue(sx.charAt(0)),
                        d.get(sx.substring(2)).stream().filter(entry -> entry != null && !"".equals(entry)).collect(Collectors.toSet())))
                .mapToInt(i -> i.getValue().isEmpty() ? i.getKey() : i.getKey() + i.getKey() * rec(i.getValue(), d))
                .sum();
    }
}