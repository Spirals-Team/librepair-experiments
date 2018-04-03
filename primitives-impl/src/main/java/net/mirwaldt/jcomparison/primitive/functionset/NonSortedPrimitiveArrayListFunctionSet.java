package net.mirwaldt.jcomparison.primitive.functionset;

import net.mirwaldt.jcomparison.primitive.functionset.api.PrimitiveArrayListFunctionSet;
import net.mirwaldt.jcomparison.primitive.mutable.api.*;
import org.apache.commons.collections.primitives.*;

import java.util.function.ToIntBiFunction;

import static net.mirwaldt.jcomparison.primitive.mutable.api.Mutable.*;

public class NonSortedPrimitiveArrayListFunctionSet implements PrimitiveArrayListFunctionSet {
    private final ToIntBiFunction<Mutable<?, ?>, Object> indexOfPrimitiveFunction =
            createIndexOfPrimitiveFunction();

    private final ToIntBiFunction<Mutable<?, ?>, Object> lastIndexOfPrimitiveFunction =
            createLastIndexOfPrimitiveFunction();

    private final ToIntBiFunction<Mutable<?, ?>, Object> addPrimitiveFunction =
            createAddPrimitiveFunction();

    @Override
    public ToIntBiFunction<Mutable<?, ?>, Object> indexOfPrimitiveFunction() {
        return indexOfPrimitiveFunction;
    }

    @Override
    public ToIntBiFunction<Mutable<?, ?>, Object> lastIndexOfPrimitiveFunction() {
        return lastIndexOfPrimitiveFunction;
    }

    @Override
    public ToIntBiFunction<Mutable<?, ?>, Object> addPrimitiveFunction() {
        return addPrimitiveFunction;
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> createIndexOfPrimitiveFunction() {
        return (mutableElement, lazyInitializingArrayList) -> {
            final int index = indexOf(mutableElement, lazyInitializingArrayList, false);
            if (index < 0) {
                return -1;
            } else {
                return index;
            }
        };
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> createLastIndexOfPrimitiveFunction() {
        return (mutableElement, lazyInitializingArrayList) -> {
            final int index = indexOf(mutableElement, lazyInitializingArrayList, true);
            if (index < 0) {
                return -1;
            } else {
                return index;
            }
        };
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> createAddPrimitiveFunction() {
        return (mutableElement, lazyInitializingArrayList) -> {
            final byte type = mutableElement.getType();
            if (type == TYPE_BYTE) {
                final MutableByte mutableByte = (MutableByte) mutableElement;
                final ArrayByteList byteList = (ArrayByteList) lazyInitializingArrayList;
                byteList.add(mutableByte.getValue());
                return byteList.size() - 1;
            } else if (type == TYPE_CHAR) {
                final MutableChar mutableChar = (MutableChar) mutableElement;
                final ArrayCharList charList = (ArrayCharList) lazyInitializingArrayList;
                charList.add(mutableChar.getValue());
                return charList.size() - 1;
            } else if (type == TYPE_SHORT) {
                final MutableShort mutableShort = (MutableShort) mutableElement;
                final ArrayShortList shortList = (ArrayShortList) lazyInitializingArrayList;
                shortList.add(mutableShort.getValue());
                return shortList.size() - 1;
            } else if (type == TYPE_INT) {
                final MutableInt mutableInt = (MutableInt) mutableElement;
                final ArrayIntList intList = (ArrayIntList) lazyInitializingArrayList;
                intList.add(mutableInt.getValue());
                return intList.size() - 1;
            } else if (type == TYPE_LONG) {
                final MutableLong mutableLong = (MutableLong) mutableElement;
                final ArrayLongList longList = (ArrayLongList) lazyInitializingArrayList;
                longList.add(mutableLong.getValue());
                return longList.size() - 1;
            } else if (type == TYPE_FLOAT) {
                final MutableFloat mutableFloat = (MutableFloat) mutableElement;
                final ArrayFloatList floatList = (ArrayFloatList) lazyInitializingArrayList;
                floatList.add(mutableFloat.getValue());
                return floatList.size() - 1;
            } else if (type == TYPE_DOUBLE) {
                final MutableDouble mutableDouble = (MutableDouble) mutableElement;
                final ArrayDoubleList doubleList = (ArrayDoubleList) lazyInitializingArrayList;
                doubleList.add(mutableDouble.getValue());
                return doubleList.size() - 1;
            } else {
                throw new IllegalArgumentException("Cannot handle type "
                        + mutableElement.getType() + " here.");
            }
        };
    }

    private int indexOf(Mutable<?, ?> mutableElement,
                        Object arrayList,
                        boolean lastIndexOf) {
        final byte type = mutableElement.getType();
        if (type == TYPE_BYTE) {
            final ArrayByteList byteList = (ArrayByteList) arrayList;
            final MutableByte mutableByte = (MutableByte) mutableElement;
            if (lastIndexOf) {
                return byteList.lastIndexOf(mutableByte.getValue());
            } else {
                return byteList.indexOf(mutableByte.getValue());
            }
        } else if (type == TYPE_CHAR) {
            final ArrayCharList charList = (ArrayCharList) arrayList;
            final MutableChar mutableChar = (MutableChar) mutableElement;
            if (lastIndexOf) {
                return charList.lastIndexOf(mutableChar.getValue());
            } else {
                return charList.indexOf(mutableChar.getValue());
            }
        } else if (type == TYPE_SHORT) {
            final ArrayShortList shortList = (ArrayShortList) arrayList;
            final MutableShort mutableShort = (MutableShort) mutableElement;
            if (lastIndexOf) {
                return shortList.lastIndexOf(mutableShort.getValue());
            } else {
                return shortList.indexOf(mutableShort.getValue());
            }
        } else if (type == TYPE_INT) {
            final ArrayIntList intList = (ArrayIntList) arrayList;
            final MutableInt mutableInt = (MutableInt) mutableElement;
            if (lastIndexOf) {
                return intList.lastIndexOf(mutableInt.getValue());
            } else {
                return intList.indexOf(mutableInt.getValue());
            }
        } else if (type == TYPE_LONG) {
            final ArrayLongList longList = (ArrayLongList) arrayList;
            final MutableLong mutableLong = (MutableLong) mutableElement;
            if (lastIndexOf) {
                return longList.lastIndexOf(mutableLong.getValue());
            } else {
                return longList.indexOf(mutableLong.getValue());
            }
        } else if (type == TYPE_FLOAT) {
            final ArrayFloatList floatList = (ArrayFloatList) arrayList;
            final MutableFloat mutableFloat = (MutableFloat) mutableElement;
            if (lastIndexOf) {
                return floatList.lastIndexOf(mutableFloat.getValue());
            } else {
                return floatList.indexOf(mutableFloat.getValue());
            }
        } else if (type == TYPE_DOUBLE) {
            final ArrayDoubleList doubleList = (ArrayDoubleList) arrayList;
            final MutableDouble mutableDouble = (MutableDouble) mutableElement;
            if (lastIndexOf) {
                return doubleList.lastIndexOf(mutableDouble.getValue());
            } else {
                return doubleList.indexOf(mutableDouble.getValue());
            }
        } else {
            throw new IllegalArgumentException("Cannot handle type "
                    + mutableElement.getType() + " here.");
        }
    }
}
