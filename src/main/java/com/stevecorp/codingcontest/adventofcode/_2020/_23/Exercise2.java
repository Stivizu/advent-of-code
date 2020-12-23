package com.stevecorp.codingcontest.adventofcode._2020._23;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    private static final Function<String, List<Integer>> MAPPER = inputLine ->
            inputLine.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());

    public static void main(final String... args) {
        final List<Integer> input = parseFile("2020/23/input.txt", MAPPER).get(0);

        final int[] smartInput = new int[1000001];
        IntStream.range(0, input.size() - 1).forEach(index -> smartInput[input.get(index)] = input.get(index + 1));
        smartInput[input.get(input.size() - 1)] = 10;
        IntStream.rangeClosed(10, 1000000).forEach(index -> smartInput[index] = index + 1);
        smartInput[smartInput.length - 1] = input.get(0);

        int initialCup = input.get(0);
        for (int turn = 0; turn < 10000000; turn++) {

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

        final long solution = (long) smartInput[1] * smartInput[smartInput[1]];

        System.out.println("The multiplication value of the labels is: " + solution);

    }
}
