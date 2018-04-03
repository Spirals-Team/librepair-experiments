package net.mirwaldt.jcomparison.primitive.functionset;

import net.mirwaldt.jcomparison.primitive.functionset.api.PrimitiveArrayListFunctionSet;
import net.mirwaldt.jcomparison.primitive.mutable.api.*;
import net.mirwaldt.jcomparison.primitive.util.api.BinarySearch;
import org.apache.commons.collections.primitives.*;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntBiFunction;

import static net.mirwaldt.jcomparison.primitive.mutable.api.Mutable.*;

public class SortedPrimitiveArrayListFunctionSet implements PrimitiveArrayListFunctionSet {
    private final Comparator<Mutable<?, ?>> objectComparator;
    private final Function<Object, Mutable<?, ?>> inputToMutableFunction;
    private final BinarySearch binarySearch;

    private final ToIntBiFunction<Mutable<?, ?>, Object> indexOfPrimitiveFunction =
            createIndexOfPrimitiveFunction();

    private final ToIntBiFunction<Mutable<?, ?>, Object> lastIndexOfPrimitiveFunction =
            createLastIndexOfPrimitiveFunction();

    private final ToIntBiFunction<Mutable<?, ?>, Object> addPrimitiveFunction =
            createAddPrimitiveFunction();
    
