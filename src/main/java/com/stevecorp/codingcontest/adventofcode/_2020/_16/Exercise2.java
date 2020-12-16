package com.stevecorp.codingcontest.adventofcode._2020._16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    public static void main(final String... args) {
        final List<String> input = parseFileRaw("2020/16/input.txt");

        final int[] contentSplitIndexes = IntStream.range(0, input.size())
                .filter(index -> input.get(index).isBlank())
                .toArray();

        final Map<String, TicketEntrySpecs> ticketEntryRanges = input.subList(0, contentSplitIndexes[0]).stream()
                .map(TicketEntrySpecs::new)
                .collect(Collectors.toMap(
                        ticketEntrySpecs -> ticketEntrySpecs.id,
                        ticketEntrySpecs -> ticketEntrySpecs
                ));
        final Set<Integer> yesElements = ticketEntryRanges.values().stream()
                .flatMap(ticketEntrySpecs -> Stream.of(ticketEntrySpecs.interval1, ticketEntrySpecs.interval2))
                .map(Interval::allPossibilities)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        final List<List<Integer>> validTickets = input.subList(contentSplitIndexes[1] + 2, input.size()).stream()
                .map(inputLine -> inputLine.split(","))
                .map(splitInputLine -> Arrays.stream(splitInputLine).map(Integer::parseInt).collect(Collectors.toList()))
                .filter(yesElements::containsAll)
                .collect(Collectors.toList());

        final Map<Integer, Set<String>> ticketMatching = IntStream.range(0, validTickets.get(0).size()).boxed()
                .collect(Collectors.toMap(
                        index -> index,
                        index -> new HashSet<>(ticketEntryRanges.keySet())
                ));

        final Map<String, Integer> solutionKey = new HashMap<>();
        while (!ticketMatching.isEmpty()) {

            final List<Integer> toRemove = new ArrayList<>();

            for (final Map.Entry<Integer, Set<String>> matchingEntry : ticketMatching.entrySet()) {
                final Set<String> entriesToCheck = new HashSet<>(matchingEntry.getValue());
                final int index = matchingEntry.getKey();
                for (String entry : entriesToCheck) {
                    final boolean isPossible = validTickets.stream()
                            .map(numbers -> numbers.get(index))
                            .filter(number -> !ticketEntryRanges.get(entry).isValid(number))
                            .findFirst().isEmpty();
                    if (!isPossible) {
                        matchingEntry.getValue().remove(entry);
                    }
                }
                if (matchingEntry.getValue().size() == 1) {
                    final String solutionEntry = matchingEntry.getValue().iterator().next();
                    toRemove.add(index);
                    solutionKey.put(solutionEntry, index);
                    ticketMatching.values().forEach(ticketMatchingElement -> ticketMatchingElement.remove(solutionEntry));
                }
            }

            toRemove.forEach(ticketMatching::remove);

        }

        final List<Integer> yourTicket = input.subList(contentSplitIndexes[0] + 2, contentSplitIndexes[0] + 3).stream()
                .map(inputLine -> inputLine.split(","))
                .flatMap(Stream::of)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        final Long solution = solutionKey.entrySet().stream()
                .filter(solutionEntry -> solutionEntry.getKey().startsWith("departure"))
                .map(solutionEntry -> (long) yourTicket.get(solutionEntry.getValue()))
                .reduce(1L, (a, b) -> a * b);

        System.out.println(solution);

    }

    private static final class Interval {
        int start;
        int end;

        public Interval(final String intervalDefinition) {
            final String[] splitInterval = intervalDefinition.split("-");
            this.start = Integer.parseInt(splitInterval[0]);
            this.end = Integer.parseInt(splitInterval[1]);
        }

        public List<Integer> allPossibilities() {
            return IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }

    private static final class TicketEntrySpecs {
        String id;
        Interval interval1;
        Interval interval2;

        public TicketEntrySpecs(final String inputLine) {
            final String[] splitInputLine = inputLine.split(": ");
            final String[] splitIntervals = splitInputLine[1].split(" or ");
            this.id = splitInputLine[0];
            this.interval1 = new Interval(splitIntervals[0]);
            this.interval2 = new Interval(splitIntervals[1]);
        }

        public boolean isValid(final int toValidate) {
            return (toValidate >= interval1.start && toValidate <= interval1.end)
                    || (toValidate >= interval2.start && toValidate <= interval2.end);
        }
    }
}