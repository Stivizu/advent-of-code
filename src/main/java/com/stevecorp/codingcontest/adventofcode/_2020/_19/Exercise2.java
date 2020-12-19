package com.stevecorp.codingcontest.adventofcode._2020._19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    private static final int LIMIT_DEPTH = 5;

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

        if (ruleNumber == 8) {
            final String rule42Regex = "(" + recursiveRegexBuilder(42, rules, regexMemory) + "+)";
            regexMemory.put(8, rule42Regex);
            return rule42Regex;
        }

        if (ruleNumber == 11) {
            final String rule42Regex = recursiveRegexBuilder(42, rules, regexMemory);
            final String rule31Regex = recursiveRegexBuilder(31, rules, regexMemory);
            final String rule11Regex = "(" + IntStream.range(1, 1 + LIMIT_DEPTH)
                    .mapToObj(index -> {
                        final StringBuilder regexBuilder = new StringBuilder("(");
                        IntStream.range(0, index).forEach(index2 -> regexBuilder.append(rule42Regex));
                        IntStream.range(0, index).forEach(index2 -> regexBuilder.append(rule31Regex));
                        regexBuilder.append(")");
                        return regexBuilder.toString();
                    })
                    .collect(Collectors.joining("|")) + ")";
            regexMemory.put(11, rule11Regex);
            return rule11Regex;
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
