package com.stevecorp.codingcontest.adventofcode._004;

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

    private static final Map<String, Predicate<String>> KY = Map.of(
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
            s -> s.isEmpty() ? null : Arrays.stream(s.split(" "))
                    .map(ss -> ss.split(":"))
                    .filter(ss -> KY.containsKey(ss[0]) && KY.get(ss[0]).test(ss[1]))
                    .collect(Collectors.toMap(ss -> ss[0], s3 -> s3[1]));

    public static void main(final String... args) {
        final List<Map<String, String>> in = parseFile("challenges/004/input.txt", MAPPER);
        in.add(null);
        int co = 0;
        final Map<String, String> tm = new HashMap<>();
        for (final Map<String, String> en : in) {
            if (en != null) {
                tm.putAll(en);
            } else {
                co += tm.size() == 7 ? 1 : 0;
                tm.clear();
            }
        }
        System.out.println(co);
    }
}