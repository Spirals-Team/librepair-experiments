package ru.iac.utils;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConvertByte {
    private static final int GB = 1073741824;
    private static final int MB = 1048576;
    private static final int KB = 1024;

    private enum SIZE {
        Gb,
        Mb,
        Kb;
    }

    public static String conv(long num) {
        SIZE textSize;
        float res = num;
        if (num >= GB) {
            res /= GB;
            textSize = SIZE.Gb;
        } else if (num >= MB) {
            res /= MB;
            textSize = SIZE.Mb;
        } else if (num >= KB) {
            res /= KB;
            textSize = SIZE.Kb;
        } else {
            return String.format("%d b", num);
        }
        return String.format("%.2f %s", res, textSize).replace(",00", "");
    }
}
