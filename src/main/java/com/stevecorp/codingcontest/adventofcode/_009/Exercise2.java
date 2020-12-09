package com.stevecorp.codingcontest.adventofcode._009;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.stevecorp.codingcontest.adventofcode._009.Exercise1.exercise1;
import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    public static void main(final String... args) {
        final List<Long> input = parseFile("challenges/009/input.txt", Long::parseLong);
        final Long invalidNumber = exercise1(input);
        for (int inputSubListStartIndex = 0; inputSubListStartIndex < input.size() - 1; inputSubListStartIndex++) {
            final Long subListStartElement = input.get(inputSubListStartIndex);
            Long sum = subListStartElement;
            final Set<Long> sumElements = new HashSet<>(Collections.singletonList(subListStartElement));
            for (int subListIndex = inputSubListStartIndex + 1; subListIndex < input.size(); subListIndex++) {
                final Long subListElement = input.get(subListIndex);
                sum += subListElement;
                sumElements.add(subListElement);
                if (sum.equals(invalidNumber)) {
                    final Long min = sumElements.stream().mapToLong(x -> x).min().orElseThrow();
                    final Long max = sumElements.stream().mapToLong(x -> x).max().orElseThrow();
                    System.out.println("Encryption weakness: " + (min + max));
                    System.exit(-1);
                }
                if (sum > invalidNumber) {
                    break;
                }
            }
        }
    }
}