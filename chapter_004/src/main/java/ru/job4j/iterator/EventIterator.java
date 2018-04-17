package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class EventIterator implements Iterator {

    private final int[] values;
    private int cur = 0;

    public EventIterator(final int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        int length = values.length;
        if (cur == length) {
            result = false;
        } else {
            if (values[cur] % 2 == 0) {
                result = true;
            } else {
                while (cur < length - 1) {
                    if (values[++cur] % 2 == 0) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return values[cur++];
    }
}
