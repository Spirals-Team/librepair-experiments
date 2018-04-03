package net.mirwaldt.jcomparison.primitive.functionset.api;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;

import java.util.List;
import java.util.function.ToIntBiFunction;

public interface PrimitiveArrayListFunctionSet {
    ToIntBiFunction<Mutable<?, ?>, Object> indexOfPrimitiveFunction();
    ToIntBiFunction<Mutable<?, ?>, Object> lastIndexOfPrimitiveFunction();

    /**
     * @return a ToIntBiFunction that takes the mutable and the primitive array list 
     * and returns the index at which the element was inserted.
     */
    ToIntBiFunction<Mutable<?, ?>, Object> addPrimitiveFunction();
}
