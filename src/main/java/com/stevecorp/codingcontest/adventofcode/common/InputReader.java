package com.stevecorp.codingcontest.adventofcode.common;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    public static <T, U> Map<T, U> parseFileMap(final String inputFilePath, final Function<String, Map.Entry<T, U>> mapper) {
        try {
            return Files.readAllLines(Paths.get(InputReader.class.getClassLoader().getResource(inputFilePath).toURI()))
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
