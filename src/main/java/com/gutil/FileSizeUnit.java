package com.gutil;

/**
 * Enum representing common file size units:
 * <ul>
 *  <li>Byte (1024<sup>0</sup> byte)</li>
 *  <li>Kilobyte (1024<sup>1</sup> bytes)</li>
 *  <li>Megabyte (1024<sup>2</sup> bytes)</li>
 *  <li>Gigabyte (1024<sup>3</sup> bytes)</li>
 *  <li>Terabyte (1024<sup>4</sup> bytes)</li>
 *  </ul>
 * @author Dariusz Gren
 * @version 1.0
 */
public enum FileSizeUnit {

    BYTE(1L),
    KILOBYTE(1_024L),
    MEGABYTE(1_048_576L),
    GIGABYTE(1_073_741_824L),
    TERABYTE(1_099_511_627_776L),
    ;

    private final long numberOfBytes;

    private FileSizeUnit(long numberOfBytes) {
        this.numberOfBytes = numberOfBytes;
    }

    /**
     * Returns amount of bytes kept by 1 unit.
     * @return number of bytes
     */
    public long getNumberOfBytes() {
        return numberOfBytes;
    }

    /**
     * Converts value from one size unit to another. For example while converting 2048 Megabytes to Gigabytes, 2.0 will
     * be returned.
     * @param originalUnitValue original file size in base unit
     * @param originalUnit base unit (from which size will be converted to)
     * @param targetUnit target unit (to which size will be converted from)
     * @return file size in target unit (as {@code double})
     */
    public static double convert(double originalUnitValue, FileSizeUnit originalUnit, FileSizeUnit targetUnit) {
        return originalUnitValue * ((double) originalUnit.getNumberOfBytes() / targetUnit.getNumberOfBytes());
    }

}
