package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;

import java.util.function.DoubleFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RelativeEpsilonDoubleComparatorTest implements RelativeEpsilonDoubleComparatorTestable {
    @Override
    public DoubleFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier() {
        return (epsilon) -> new LazyAlwaysSupplyingTheSameInstanceSupplier<>(
                () -> new RelativeEpsilonDoubleComparator(ValueComparators.pairFactory, epsilon)
        );
    }

    // is invoked by JUnit 5 via reflection
    static Stream<Class<?>> getTypeStream() {
        return Stream.of(
                float.class,
                double.class,
                MutableFloat.class,
                MutableDouble.class
        );
    }
}