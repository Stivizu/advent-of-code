package com.stevecorp.codingcontest.adventofcode._001;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class SolutionA {

    public static void main(final String... args) {
        final int goal = 2020;

        final List<Integer> input = parseFile("challenges/001/input.txt", Integer::parseInt);
        final Map<Integer, List<Integer>> byLastDigit = input.stream()
                .collect(Collectors.groupingBy(integer -> integer % 10, Collectors.toList()));
        byLastDigit.forEach((key, value) -> Collections.sort(value));

        System.out.println("Grouped by last digit:");
        byLastDigit.entrySet().forEach(System.out::println);
        System.out.println();

        final List<List<Integer>> method1 = List.of(byLastDigit.get(0), byLastDigit.get(5));
        for (final List<Integer> inputPerLastDigit : method1) {

            System.out.println("Digit: " + (inputPerLastDigit.get(0) % 10));

            final int min = inputPerLastDigit.get(0);
            final List<Integer> filtered = inputPerLastDigit.stream()
                    .filter(integer -> min + integer <= goal)
                    .collect(Collectors.toList());

            System.out.println("Filtered input:");
            System.out.println(filtered);

            if (filtered.size() > 1) {
                int lastIndexToCheck = filtered.size();
                for (int i = 0; i < lastIndexToCheck - 1; i++) {
                    for (int j = 1; j < lastIndexToCheck; j++) {
                        final int number1 = filtered.get(i);
                        final int number2 = filtered.get(j);
                        final int sum = number1 + number2;
                        if (number1 + number2 == goal) {
                            System.out.println("\nSOLUTION ==> " + number1 + " * " + number2 + " = " + (number1 * number2));
                            System.exit(1);
                        }
                        if (sum > goal) {
                            lastIndexToCheck = j;
                        }
                    }
                }
            }

            System.out.println("--> Did not contain the solution\n");

        }

        final List<List<List<Integer>>> method2 = List.of(
                List.of(byLastDigit.get(1), byLastDigit.get(9)),
                List.of(byLastDigit.get(2), byLastDigit.get(8)),
                List.of(byLastDigit.get(3), byLastDigit.get(7)),
                List.of(byLastDigit.get(4), byLastDigit.get(6)));
        for (final List<List<Integer>> inputPerLastDigitCombo : method2) {
            final List<Integer> input1 = inputPerLastDigitCombo.get(0);
            final List<Integer> input2 = inputPerLastDigitCombo.get(1);

            System.out.println("Digit combination: " + (input1.get(0) % 10) + ", " + (input2.get(0) % 10));

            final int min1 = input1.get(0);
            final int min2 = input2.get(0);
            final List<Integer> filtered1 = input1.stream()
                    .filter(integer -> min2 + integer <= goal)
                    .collect(Collectors.toList());
            final List<Integer> filtered2 = input2.stream()
                    .filter(integer -> min1 + integer <= goal)
                    .collect(Collectors.toList());

            System.out.println("Filtered input:");
            System.out.println(filtered1);
            System.out.println(filtered2);

            if (!filtered1.isEmpty() && !filtered2.isEmpty()) {

                int lastIndexToCheck1 = filtered2.size();
                outerLoop1:
                for (int i = 0; i < filtered1.size(); i++) {
                    for (int j = 0; j < lastIndexToCheck1; j++) {
                        final int number1 = filtered1.get(i);
                        final int number2 = filtered2.get(j);
                        final int sum = number1 + number2;
                        if (number1 + number2 == goal) {
                            System.out.println("\nSOLUTION ==> " + number1 + " * " + number2 + " = " + (number1 * number2));
                            System.exit(1);
                        }
                        if (sum > goal) {
                            lastIndexToCheck1 = j;
                        }
                        if (sum > goal) {
                            break outerLoop1;
                        }
                    }
                }

                int lastIndexToCheck2 = filtered1.size();
                outerLoop2:
                for (int i = 0; i < filtered2.size(); i++) {
                    for (int j = 0; j < lastIndexToCheck2; j++) {
                        final int number1 = filtered2.get(i);
                        final int number2 = filtered1.get(j);
                        final int sum = number1 + number2;
                        if (number1 + number2 == goal) {
                            System.out.println("\nSOLUTION ==> " + number1 + " * " + number2 + " = " + (number1 * number2));
                            System.exit(1);
                        }
                        if (sum > goal) {
                            lastIndexToCheck2 = j;
                        }
                        if (sum > goal) {
                            break outerLoop2;
                        }
                    }
                }

            }

            System.out.println("--> Did not contain the solution\n");

        }

    }
}
