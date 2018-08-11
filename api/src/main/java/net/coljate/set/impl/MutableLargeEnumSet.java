package net.coljate.set.impl;

import java.util.Arrays;

import net.coljate.set.MutableSet;

/**
 *
 * @author Ollie
 */
public class MutableLargeEnumSet<E extends Enum<E>>
        extends LargeEnumSet<E>
        implements MutableSet<E> {

    protected MutableLargeEnumSet(final Class<E> enumClass, final byte[] values) {
        super(enumClass, values);
    }

    @Override
    public boolean add(final E element) {
        final int i = element.ordinal();
        if (values[i] == PRESENT) {
            return false;
        } else {
            values[i] = PRESENT;
            return true;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(final Object element) {
        if (this.contains(element)) {
            values[((E) element).ordinal()] = MISSING;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(values, MISSING);
    }

}
