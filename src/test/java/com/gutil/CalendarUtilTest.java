package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

public class CalendarUtilTest {

    @ParameterizedTest
    @MethodSource("yearsBetweenTestSource")
    public void yearsBetweenTest(int fullYears, double fractionalYears, LocalDate firstDate, LocalDate secondDate) {
        Assertions.assertEquals(fullYears, CalendarUtil.getFullYearsBetween(firstDate, secondDate));
        Assertions.assertEquals(fractionalYears, CalendarUtil.getYearsBetween(firstDate, secondDate));
    }

    private static Stream<Arguments> yearsBetweenTestSource() {
        return Stream.of(
                Arguments.of(3, 3.0, LocalDate.of(1945, Month.DECEMBER, 12), LocalDate.of(1948, Month.DECEMBER, 12)),
                Arguments.of(-3, -3.0, LocalDate.of(1948, Month.DECEMBER, 12), LocalDate.of(1945, Month.DECEMBER, 12)),
                Arguments.of(12, 12.2, LocalDate.of(2005, Month.JANUARY, 12), LocalDate.of(2017, Month.MARCH, 26)),
                Arguments.of(12, 12.8, LocalDate.of(2005, Month.JANUARY, 12), LocalDate.of(2017, Month.OCTOBER, 31))
        );
    }

    @ParameterizedTest
    @MethodSource("daysBetweenTestSource")
    public void daysBetweenTest(int expectedDays, LocalDate firstDate, LocalDate secondDate) {
        Assertions.assertEquals(expectedDays, CalendarUtil.getDaysBetween(firstDate, secondDate));
    }

    private static Stream<Arguments> daysBetweenTestSource() {
        return Stream.of(
                Arguments.of(3, LocalDate.of(1945, Month.DECEMBER, 12), LocalDate.of(1945, Month.DECEMBER, 15)),
                Arguments.of(-3, LocalDate.of(1945, Month.DECEMBER, 15), LocalDate.of(1945, Month.DECEMBER, 12)),
                Arguments.of(0, LocalDate.of(2024, Month.SEPTEMBER, 14), LocalDate.of(2024, Month.SEPTEMBER, 14)),
                Arguments.of(366, LocalDate.of(2016, Month.JANUARY, 1), LocalDate.of(2017, Month.JANUARY, 1)),
                Arguments.of(26254, LocalDate.of(1950, Month.MAY, 12), LocalDate.of(2022, Month.MARCH, 29))
        );
    }

    @Test
    public void randomDateTest() {
        Assertions.assertNotNull(CalendarUtil.getRandomMonth());

        for (int i = 0 ; i < 500; ++i) {
            int randomDayOfFebruary = CalendarUtil.getRandomDayOfMonth(Month.FEBRUARY);
            int randomDayOfFebruaryInLeap = CalendarUtil.getRandomDayOfMonth(Month.FEBRUARY, 2012);
            int randomDayOfDecember = CalendarUtil.getRandomDayOfMonth(Month.DECEMBER);
            Assertions.assertTrue(randomDayOfFebruary <= 28 && randomDayOfFebruary > 0);
            Assertions.assertTrue(randomDayOfFebruaryInLeap <= 29 && randomDayOfFebruaryInLeap > 0);
            Assertions.assertTrue(randomDayOfDecember <= 31 && randomDayOfDecember > 0);
        }

        for (int i = 0 ; i < 500; ++i) {
            LocalDate date = CalendarUtil.getRandomDate(2008);
            Assertions.assertTrue(date.isAfter(LocalDate.of(2007, Month.DECEMBER, 31)) && date.isBefore(LocalDate.of(2009, Month.JANUARY, 1)));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "true, 2016",
            "true, 2008",
            "false, 2009",
            "true, 2000",
            "false, 1900"
    })
    public void isLeapYearTest(boolean expectedResult, int year) {
        Assertions.assertEquals(expectedResult, CalendarUtil.isLeapYear(year));
    }

}
