package net.coljate;

import net.coljate.counter.Counter;
import net.coljate.counter.MutableCounter;
import net.coljate.counter.impl.CommonsBagCounter;
import net.coljate.counter.impl.MutableCommonsBagCounter;

/**
 * Extensible utility class for Commons Collections.
 *
 * @author Ollie
 * @since 1.0
 */
public class Commons {

    protected Commons() {
    }

    public static <T> Counter<T> viewOf(final org.apache.commons.collections4.Bag<T> bag) {
        return CommonsBagCounter.viewOf(bag);
    }

    public static <T> MutableCounter<T> createHashCounter() {
        return MutableCommonsBagCounter.createHashCounter();
    }

    public static <T extends Comparable<? super T>> MutableCounter<T> createTreeCounter() {
        return MutableCommonsBagCounter.createTreeCounter();
    }

}
