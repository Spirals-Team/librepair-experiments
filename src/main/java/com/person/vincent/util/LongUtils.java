package com.person.vincent.util;

/**
 * Author: vincent
 * Date: 2018-04-10 20:04:00
 * Comment:
 */
public class LongUtils {

    public static Long[] toLongArray(long[] arrays) {
        Long[] longs = new Long[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            longs[i] = arrays[i];
        }
        return longs;
    }
}
