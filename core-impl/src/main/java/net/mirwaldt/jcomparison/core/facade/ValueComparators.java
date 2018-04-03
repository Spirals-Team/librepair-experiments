package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.annotation.ThreadSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.pair.impl.DefaultPairFactory;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.util.supplier.impl.ThreadLocalSupplier;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableResultValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.MutableResultValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.MutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.SwitchingEqualsComparator;

import java.util.function.Function;

public class ValueComparators {
    public final static PairFactory pairFactory = new DefaultPairFactory(ArrayWrapperFunctions.checkingArrayWrapperFunction());

    @ThreadSafe
    private final static ItemComparator IMMUTABLE_IDENTITY_COMPARATOR = 
            new ImmutableResultValueComparator((left, right) -> left == right, pairFactory, ArrayWrapperFunctions.checkingArrayWrapperFunction());
    private final static ValueComparator IMMUTABLE_EQUALS_COMPARATOR = 
            new ImmutableResultValueComparator(Object::equals, pairFactory, ArrayWrapperFunctions.checkingArrayWrapperFunction());
    @NotThreadSafe
    private final static ValueComparator<Object> NON_THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR = new MutableResultValueComparator(Object::equals, pairFactory,
            new LazyAlwaysSupplyingTheSameInstanceSupplier<>(MutableValueComparisonResult::new), ArrayWrapperFunctions.checkingArrayWrapperFunction());
    @ThreadSafe
    private final static ValueComparator<Object> THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR = new MutableResultValueComparator(Object::equals, pairFactory,
            new ThreadLocalSupplier<>(MutableValueComparisonResult::new), ArrayWrapperFunctions.checkingArrayWrapperFunction());
    /**
     * can switch between IMMUTABLE_EQUALS_COMPARATOR and THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR if comparator options allow it
     */
    private final static ValueComparator<Object> SWITCHING_EQUALS_COMPARATOR = new SwitchingEqualsComparator<Object>(IMMUTABLE_EQUALS_COMPARATOR,
            THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR);

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> immutableResultIdentityComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) IMMUTABLE_IDENTITY_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> immutableResultIdentityComparator(
            PairFactory pairFactory, Function<Object, Object> checkingArrayWrapperFunction) {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) new ImmutableResultValueComparator(
                (left, right) -> left == right,
                pairFactory,
                checkingArrayWrapperFunction);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> immutableResultEqualsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) IMMUTABLE_EQUALS_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> immutableResultEqualsComparator(
            PairFactory pairFactory, Function<Object, Object> checkingArrayWrapperFunction) {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) new ImmutableResultValueComparator(
                Object::equals, 
                pairFactory,
                checkingArrayWrapperFunction);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> nonThreadSafeMutableResultEqualsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) NON_THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> nonThreadSafeMutableResultEqualsComparator(
            PairFactory pairFactory, Function<Object, Object> checkingArrayWrapperFunction) {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) new MutableResultValueComparator(
                Object::equals, 
                pairFactory,
                new LazyAlwaysSupplyingTheSameInstanceSupplier<>(MutableValueComparisonResult::new),
                checkingArrayWrapperFunction);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> threadSafeMutableResultEqualsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) THREAD_SAFE_MUTABLE_EQUALS_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> threadSafeMutableResultEqualsComparator(
            PairFactory pairFactory, Function<Object, Object> checkingArrayWrapperFunction) {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) new MutableResultValueComparator(
                Object::equals, 
                pairFactory,
                new ThreadLocalSupplier<>(MutableValueComparisonResult::new),
                checkingArrayWrapperFunction);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> switchingEqualsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) SWITCHING_EQUALS_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?, ?, ?>> switchingEqualsComparator(ValueComparator<Object> immutableResultEqualsComparator,
                                                                                                       ValueComparator<Object> threadSafeMutableResultEqualsComparator) {
        return (ItemComparator<T, ? extends ComparisonResult<?, ?, ?>>) new SwitchingEqualsComparator<Object>(immutableResultEqualsComparator,
                threadSafeMutableResultEqualsComparator);
    }
}
