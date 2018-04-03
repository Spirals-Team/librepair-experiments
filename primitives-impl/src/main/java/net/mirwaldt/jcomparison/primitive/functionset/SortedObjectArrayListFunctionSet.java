package net.mirwaldt.jcomparison.primitive.functionset;

import net.mirwaldt.jcomparison.primitive.functionset.api.ObjectArrayListFunctionSet;
import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.util.api.BinarySearch;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntBiFunction;

/**
 * 
 */
public class SortedObjectArrayListFunctionSet implements ObjectArrayListFunctionSet {
    private final Comparator<Object> objectComparator;
    private final Function<Object, Mutable<?, ?>> leftInputToMutableFunction;
    private final Function<Object, Mutable<?, ?>> rightInputToMutableFunction;
    private final BinarySearch binarySearch;
    
    private final ToIntBiFunction<Object, List<Object>> indexOfObjectFunction =
            createIndexOfObjectFunction();
    private final ToIntBiFunction<Object, List<Object>> lastIndexOfObjectFunction =
            createLastIndexOfObjectFunction();
    private final ToIntBiFunction<Object, List<Object>> addObjectFunction =
            createAddObjectFunction();

    public SortedObjectArrayListFunctionSet(Function<Object, Mutable<?, ?>> leftInputToMutableFunction, 
                                            Function<Object, Mutable<?, ?>> rightInputToMutableFunction, 
                                            BinarySearch binarySearch, 
                                            Comparator<Object> objectComparator) {
        this.leftInputToMutableFunction = leftInputToMutableFunction;
        this.rightInputToMutableFunction = rightInputToMutableFunction;
        this.binarySearch = binarySearch;
        this.objectComparator = objectComparator;
    }

    public SortedObjectArrayListFunctionSet(Function<Object, Mutable<?, ?>> leftInputToMutableFunction,
                                            Function<Object, Mutable<?, ?>> rightInputToMutableFunction,
                                            BinarySearch binarySearch) {
        this.leftInputToMutableFunction = leftInputToMutableFunction;
        this.rightInputToMutableFunction = rightInputToMutableFunction;
        this.binarySearch = binarySearch;
        
        // sort by hashCode
        this.objectComparator = Comparator.comparingInt(Object::hashCode).thenComparing((o1, o2) -> {
            if(o1.equals(o2)) {
                return 0;
            } else {
                return 1; // next neighbor
            }
        });
    }
    
    @Override
    public ToIntBiFunction<Object, List<Object>> indexOfObjectFunction() {
        return indexOfObjectFunction;
    }

    @Override
    public ToIntBiFunction<Object, List<Object>> lastIndexOfObjectFunction() {
        return lastIndexOfObjectFunction;
    }

    @Override
    public ToIntBiFunction<Object, List<Object>> addObjectFunction() {
        return addObjectFunction;
    }

    public ToIntBiFunction<Object, List<Object>> createIndexOfObjectFunction() {
        return (object, list) -> {
            if (list.isEmpty()) {
                return -1;
            } else {
                final int index = binarySearchIndexOf(object, list, false);
                if (index < 0) {
                    return -1;
                } else {
                    return index;
                }
            }
        };
    }

    public ToIntBiFunction<Object, List<Object>> createLastIndexOfObjectFunction() {
        return (object, list) -> {
            if (list.isEmpty()) {
                return -1;
            } else {
                final int index = binarySearchIndexOf(object, list, true);
                if (index < 0) {
                    return -1;
                } else {
                    return index;
                }
            }
        };
    }

    public ToIntBiFunction<Object, List<Object>> createAddObjectFunction() {
        return (object, list) -> {
            final int index = binarySearchIndexOf(object, list, false);
            final int insertionIndex;
            if (index < 0) {
                insertionIndex = -index + 1;
            } else {
                insertionIndex = index;
            }
            list.add(insertionIndex, object);
            return insertionIndex;
        };
    }

    public int binarySearchIndexOf(Object element,
                                   List<Object> objects,
                                   boolean lastIndexOf) {
        final Mutable<?, ?> mutableElement = leftInputToMutableFunction.apply(element);
        final IntFunction<Mutable<?,?>> mutableElementAtFunction = index -> rightInputToMutableFunction.apply(objects.get(index));
        final Comparator<Mutable<?, ?>> mutableObjectComparator = (leftMutable, rightMutable) -> objectComparator.compare(leftMutable.get(), rightMutable.get());
        return binarySearch.binarySearchIndexOf(mutableElement, mutableElementAtFunction, mutableObjectComparator, objects.size(), lastIndexOf);
    }
}
