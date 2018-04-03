package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableByte;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableInt;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableLong;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableShort;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;

import java.util.function.DoubleFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RelativeEpsilonLongComparatorTest implements RelativeEpsilonLongComparatorTestable {
    @Override
    public DoubleFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier() {
        return (epsilon) -> new LazyAlwaysSupplyingTheSameInstanceSupplier<>(
                () -> new RelativeEpsilonLongComparator(ValueComparators.pairFactory, epsilon)
        );
    }

    // is invoked by JUnit 5 via reflection
    static Stream<Class<?>> getTypeStream() {
        return Stream.of(
                byte.class,
                short.class,
                int.class,
                long.class,
                MutableByte.class,
                MutableShort.class,
                MutableInt.class,
                MutableLong.class
        );
    }
}
