package com.stevecorp.codingcontest.adventofcode._2020._14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFileRaw;

public class Exercise1 {

    public static void main(final String... args) {
        final List<String> input = parseFileRaw("2020/14/input.txt");

        Long mask0 = null;
        Long mask1 = null;
        final Map<Long, Long> memory = new HashMap<>();

        for (final String inputLine : input) {
            switch (inputLine.charAt(1)) {
                case 'a' -> {
                    final String mask = inputLine.split(" = ")[1];
                    mask0 = Long.parseLong(mask.replaceAll("X", "1"), 2);
                    mask1 = Long.parseLong(mask.replaceAll("X", "0"), 2);
                }
                case 'e' -> {
                    final long memAddress = Long.parseLong(inputLine.substring(inputLine.indexOf("[") + 1, inputLine.indexOf("]")));
                    final long instruction = Long.parseLong(inputLine.split(" = ")[1]);
                    final Long result = (instruction & mask0) | mask1;
                    memory.put(memAddress, result);
                }
            }
        }

        final long solution = memory.values().stream().mapToLong(x -> x).sum();

        System.out.println("The memory sum is: " + solution);
    }
}