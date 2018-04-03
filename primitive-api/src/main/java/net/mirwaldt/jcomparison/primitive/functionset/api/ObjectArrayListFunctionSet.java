package net.mirwaldt.jcomparison.primitive.functionset.api;

import java.util.List;
import java.util.function.ToIntBiFunction;

public interface ObjectArrayListFunctionSet {
    ToIntBiFunction<Object, List<Object>> indexOfObjectFunction();
    ToIntBiFunction<Object, List<Object>> lastIndexOfObjectFunction();

    /**
     * @return a ToIntBiFunction that takes the element and the array list 
     * and returns the index at which the element was inserted.
     */
    ToIntBiFunction<Object, List<Object>> addObjectFunction();
}
