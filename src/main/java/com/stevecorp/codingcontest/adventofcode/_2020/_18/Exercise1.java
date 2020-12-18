package com.stevecorp.codingcontest.adventofcode._2020._18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, List<String>> MAPPER = inputLine ->
            new ArrayList<>(Arrays.asList(inputLine
                    .replaceAll("\\(", "( ")
                    .replaceAll("\\)", " )")
                    .split(" ")));

    public static void main(final String... args) {
        final List<List<String>> input = parseFile("2020/18/input.txt", MAPPER);
        final long solution = input.stream()
                .mapToLong(Exercise1::solve)
                .sum();
        System.out.println("The total sum is: " + solution);
    }

    private static long solve(final List<String> input) {
        while(input.contains(")")) {
            final int closingParenthesisIndex = IntStream.range(0, input.size())
                    .filter(index -> ")".equals(input.get(index)))
                    .findFirst().orElseThrow();
            solveParentheses(input, closingParenthesisIndex);
        }
        return calculate(input);
    }

    private static void solveParentheses(final List<String> input, final int closingParenthesisIndex) {
        final int openingParenthesisIndex = IntStream.range(0, input.size())
                .filter(index -> "(".equals(input.get(index)))
                .filter(index -> index < closingParenthesisIndex)
                .max().orElseThrow();
        final List<String> inParentheses = input.subList(openingParenthesisIndex + 1, closingParenthesisIndex);
        long intermediaryResult = calculate(inParentheses);
        input.add(openingParenthesisIndex, String.valueOf(intermediaryResult));
        for (int removeCounter = 0; removeCounter < closingParenthesisIndex - openingParenthesisIndex + 1; removeCounter++) {
            input.remove(openingParenthesisIndex + 1);
        }
    }

    private static long calculate(final List<String> input) {
        long result = Long.parseLong(input.get(0));
        for (int inParenthesisIndex = 1; inParenthesisIndex < input.size(); inParenthesisIndex += 2) {
            final long nextNumber = Long.parseLong(input.get(inParenthesisIndex + 1));
            if ("+".equals(input.get(inParenthesisIndex))) {
                result += nextNumber;
            } else {
                result *= nextNumber;
            }
        }
        return result;
    }
}
