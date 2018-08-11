package net.coljate.list.impl;

import net.coljate.list.Array;

/**
 *
 * @author Ollie
 */
public interface NativeArray<T> extends Array<T> {

    @Override
    Object[] arrayCopy();

    @Override
    default MutableNativeArray<T> mutableCopy() {
        return new MutableNativeArray<>(this.arrayCopy(), this.count());
    }

    @Override
    default ImmutableNativeArray<T> immutableCopy() {
        return new ImmutableNativeArray<>(this.arrayCopy(), this.count());
    }

}
