package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class StringUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
            "text, false, false",
            ", true, true",
            "' ', false, true",
            "_, false, false",
            "\t, true, true",
            "\n, true, true"
    })
    public void blankOrEmptyTest(String text, boolean isEmpty, boolean isBlank) {
        Assertions.assertEquals(isEmpty, StringUtil.isEmpty(text));
        Assertions.assertEquals(isBlank, StringUtil.isBlank(text));
    }

    @Test
    public void reverseTest() {
        Assertions.assertNull(StringUtil.reverse(null));
        Assertions.assertEquals("a", StringUtil.reverse("a"));
        Assertions.assertEquals("zyx", StringUtil.reverse("xyz"));
        Assertions.assertEquals("abba", StringUtil.reverse("abba"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            ", a, 5, aaaaa, aaaaa",
            "xyz, _, 4, _xyz, xyz_",
            "xyz, _, 2, xyz, xyz",
            "pl, Ą, 5, ĄĄĄpl, plĄĄĄ",
            "'', x, 1, x, x",
    })
    public void paddingText(String originalText, char pad, int expectedLength, String padLeading, String padTrailing) {
        Assertions.assertEquals(padTrailing, StringUtil.padTrailing(originalText, pad, expectedLength));
        Assertions.assertEquals(padLeading, StringUtil.padLeading(originalText, pad, expectedLength));
    }

    @ParameterizedTest
    @MethodSource("startsWithTestSource")
    public void startsWithTest(boolean expectedResult, boolean expectedResultIgnoreCase, String originalText, String... candidates) {
        Assertions.assertEquals(expectedResult, StringUtil.startsWithAny(originalText, candidates));
        Assertions.assertEquals(expectedResultIgnoreCase, StringUtil.startsWithAnyIgnoreCase(originalText, candidates));
    }

    private static Stream<Arguments> startsWithTestSource() {
        return Stream.of(
                Arguments.of(false, false, null, new String[] {"a", "b"}),
                Arguments.of(false, false, "abc", null),
                Arguments.of(false, false, "abc", new String[] {}),
                Arguments.of(true, true, "abc", new String[] {"a"}),
                Arguments.of(true, true, "abc", new String[] {"a", "b", "c"}),
                Arguments.of(false, false, "abc", new String[] {"d"}),
                Arguments.of(false, true, "abc", new String[] {"A"}),
                Arguments.of(true, true, "abc", new String[] {"abc"}),
                Arguments.of(false, true, "abc", new String[] {"aBc"}),
                Arguments.of(false, false, "abc", new String[] {"abcd"}),
                Arguments.of(false, false, "abc", new String[] {"c"})
        );
    }

    @ParameterizedTest
    @MethodSource("endsWithTestSource")
    public void endsWithTest(boolean expectedResult, boolean expectedResultIgnoreCase, String originalText, String... candidates) {
        Assertions.assertEquals(expectedResult, StringUtil.endsWithAny(originalText, candidates));
        Assertions.assertEquals(expectedResultIgnoreCase, StringUtil.endsWithAnyIgnoreCase(originalText, candidates));
    }

    private static Stream<Arguments> endsWithTestSource() {
        return Stream.of(
                Arguments.of(false, false, null, new String[] {"a", "b"}),
                Arguments.of(false, false, "abc", null),
                Arguments.of(false, false, "abc", new String[] {}),
                Arguments.of(true, true, "abc", new String[] {"c"}),
                Arguments.of(true, true, "abc", new String[] {"a", "b", "c"}),
                Arguments.of(false, false, "abc", new String[] {"d"}),
                Arguments.of(false, true, "abc", new String[] {"C"}),
                Arguments.of(true, true, "abc", new String[] {"abc"}),
                Arguments.of(false, false, "abc", new String[] {"cb"}),
                Arguments.of(false, true, "abc", new String[] {"aBc"}),
                Arguments.of(false, false, "abc", new String[] {"_abc"}),
                Arguments.of(false, false, "abc", new String[] {"a"})
        );
    }

}
