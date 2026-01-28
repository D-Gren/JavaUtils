package com.gutil;

import java.util.Random;

/**
 * Class containing utility tools for Math operations.
 * @author Dariusz Gren
 * @version 1.0
 */
public class MathUtil {

    /**
     * Checks if a specific value is inside a specific range and if not, return the closest possible value in the range.
     * @param value value to be checked
     * @param min minimal accepted value
     * @param max maximal accepted value
     * @return original value if inside the range or the closest accepted value
     */
    public static int adjustToRange(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid arguments, min {" + min + "} cannot be bigger than max {" + max + "}");
        }

        return Math.min(max, Math.max(min, value));
    }

    /**
     * Checks if a specific value is inside a specific range and if not, return the closest possible value in the range.
     * @param value value to be checked
     * @param min minimal accepted value
     * @param max maximal accepted value
     * @return original value if inside the range or the closest accepted value
     */
    public static long adjustToRange(long value, long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid arguments, min {" + min + "} cannot be bigger than max {" + max + "}");
        }

        return Math.min(max, Math.max(min, value));
    }

    /**
     * Checks if a specific value is inside a specific range and if not, return the closest possible value in the range.
     * @param value value to be checked
     * @param min minimal accepted value
     * @param max maximal accepted value
     * @return original value if inside the range or the closest accepted value
     */
    public static double adjustToRange(double value, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid arguments, min {" + min + "} cannot be bigger than max {" + max + "}");
        }

        return Math.min(max, Math.max(min, value));
    }

    /**
     * Returns random integer value from a specific range.
     * @param minInclusive minimal possible value (inclusive)
     * @param maxExclusive maximal possible value (exclusive)
     * @return random value from a specific range
     */
    public static int randomInt(int minInclusive, int maxExclusive) {
        Random rand = new Random();
        return rand.nextInt(minInclusive, maxExclusive);
    }

    /**
     * Returns random long value from a specific range.
     * @param minInclusive minimal possible value (inclusive)
     * @param maxExclusive maximal possible value (exclusive)
     * @return random value from a specific range
     */
    public static long randomLong(long minInclusive, long maxExclusive) {
        Random rand = new Random();
        return rand.nextLong(minInclusive, maxExclusive);
    }

    /**
     * Returns random double value from a specific range.
     * @param minInclusive minimal possible value (inclusive)
     * @param maxExclusive maximal possible value (exclusive)
     * @return random value from a specific range
     */
    public static double randomDouble(double minInclusive, double maxExclusive) {
        Random rand = new Random();
        return rand.nextDouble(minInclusive, maxExclusive);
    }

}
