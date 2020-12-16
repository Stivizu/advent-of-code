package com.stevecorp.codingcontest.adventofcode._2020._16;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    public static void main(final String... args) {
        final List<String> input = parseFileRaw("2020/16/input.txt");

        final int[] contentSplitIndexes = IntStream.range(0, input.size())
                .filter(index -> input.get(index).isBlank())
                .toArray();

        final List<Interval> allIntervals = input.subList(0, contentSplitIndexes[0]).stream()
                .map(inputLine -> inputLine.split(": ")[1])
                .map(splitInputLine -> splitInputLine.split(" or "))
                .flatMap(Stream::of)
                .map(interval -> interval.split("-"))
                .map(interval -> new Interval(interval[0], interval[1]))
                .collect(Collectors.toList());
        final Set<Integer> yesElements = allIntervals.parallelStream()
                .map(Interval::allPossibilities)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        final int ticketScanningErrorRate = input.subList(contentSplitIndexes[1] + 2, input.size()).parallelStream()
                .map(inputLine -> inputLine.split(","))
                .flatMap(Stream::of)
                .mapToInt(Integer::parseInt)
                .filter(number -> !yesElements.contains(number))
                .sum();

        System.out.println("The ticket scanning error rate is: " + ticketScanningErrorRate);
    }

    private static final class Interval {
        int start;
        int end;

        public Interval(String start, String end) {
            this.start = Integer.parseInt(start);
            this.end = Integer.parseInt(end);
        }

        public List<Integer> allPossibilities() {
            return IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }
}