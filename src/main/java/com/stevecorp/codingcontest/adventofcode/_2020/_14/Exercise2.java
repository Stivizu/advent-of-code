package com.stevecorp.codingcontest.adventofcode._2020._14;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise2 {

    public static void main(final String... args) {
        final List<String> input = parseFileRaw("2020/14/input.txt");

        Long mask1 = null;
        Deque<Integer> floatingIndexes = null;
        final Map<Long, Long> memory = new HashMap<>();

        for (final String inputLine : input) {
            switch (inputLine.charAt(1)) {
                case 'a' -> {
                    final String mask = inputLine.split(" = ")[1];
                    mask1 = Long.parseLong(mask.replaceAll("X", "0"), 2);
                    floatingIndexes = allIndexesOf(mask, "X");
                }
                case 'e' -> {

                    final long memAddress = Long.parseLong(inputLine.substring(inputLine.indexOf("[") + 1, inputLine.indexOf("]")));
                    final long instruction = Long.parseLong(inputLine.split(" = ")[1]);

                    final long baseAddress = memAddress | mask1;
                    final String baseAddressString = StringUtils.leftPad(Long.toBinaryString(baseAddress), 36, "0");
                    final Set<String> expandedMemAddresses = expandMemAddress(baseAddressString, floatingIndexes);

                    expandedMemAddresses.forEach(address -> memory.put(Long.parseLong(address, 2), instruction));

                }
            }
        }

        final long solution = memory.values().stream().mapToLong(x -> x).sum();

        System.out.println("The memory sum is: " + solution);
    }

    private static Deque<Integer> allIndexesOf(final String input, final String search) {
        final Deque<Integer> indexes = new ArrayDeque<>();
        int index = input.indexOf(search);
        while (index >= 0) {
            indexes.add(index);
            index = input.indexOf(search, index + 1);
        }
        return indexes;
    }

    private static Set<String> expandMemAddress(final String baseAddressBinaryString, final Deque<Integer> floatingIndexes) {
        Set<String> memAddresses = new HashSet<>();
        memAddresses.add(baseAddressBinaryString);
        for (final Integer floatingIndex : floatingIndexes) {
            final Set<String> intermediaryAddresses = new HashSet<>();
            for (final String memAddress : memAddresses) {
                final String mutation1 = memAddress.substring(0, floatingIndex) + "1" + memAddress.substring(floatingIndex + 1);
                final String mutation2 = memAddress.substring(0, floatingIndex) + "0" + memAddress.substring(floatingIndex + 1);
                intermediaryAddresses.add(mutation1);
                intermediaryAddresses.add(mutation2);
            }
            memAddresses = intermediaryAddresses;
        }
        return memAddresses;
    }
}