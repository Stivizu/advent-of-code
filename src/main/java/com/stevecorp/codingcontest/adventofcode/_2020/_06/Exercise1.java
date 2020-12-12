package com.stevecorp.codingcontest.adventofcode._2020._06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, List<Integer>> MAPPER = s -> s.chars().boxed().collect(Collectors.toList());

    public static void main(final String... args) {
        final long unanimousYesSummedAcrossGroups = group(parseFile("2020/06/input.txt", MAPPER), List::isEmpty).stream()
                .map(answerGroup -> new HashSet<>(answerGroup).stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()))
                .mapToInt(Set::size)
                .sum();
        System.out.println("The sum of the counts is: " + unanimousYesSummedAcrossGroups);
    }

    private static <T> List<List<T>> group(final List<T> input, final Predicate<T> separate) {
        final List<List<T>> output = new ArrayList<>();
        List<T> intermediate = new ArrayList<>();
        for (final T inputElement : input) {
            if (separate.test(inputElement)) {
                output.add(intermediate);
                intermediate = new ArrayList<>();
            } else {
                intermediate.add(inputElement);
            }
        }
        output.add(intermediate);
        return output;
    }
}