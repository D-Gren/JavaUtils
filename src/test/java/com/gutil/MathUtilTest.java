package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathUtilTest {

    @Test
    public void adjustToRangeTest() {
        Assertions.assertEquals(4, MathUtil.adjustToRange(4, 2, 5));
        Assertions.assertEquals(2, MathUtil.adjustToRange(1, 2, 5));
        Assertions.assertEquals(5, MathUtil.adjustToRange(7, 2, 5));
        Assertions.assertEquals(-3, MathUtil.adjustToRange(-10, -3, 9));
        Assertions.assertEquals(2, MathUtil.adjustToRange(2, -123125912L, 59129293912L));
        Assertions.assertEquals(2.5, MathUtil.adjustToRange(2.5, 2.4444444, 2.8912281));
        Assertions.assertEquals(2.4444444, MathUtil.adjustToRange(-8.0, 2.4444444, 2.8912281));
        Assertions.assertEquals(2.8912281, MathUtil.adjustToRange(14, 2.4444444, 2.8912281));

        Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.adjustToRange(0, 9, 8));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.adjustToRange(0L, 9L, 8L));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.adjustToRange(0.0, 9.0, 8.0));
    }

    @Test
    public void randomValue() {
        for (int i = 0; i < 100; ++i) {
            int randomInt = MathUtil.randomInt(-2, 8);
            Assertions.assertTrue(randomInt >= -2 && randomInt < 8);

            double randomDouble = MathUtil.randomDouble(0.0, 4.9);
            Assertions.assertTrue(randomDouble >= 0.0 && randomDouble < 4.9);

            double randomLong = MathUtil.randomLong(100, 150);
            Assertions.assertTrue(randomLong >= 100 && randomLong < 150);
        }

        Assertions.assertEquals(4, MathUtil.randomInt(4, 5));
    }

}
