package com.stevecorp.codingcontest.adventofcode._2020._07;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileMap;

public class Exercise1 {

    private static final Function<String, Map.Entry<String, Set<String>>> MAPPER = inputLine -> {
        final String[] splitInputLine = inputLine.replaceAll("\\d|\\.|bags|bag", "").split("contain");
        return new SimpleEntry<>(
                splitInputLine[0].trim(),
                Arrays.stream(splitInputLine[1].split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet()));
    };

    public static void main(final String... args) {
        final Map<String, Set<String>> input = reverseMap(parseFileMap("2020/07/input.txt", MAPPER));
        final int numberOfColors = recursiveLookup(input.get("shiny gold"), input).size();
        System.out.println("Number of bag colors that can contain a shiny gold bag: " + numberOfColors);
    }

    private static Set<String> recursiveLookup(final Set<String> bagEntries, final Map<String, Set<String>> input) {
        final Set<String> intermediate = new HashSet<>(bagEntries);
        bagEntries.stream()
                .filter(input::containsKey)
                .forEach(bagColor -> intermediate.addAll(recursiveLookup(input.get(bagColor), input)));
        return intermediate;
    }

    public static <T> Map<T, Set<T>> reverseMap(final Map<T, Set<T>> input) {
        final Map<T, Set<T>> output = new HashMap<>();
        input.forEach((key, value) -> {
            for (final T element : value) {
                final Set<T> intermediate = output.containsKey(element) ? output.get(element) : new HashSet<>();
                intermediate.add(key);
                output.put(element, intermediate);
            }
        });
        return output;
    }
}