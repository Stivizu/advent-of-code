package com.stevecorp.codingcontest.adventofcode._2020._23;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, List<Integer>> MAPPER = inputLine ->
            inputLine.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());

    public static void main(final String... args) {
        final List<Integer> input = parseFile("2020/23/input.txt", MAPPER).get(0);
        input.add(input.get(0));

        final int[] smartInput = new int[input.size()];
        IntStream.range(0, input.size() - 1)
                .forEach(index -> smartInput[input.get(index)] = input.get(index + 1));

        int initialCup = input.get(0);
        for (int turn = 0; turn < 100; turn++) {

            final int nextCup1 = smartInput[initialCup];
            final int nextCup2 = smartInput[nextCup1];
            final int nextCup3 = smartInput[nextCup2];

            int destinationCup = initialCup;
            do {
                destinationCup = --destinationCup <= 0 ? smartInput.length - 1 : destinationCup;
            } while (destinationCup == nextCup1 || destinationCup == nextCup2 || destinationCup == nextCup3);

            final int mem = smartInput[destinationCup];
            smartInput[initialCup] = smartInput[nextCup3];
            smartInput[destinationCup] = nextCup1;
            smartInput[nextCup3] = mem;
            initialCup = smartInput[initialCup];

        }

        final StringBuilder solution = new StringBuilder();
        int cupIndex = 1;
        do {
            solution.append(smartInput[cupIndex]);
            cupIndex = smartInput[cupIndex];
        } while (cupIndex != 1);

        System.out.println("The labels on the cup are: " + solution.deleteCharAt(solution.length() - 1));

    }
}
