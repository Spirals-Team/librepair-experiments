package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayWrapper;
import net.mirwaldt.jcomparison.core.util.ArrayAccessor;

import java.util.Arrays;

public class DefaultArrayWrapper implements ArrayWrapper {
    private final Object[] array;

    public DefaultArrayWrapper(Object[] array) {
        this.array = array;
    }

    public DefaultArrayWrapper(Object array) {
        if(array instanceof Object[]) {
            this.array = (Object[]) array;
        } else if(array != null && array.getClass().isArray()) {
            final int length = ArrayAccessor.getLength(array);
            this.array = new Object[length];
            for (int index = 0; index < length; index++) {
                this.array[index] = ArrayAccessor.getElementAtIndex(array, index);   
            }
        } else if(array == null) {
            this.array = null;
        }  else {
            throw new IllegalArgumentException("Object is not an array but of type '" + array.getClass().getName() + "'.");
        }
    }

    @Override
    public Object[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultArrayWrapper that = (DefaultArrayWrapper) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(array);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(array);
    }
}
