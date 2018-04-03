package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.DoubleFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RelativeEpsilonBigDecimalComparatorTest implements RelativeEpsilonDoubleComparatorTestable{
    @Override
    public DoubleFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier() {
        return (epsilon) -> new LazyAlwaysSupplyingTheSameInstanceSupplier<>(
                () -> new ValueComparator<Object>() {
                    final RelativeEpsilonBigDecimalComparator comparator =
                            new RelativeEpsilonBigDecimalComparator(
                                    new BigDecimal(String.valueOf(epsilon)),
                                    ValueComparators.pairFactory,
                                    RoundingMode.HALF_EVEN);

                    @Override
                    public ValueComparisonResult<Object> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace, ComparatorOptions comparatorOptions) throws ComparisonFailedException {
                        return (ValueComparisonResult) comparator.compare((BigDecimal) leftItem, (BigDecimal) rightItem, visitedObjectsTrace, comparatorOptions);
                    }
                }
        );
    }

    // is invoked by JUnit 5 via reflection
    static Stream<Class<?>> getTypeStream() {
        return Stream.of(
                BigDecimal.class
        );
    }
}
