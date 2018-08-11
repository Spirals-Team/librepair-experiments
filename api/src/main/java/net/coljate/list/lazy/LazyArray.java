package net.coljate.list.lazy;

import net.coljate.list.Array;
import net.coljate.list.ImmutableArray;
import net.coljate.list.MutableArray;

/**
 *
 * @author Ollie
 */
public interface LazyArray<T>
        extends LazyList<T>, Array<T> {

    @Override
    default MutableArray<T> mutableCopy() {
        return Array.super.mutableCopy();
    }

    @Override
    default ImmutableArray<T> immutableCopy() {
        return Array.super.immutableCopy();
    }

}
