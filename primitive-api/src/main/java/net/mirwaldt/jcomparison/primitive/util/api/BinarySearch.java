package net.mirwaldt.jcomparison.primitive.util.api;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;

import java.util.Comparator;
import java.util.function.IntFunction;

public interface BinarySearch {
    int binarySearchIndexOf(Mutable<?, ?> mutableElement,
                            IntFunction<Mutable<?, ?>> mutableElementAtFunction,
                            Comparator<Mutable<?, ?>> mutableComparator,
                            int size,
                            boolean lastIndexOf);
}
