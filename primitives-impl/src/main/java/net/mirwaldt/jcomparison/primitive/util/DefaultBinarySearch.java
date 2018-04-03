package net.mirwaldt.jcomparison.primitive.util;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.util.api.BinarySearch;

import java.util.Comparator;
import java.util.function.IntFunction;

public class DefaultBinarySearch implements BinarySearch {

    public int binarySearchIndexOf(Mutable<?, ?> mutableElement,
                                   IntFunction<Mutable<?, ?>> mutableElementAtFunction,
                                   Comparator<Mutable<?, ?>> mutableComparator,
                                   int size,
                                   boolean lastIndexOf) {
        int startIndex = 0;
        int endIndex = size - 1;

        final Mutable<?, ?> mutableLeftElement = mutableElement;

        while (startIndex <= endIndex) {
            int middleIndex = startIndex + (endIndex - startIndex) / 2;

            Mutable<?, ?> mutableRightElement = mutableElementAtFunction.apply(middleIndex);
            int comparisonResult = mutableComparator.compare(mutableLeftElement, mutableRightElement);
            if (comparisonResult < 0) {
                endIndex = middleIndex - 1;
            } else if (0 < comparisonResult) {
                startIndex = middleIndex + 1;
            } else {
                final int foundIndex = middleIndex;
                if (lastIndexOf) {
                    for (int index = foundIndex + 1; index <= endIndex; index++) {
                        mutableRightElement = mutableElementAtFunction.apply(middleIndex);
                        comparisonResult = mutableComparator.compare(mutableLeftElement, mutableRightElement);
                        if (comparisonResult != 0) {
                            return index - 1;
                        }
                    }
                } else { // firstIndexOf
                    for (int index = foundIndex - 1; startIndex <= index; index--) {
                        mutableRightElement = mutableElementAtFunction.apply(middleIndex);
                        comparisonResult = mutableComparator.compare(mutableLeftElement, mutableRightElement);
                        if (comparisonResult != 0) {
                            return index + 1;
                        }
                    }
                }
            }
        }
        return -(startIndex + 1);
    }

}
