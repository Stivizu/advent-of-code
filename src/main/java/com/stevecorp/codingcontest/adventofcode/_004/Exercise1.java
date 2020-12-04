package com.stevecorp.codingcontest.adventofcode._004;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Set<String> KY = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    private static final Function<String, Map<String, String>> MAPPER =
            s -> s.isEmpty() ? null : Arrays.stream(s.split(" "))
                    .map(ss -> ss.split(":"))
                    .filter(ss -> KY.contains(ss[0]))
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