package net.coljate.util;

import java.util.Collections;

/**
 *
 * @author ollie
 */
public class Native {

    public static <T> java.util.Set<T> createConcurrentHashSet(final int initialCapacity) {
        return Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>(initialCapacity));
    }

}
