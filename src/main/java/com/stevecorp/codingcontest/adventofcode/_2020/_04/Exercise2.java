package com.stevecorp.codingcontest.adventofcode._2020._04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;
import static com.stevecorp.codingcontest.adventofcode.common.Predicates.pBounds;
import static com.stevecorp.codingcontest.adventofcode.common.Predicates.pContains;
import static com.stevecorp.codingcontest.adventofcode.common.Predicates.pLengthExact;
import static com.stevecorp.codingcontest.adventofcode.common.Predicates.pNumber;
import static com.stevecorp.codingcontest.adventofcode.common.Predicates.pRegex;

public class Exercise2 {

    private static final Map<String, Predicate<String>> REQUIRED_PASSPORTS_FIELDS = Map.of(
            "byr", pBounds(1920, 2002),
            "iyr", pBounds(2010, 2020),
            "eyr", pBounds(2020, 2030),
            "hgt", s -> switch (s.substring(s.length() - 2)) {
                case "cm" -> pBounds(150, 193).test(s.substring(0, s.length() - 2));
                case "in" -> pBounds(59, 76).test(s.substring(0, s.length() - 2));
                default -> false;
            },
            "hcl", pRegex(Pattern.compile("#[0-9a-f]{6}")),
            "ecl", pContains(Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")),
            "pid", pNumber().and(pLengthExact(9)));

    private static final Function<String, Map<String, String>> MAPPER =
            inputLine -> inputLine.isEmpty() ? null : Arrays.stream(inputLine.split(" "))
                    .map(passportEntry -> passportEntry.split(":"))
                    .filter(passportEntrySplit -> REQUIRED_PASSPORTS_FIELDS.containsKey(passportEntrySplit[0])
                            && REQUIRED_PASSPORTS_FIELDS.get(passportEntrySplit[0]).test(passportEntrySplit[1]))
                    .collect(Collectors.toMap(
                            passportEntrySplit -> passportEntrySplit[0],
                            passportEntrySplit -> passportEntrySplit[1]));

    public static void main(final String... args) {
        final List<Map<String, String>> input = grouping(parseFile("2020/04/input.txt", MAPPER));
        final long numberOfValidPassports = input.stream()
                .mapToInt(Map::size)
                .filter(size -> size == REQUIRED_PASSPORTS_FIELDS.size())
                .count();
        System.out.println("Number of valid passports: " + numberOfValidPassports);
    }

    private static List<Map<String, String>> grouping(final List<Map<String, String>> input) {
        final List<Map<String, String>> output = new ArrayList<>();
        Map<String, String> intermediaryMap = new HashMap<>();
        for (final Map<String, String> inputElement : input) {
            if (inputElement == null) {
                output.add(intermediaryMap);
                intermediaryMap = new HashMap<>();
            } else {
                intermediaryMap.putAll(inputElement);
            }
        }
        output.add(intermediaryMap);
        return output;
    }
}