package com.ljd.hackajob.phonebook.util;

/**
 *
 * @author leodavison
 *
 */
public class Utils {
    private Utils() {
        // nothing needed here.
    }

    public static boolean safeEquals(Object a, Object b) {
        if (a == null) {
            return (b==null);
        }

        return a.equals(b);
    }
}
