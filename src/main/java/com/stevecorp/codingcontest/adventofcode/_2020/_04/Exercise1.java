package com.stevecorp.codingcontest.adventofcode._2020._04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Set<String> REQUIRED_PASSPORT_FIELDS = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    private static final Function<String, Set<String>> MAPPER =
            inputLine -> inputLine.isEmpty() ? null : Arrays.stream(inputLine.split(" "))
                    .map(passportElement -> passportElement.split(":")[0])
                    .filter(REQUIRED_PASSPORT_FIELDS::contains)
                    .collect(Collectors.toSet());

    public static void main(final String... args) {
        final List<Set<String>> input = group(parseFile("2020/04/input.txt", MAPPER));
        final long numberOfValidPassports = input.stream()
                .mapToInt(Set::size)
                .filter(size -> size == REQUIRED_PASSPORT_FIELDS.size())
                .count();
        System.out.println("Number of valid passports: " + numberOfValidPassports);
    }

    private static List<Set<String>> group(final List<Set<String>> input) {
        final List<Set<String>> output = new ArrayList<>();
        Set<String> intermediarySet = new HashSet<>();
        for (final Set<String> inputElement : input) {
            if (inputElement == null) {
                output.add(intermediarySet);
                intermediarySet = new HashSet<>();
            } else {
                intermediarySet.addAll(inputElement);
            }
        }
        output.add(intermediarySet);
        return output;
    }
}