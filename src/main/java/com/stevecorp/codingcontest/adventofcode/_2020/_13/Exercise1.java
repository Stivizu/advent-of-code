package com.stevecorp.codingcontest.adventofcode._2020._13;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    public static void main(final String... args) {
        final List<String> input = parseFileRaw("2020/13/input.txt");

        final int availableTime = Integer.parseInt(input.get(0));
        final Set<Integer> busses = Arrays.stream(input.get(1).split(","))
                .filter(bus -> !bus.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        int earliestTakeableBus = -1;
        int earliestDepartureTime = Integer.MAX_VALUE;

        for (final Integer bus : busses) {
            final int remainder = availableTime % bus;
            final int earliestPossible = bus * (availableTime / bus + (remainder == 0 ? 0 : 1));
            if (earliestPossible < earliestDepartureTime) {
                earliestTakeableBus = bus;
                earliestDepartureTime = earliestPossible;
            }
        }

        final int solution = earliestTakeableBus * (earliestDepartureTime - availableTime);
        System.out.println(solution);
    }
}