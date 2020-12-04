package com.stevecorp.codingcontest.adventofcode.common;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Predicates {

    public static Predicate<String> pNumber() {
        return s -> NumberUtils.isCreatable(s.replaceFirst("^0+(?!$)", ""));
    }

    public static Predicate<String> pBounds(final int lower, final int upper) {
        return s -> NumberUtils.isCreatable(s) && Integer.parseInt(s) >= lower && Integer.parseInt(s) <= upper;
    }

    public static Predicate<String> pLengthExact(final int len) {
        return pLength(len, len);
    }

    public static Predicate<String> pLengthMin(final int min) {
        return pLength(min, Integer.MAX_VALUE);
    }

    public static Predicate<String> pLengthMax(final int max) {
        return pLength(0, max);
    }

    public static Predicate<String> pLength(final int min, final int max) {
        return s -> s.length() >= min && s.length() <= max;
    }

    public static Predicate<String> pContains(final Set<String> values) {
        return values::contains;
    }

    public static Predicate<String> pPrefix(final String prefix) {
        return s -> s.startsWith(prefix);
    }

    public static Predicate<String> pSuffix(final String suffix) {
        return s -> s.endsWith(suffix);
    }

    public static Predicate<String> pRegex(final Pattern regex) {
        return s -> regex.matcher(s).matches();
    }
}
