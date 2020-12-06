package com.stevecorp.codingcontest.adventofcode.common;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InputReader {

    public static <T> List<T> parseFile(final String inputFilePath, final Function<String, T> mapper) {
        try {
            return Files.readAllLines(Paths.get(InputReader.class.getClassLoader().getResource(inputFilePath).toURI()))
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<List<T>> group(final List<T> input, final Predicate<T> separate) {
        final List<List<T>> output = new ArrayList<>();
        List<T> intermediate = new ArrayList<>();
        for (final T inputElement : input) {
            if (separate.test(inputElement)) {
                output.add(intermediate);
                intermediate = new ArrayList<>();
            } else {
                intermediate.add(inputElement);
            }
        }
        output.add(intermediate);
        return output;
    }
}
