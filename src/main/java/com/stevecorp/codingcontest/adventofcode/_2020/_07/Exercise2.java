package com.stevecorp.codingcontest.adventofcode._2020._07;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileMap;

public class Exercise2 {

    private static final Function<String, Map.Entry<String, Set<String>>> MAPPER = inputLine -> {
        final String[] splitInputLine = inputLine.replaceAll("\\.|no other bags|bags|bag", "").split("contain");
        return new SimpleEntry<>(
                splitInputLine[0].trim(),
                Arrays.stream(splitInputLine[1].split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet()));
    };

    public static void main(final String... args) {
        final Map<String, Set<String>> input = parseFileMap("2020/07/input.txt", MAPPER);
        final int numberOfRequiredBags = recursiveLookup(input.get("shiny gold"), input);
        System.out.println("Number of required individual bags inside the shiny gold bag: " + numberOfRequiredBags);
    }

    private static int recursiveLookup(final Set<String> bagEntries, final Map<String, Set<String>> input) {
        return bagEntries.stream()
                .map(bagEntry -> new SimpleEntry<>(
                        Character.getNumericValue(bagEntry.charAt(0)),
                        input.get(bagEntry.substring(2)).stream()
                                .filter(entry -> entry != null && !"".equals(entry))
                                .collect(Collectors.toSet())))
                .mapToInt(mappedBagEntry -> mappedBagEntry.getValue().isEmpty()
                        ? mappedBagEntry.getKey()
                        : mappedBagEntry.getKey() + mappedBagEntry.getKey() * recursiveLookup(mappedBagEntry.getValue(), input))
                .sum();
    }
}