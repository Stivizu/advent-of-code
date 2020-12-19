package com.stevecorp.codingcontest.adventofcode._2020._19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    public static void main(String[] args) {
        final List<String> input = parseFileRaw("2020/19/input.txt");
        final int separationIndex = input.indexOf("");

        final Map<Integer, String> rules = input.subList(0, separationIndex).stream()
                .map(inputLine -> inputLine.replaceAll("\"", ""))
                .map(inputLine -> inputLine.split(": "))
                .collect(Collectors.toMap(
                        splitInputLine -> Integer.parseInt(splitInputLine[0]),
                        splitInputLine -> splitInputLine[1]
                ));

        final String regex = "^"
                + recursiveRegexBuilder(0, rules, new HashMap<>())
                + "$";

        final long numberOfValidEntries = input.subList(separationIndex + 1, input.size()).stream()
                .map(inputLine -> inputLine.matches(regex))
                .filter(x -> x)
                .count();

        System.out.println("Number of valid entries: " + numberOfValidEntries);
    }

    public static String recursiveRegexBuilder(final int ruleNumber, final Map<Integer, String> rules, final Map<Integer, String> regexMemory) {
        if (regexMemory.containsKey(ruleNumber)) {
            return regexMemory.get(ruleNumber);
        }

        final String rule = rules.get(ruleNumber);

        if ("a".equals(rule) || "b".equals(rule)) {
            return rule;
        }

        final String ruleInputRegex = "(" + Arrays.stream(rule.split(" "))
                .map(splitRuleInput -> "|".equals(splitRuleInput)
                        ? "|"
                        : recursiveRegexBuilder(Integer.parseInt(splitRuleInput), rules, regexMemory))
                .collect(Collectors.joining()) + ")";

        regexMemory.put(ruleNumber, ruleInputRegex);
        return ruleInputRegex;
    }
}
