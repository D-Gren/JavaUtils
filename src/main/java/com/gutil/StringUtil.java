package com.gutil;

import java.util.Arrays;

/**
 * Class containing utility methods for {@code String} objects.
 * @author Dariusz Gren
 * @version 1.0
 */
public class StringUtil {

    /**
     * Checks if given {@code String} is empty (null-safe). String is treated as empty if {@link String#isEmpty()}
     * returns true.
     * @param text {@code String} to be checked
     * @return {@code true} if given String is empty or {@code null}
     */
    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * Checks if given {@code String} is blank (null-safe). String is treated as blank if {@link String#isBlank()}
     * returns true.
     * @param text {@code String} to be checked
     * @return {@code true} if given String is blank or {@code null}
     */
    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    /**
     * Reverses given {@code String} object (e.g. 'aBcDeF' will be reversed by creating a new object 'FeDcBa').
     * @param text original {@code String} object to be reversed (can be {@code null})
     * @return new object representing reversed string (if {@code null} was given as parameter, {@code null} will be returned)
     */
    public static String reverse(String text) {
        if (text == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(text);
        return builder.reverse().toString();
    }

    /**
     * Adds specified character to given String on the left side to meet requested length (e.g. if 'TEXT' should be
     * padded with 'x' to meet requested length of 6, 'xxTEXT' will be returned).
     * @param originalText original {@code String} object to be expanded by specified character
     * @param pad specified character to be added on the left side of the string
     * @param expectedLength max length of the {@code String} after the operation
     * @return expanded {@code String} will length not smaller than expected length
     */
    public static String padLeading(String originalText, char pad, int expectedLength) {
        originalText = originalText == null ? "" : originalText;
        if (originalText.length() >= expectedLength) {
            return originalText;
        }

        return String.valueOf(pad).repeat(expectedLength - originalText.length()) + originalText;
    }

    /**
     * Adds specified character to given String on the right side to meet requested length (e.g. if 'TEXT' should be
     * padded with 'x' to meet requested length of 6, 'TEXTxx' will be returned).
     * @param originalText original {@code String} object to be expanded by specified character
     * @param pad specified character to be added on the right side of the string
     * @param expectedLength max length of the {@code String} after the operation
     * @return expanded {@code String} will length not smaller than expected length
     */
    public static String padTrailing(String originalText, char pad, int expectedLength) {
        originalText = originalText == null ? "" : originalText;
        if (originalText.length() >= expectedLength) {
            return originalText;
        }

        return originalText + String.valueOf(pad).repeat(expectedLength - originalText.length());
    }

    /**
     * Checks if given String starts with any of the given possible prefixes.
     * @param text {@code String} to be checked
     * @param starters possible prefixes for the {@code String} object
     * @return {@code true} if {@code String} starts with any of the possible prefixes. If given {@code String} was null,
     * or not a single prefixes was put as parameter, {@code false} will be returned.
     */
    public static boolean startsWithAny(String text, String... starters) {
        if (starters == null || starters.length == 0 || text == null) {
            return false;
        }

        return Arrays.stream(starters).anyMatch(text::startsWith);
    }

    /**
     * Checks if given String starts with any of the given possible prefixes (ignoring the case).
     * @param text {@code String} to be checked
     * @param starters possible prefixes for the {@code String} object
     * @return {@code true} if {@code String} starts with any of the possible prefixes. If given {@code String} was null,
     * or not a single prefixes was put as parameter, {@code false} will be returned.
     */
    public static boolean startsWithAnyIgnoreCase(String text, String... starters) {
        if (starters == null || starters.length == 0 || text == null) {
            return false;
        }

        text = text.toLowerCase();
        return Arrays.stream(starters).map(String::toLowerCase).anyMatch(text::startsWith);
    }

    /**
     * Checks if given String ends with any of the given possible suffixes.
     * @param text {@code String} to be checked
     * @param enders possible suffixes for the {@code String} object
     * @return {@code true} if {@code String} ends with any of the possible suffixes. If given {@code String} was null,
     * or not a single suffixes was put as parameter, {@code false} will be returned.
     */
    public static boolean endsWithAny(String text, String... enders) {
        if (enders == null || enders.length == 0 || text == null) {
            return false;
        }

        return Arrays.stream(enders).anyMatch(text::endsWith);
    }

    /**
     * Checks if given String ends with any of the given possible suffixes (ignoring the case).
     * @param text {@code String} to be checked
     * @param enders possible suffixes for the {@code String} object
     * @return {@code true} if {@code String} ends with any of the possible suffixes. If given {@code String} was null,
     * or not a single suffixes was put as parameter, {@code false} will be returned.
     */
    public static boolean endsWithAnyIgnoreCase(String text, String... enders) {
        if (enders == null || enders.length == 0 || text == null) {
            return false;
        }

        text = text.toLowerCase();
        return Arrays.stream(enders).map(String::toLowerCase).anyMatch(text::endsWith);
    }

}
