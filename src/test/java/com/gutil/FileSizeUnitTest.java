package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class FileSizeUnitTest {

    @ParameterizedTest
    @MethodSource("numberOfBytesTestSource")
    public void numberOfBytesTest(long expectedBytes, FileSizeUnit unit) {
        Assertions.assertEquals(expectedBytes, unit.getNumberOfBytes());
    }

    private static Stream<Arguments> numberOfBytesTestSource() {
        return Stream.of(
                Arguments.of((long) Math.pow(1024, 0), FileSizeUnit.BYTE),
                Arguments.of((long) Math.pow(1024, 1), FileSizeUnit.KILOBYTE),
                Arguments.of((long) Math.pow(1024, 2), FileSizeUnit.MEGABYTE),
                Arguments.of((long) Math.pow(1024, 3), FileSizeUnit.GIGABYTE),
                Arguments.of((long) Math.pow(1024, 4), FileSizeUnit.TERABYTE)
        );
    }

    @ParameterizedTest
    @MethodSource("convertingTestSource")
    public void convertingTest(double expectedValue, double originalValue, FileSizeUnit originalUnit, FileSizeUnit targetUnit) {
        Assertions.assertEquals(expectedValue, FileSizeUnit.convert(originalValue, originalUnit, targetUnit));
    }

    private static Stream<Arguments> convertingTestSource() {
        return Stream.of(
                Arguments.of(1, 1, FileSizeUnit.BYTE, FileSizeUnit.BYTE),
                Arguments.of(5, 5, FileSizeUnit.KILOBYTE, FileSizeUnit.KILOBYTE),
                Arguments.of(0.25, 0.25, FileSizeUnit.MEGABYTE, FileSizeUnit.MEGABYTE),
                Arguments.of(12052, 12052, FileSizeUnit.GIGABYTE, FileSizeUnit.GIGABYTE),
                Arguments.of(-3, -3, FileSizeUnit.TERABYTE, FileSizeUnit.TERABYTE),
                Arguments.of(1024, 1, FileSizeUnit.KILOBYTE, FileSizeUnit.BYTE),
                Arguments.of(1536, 1.5, FileSizeUnit.GIGABYTE, FileSizeUnit.MEGABYTE),
                Arguments.of(0.5, 512, FileSizeUnit.GIGABYTE, FileSizeUnit.TERABYTE),
                Arguments.of(2, 2048, FileSizeUnit.BYTE, FileSizeUnit.KILOBYTE)
        );
    }

}