    public SortedPrimitiveArrayListFunctionSet(Comparator<Mutable<?, ?>> objectComparator, 
                                               Function<Object, Mutable<?, ?>> inputToMutableFunction, 
                                               BinarySearch binarySearch) {
        this.objectComparator = objectComparator;
        this.inputToMutableFunction = inputToMutableFunction;
        this.binarySearch = binarySearch;
    }

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
        return (mutableElement, lazyInitializingArrayList)-> {            
            final int index = binarySearchIndexOf(mutableElement, lazyInitializingArrayList, false);
            if (index < 0) {
                return -1;
            } else {
                return index;
            }
        };
    }
    
    public ToIntBiFunction<Mutable<?, ?>, Object> createLastIndexOfPrimitiveFunction() {
        return (mutableElement, lazyInitializingArrayList)-> {
            final int index = binarySearchIndexOf(mutableElement, lazyInitializingArrayList, true);
            if (index < 0) {
                return -1;
            } else {
                return index;
            }
        };
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> createAddPrimitiveFunction() {
        return (mutableElement, lazyInitializingArrayList) -> {            
            final int index = binarySearchIndexOf(mutableElement, lazyInitializingArrayList, false);
            final int insertionIndex;
            if (index < 0) {
                insertionIndex = -index + 1;
            } else {
                insertionIndex = index;
            }
            final byte type = mutableElement.getType();
            if(type == TYPE_BYTE) {
                final MutableByte mutableByte = (MutableByte) mutableElement;
                final ArrayByteList byteList = (ArrayByteList) lazyInitializingArrayList;
                byteList.add(insertionIndex, mutableByte.getValue());
            } else if(type == TYPE_CHAR) {
                final MutableChar mutableChar = (MutableChar) mutableElement;
                final ArrayCharList charList = (ArrayCharList) lazyInitializingArrayList;
                charList.add(insertionIndex, mutableChar.getValue());
            } else if(type == TYPE_SHORT) {
                final MutableShort mutableShort = (MutableShort) mutableElement;
                final ArrayShortList shortList = (ArrayShortList) lazyInitializingArrayList;
                shortList.add(insertionIndex, mutableShort.getValue());
            } else if(type == TYPE_INT) {
                final MutableInt mutableInt = (MutableInt) mutableElement;
                final ArrayIntList intList = (ArrayIntList) lazyInitializingArrayList;
                intList.add(insertionIndex, mutableInt.getValue());
            } else if(type == TYPE_LONG) {
                final MutableLong mutableLong = (MutableLong) mutableElement;
                final ArrayLongList longList = (ArrayLongList) lazyInitializingArrayList;
                longList.add(insertionIndex, mutableLong.getValue());
            } else if(type == TYPE_FLOAT) {
                final MutableFloat mutableFloat = (MutableFloat) mutableElement;
                final ArrayFloatList floatList = (ArrayFloatList) lazyInitializingArrayList;
                floatList.add(insertionIndex, mutableFloat.getValue());
            } else if(type == TYPE_DOUBLE) {
                final MutableDouble mutableDouble = (MutableDouble) mutableElement;
                final ArrayDoubleList doubleList = (ArrayDoubleList) lazyInitializingArrayList;
                doubleList.add(insertionIndex, mutableDouble.getValue());
            } else {
                throw new IllegalArgumentException("Cannot handle type "
                        + mutableElement.getType() + " here.");
            }
            
            return insertionIndex;
        };
    }

    private int binarySearchIndexOf(Mutable<?, ?> mutableElement,
                                    Object lazyInitializingArrayList,
                                    boolean lastIndexOf) {
        final byte type = mutableElement.getType();
        final int size;
        final IntFunction<Mutable<?,?>> mutableElementAtFunction;
        if(type == TYPE_BYTE) {
            final ArrayByteList byteList = (ArrayByteList) lazyInitializingArrayList;
            size = byteList.size();
            mutableElementAtFunction = index -> {
                final MutableByte mutableByte = (MutableByte) inputToMutableFunction.apply(TYPE_BYTE);
                mutableByte.setValue(byteList.get(index));
                return mutableByte;
            };
        } else if(type == TYPE_CHAR) {
            final ArrayCharList charList = (ArrayCharList) lazyInitializingArrayList;
            size = charList.size();
            mutableElementAtFunction = index -> {
                final MutableChar mutableChar = (MutableChar) inputToMutableFunction.apply(TYPE_CHAR);
                mutableChar.setValue(charList.get(index));
                return mutableChar;
            };
        } else if(type == TYPE_SHORT) {
            final ArrayShortList shortList = (ArrayShortList) lazyInitializingArrayList;
            size = shortList.size();
            mutableElementAtFunction = index -> {
                final MutableShort mutableShort = (MutableShort) inputToMutableFunction.apply(TYPE_SHORT);
                mutableShort.setValue(shortList.get(index));
                return mutableShort;
            };
        } else if(type == TYPE_INT) {
            final ArrayIntList intList = (ArrayIntList) lazyInitializingArrayList;
            size = intList.size();
            mutableElementAtFunction = index -> {
                final MutableInt mutableInt = (MutableInt) inputToMutableFunction.apply(TYPE_INT);
                mutableInt.setValue(intList.get(index));
                return mutableInt;
            };
        } else if(type == TYPE_LONG) {
            final ArrayLongList longList = (ArrayLongList) lazyInitializingArrayList;
            size = longList.size();
            mutableElementAtFunction = index -> {
                final MutableLong mutableLong = (MutableLong) inputToMutableFunction.apply(TYPE_LONG);
                mutableLong.setValue(longList.get(index));
                return mutableLong;
            };
        } else if(type == TYPE_FLOAT) {
            final ArrayFloatList floatList = (ArrayFloatList) lazyInitializingArrayList;
            size = floatList.size();
            mutableElementAtFunction = index -> {
                MutableFloat mutableFloat = (MutableFloat) inputToMutableFunction.apply(TYPE_FLOAT);
                mutableFloat.setValue(floatList.get(index));
                return mutableFloat;
            };
        } else if(type == TYPE_DOUBLE) {
            final ArrayDoubleList doubleList = (ArrayDoubleList) lazyInitializingArrayList;
            size = doubleList.size();
            mutableElementAtFunction = index -> {
                MutableDouble mutableDouble = (MutableDouble) inputToMutableFunction.apply(TYPE_DOUBLE);
                mutableDouble.setValue(doubleList.get(index));
                return mutableDouble;
            };
        } else {
            throw new IllegalArgumentException("Cannot handle type "
                    + mutableElement.getType() + " here.");
        }
        
        return binarySearch.binarySearchIndexOf(mutableElement, mutableElementAtFunction, objectComparator, size, lastIndexOf);
    }
}
