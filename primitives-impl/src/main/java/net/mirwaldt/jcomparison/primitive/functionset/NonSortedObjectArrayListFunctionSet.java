package net.mirwaldt.jcomparison.primitive.functionset;

import net.mirwaldt.jcomparison.primitive.functionset.api.ObjectArrayListFunctionSet;

import java.util.List;
import java.util.function.ToIntBiFunction;

public class NonSortedObjectArrayListFunctionSet implements ObjectArrayListFunctionSet {
    private final ToIntBiFunction<Object, List<Object>> indexOfObjectFunction =
            createIndexOfObjectFunction();

    private final ToIntBiFunction<Object, List<Object>> lastIndexOfObjectFunction =
            createLastIndexOfObjectFunction();
    
    private final ToIntBiFunction<Object, List<Object>> addObjectFunction =
            createAddObjectFunction();
    
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
        return (object, lazyInitializingArrayList)-> {
            if(lazyInitializingArrayList.isEmpty()) {
                return -1;
            } else {
                return lazyInitializingArrayList.indexOf(object);
            }
        };
    }

    public ToIntBiFunction<Object, List<Object>> createLastIndexOfObjectFunction() {
        return (object, lazyInitializingArrayList)-> {
            if(lazyInitializingArrayList.isEmpty()) {
                return -1;
            } else {
                return lazyInitializingArrayList.lastIndexOf(object);
            }
        };
    }

    public ToIntBiFunction<Object, List<Object>> createAddObjectFunction() {
        return (object, lazyInitializingArrayList)-> {
            final int index = lazyInitializingArrayList.size();
            lazyInitializingArrayList.add(object);
            return index;
        };
    }
}
