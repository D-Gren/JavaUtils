package com.gutil;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Random;

/**
 * Utilities class for operation on dates and timestamps.
 * @author Dariusz Gren
 * @version 1.0
 */
public class CalendarUtil {

    /**
     * Returns how many whole years happened between two dates
     * @param startDate first {@code LocalDate}
     * @param endDate second {@code LocalDate}
     * @return full years between the dates
     */
    public static int getFullYearsBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return -Period.between(endDate, startDate).getYears();
        }

        return Period.between(startDate, endDate).getYears();
    }

    /**
     * Returns how many years happened between two dates (with fraction).
     * @param startDate first {@code LocalDate}
     * @param endDate second {@code LocalDate}
     * @return years between the dates
     */
    public static double getYearsBetween(LocalDate startDate, LocalDate endDate) {
        boolean switchSign = false;
        if (startDate.isAfter(endDate)) {
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;

            switchSign = true;
        }

        int fullYears = Period.between(startDate, endDate).getYears();
        startDate = startDate.plusYears(fullYears);

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return (switchSign ? -1 : 1) * (fullYears + days / 365.0);
    }

    /**
     * Returns the number of days between two dates (for same dates, 0 will be returned).
     * @param startDate first {@code LocalDate}
     * @param endDate second {@code LocalDate}
     * @return days the dates
     */
    public static int getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Returns random month (from January to December).
     * @return random month
     */
    public static Month getRandomMonth() {
        Random rand = new Random();
        int roll = rand.nextInt(12) + 1;
        return Month.of(roll);
    }

    /**
     * Returns random day of the month. In case of the February this method will never return 29.
     * @param month given {@code Month}
     * @return random day of the month (respecting the actual number of days in the month - returned number will
     * never be bigger than actual number of days in the month)
     */
    public static int getRandomDayOfMonth(Month month) {
        Random rand = new Random();
        return rand.nextInt(month.minLength()) + 1;
    }

    /**
     * Returns random day of the month in a specific year (allows to return 29 for case of the February during leap
     * year.
     * @param month given {@code Month}
     * @param year year for the random day
     * @return random day of the month (respecting the actual number of days in the month - returned number will
     * never be bigger than actual number of days in the month)
     */
    public static int getRandomDayOfMonth(Month month, int year) {
        Random rand = new Random();

        if (isLeapYear(year) && Month.FEBRUARY.equals(month)) {
            return rand.nextInt(1, 30);
        } else {
            return rand.nextInt(month.minLength()) + 1;
        }
    }

    /**
     * Returns random date of the given year.
     * @param year year to be checked for random date
     * @return random date from a given year
     */
    public static LocalDate getRandomDate(int year) {
        Month month = getRandomMonth();
        int dayOfMonth = getRandomDayOfMonth(month, year);
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Checks if given year was a leap year (containing 366 days).
     * @param year year to be checked
     * @return {@code true} if the year was a leap year
     */
    public static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366;
    }

}
