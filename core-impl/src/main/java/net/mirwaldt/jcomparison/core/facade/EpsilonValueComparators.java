package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.epsilon.*;

import static net.mirwaldt.jcomparison.core.facade.ValueComparators.pairFactory;

public class EpsilonValueComparators {

    private final static ValueComparator<Object> ULP_EPSILON_DOUBLE_COMPARATOR = new UlpEpsilonDoubleComparator(pairFactory);

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> ulpEpsilonDoubleComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) ULP_EPSILON_DOUBLE_COMPARATOR;
    }


    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> absoluteEpsilonDoubleComparator(double absoluteEpsilon,
                                                                                                    PairFactory pairFactory) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new AbsoluteEpsilonDoubleComparator(pairFactory, absoluteEpsilon);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> absoluteEpsilonDoubleComparator(double absoluteEpsilon) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new AbsoluteEpsilonDoubleComparator(pairFactory, absoluteEpsilon);
    }


    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> relativeEpsilonDoubleComparator(double relativeEpsilon,
                                                                                                    PairFactory pairFactory) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new RelativeEpsilonDoubleComparator(pairFactory, relativeEpsilon);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> relativeEpsilonDoubleComparator(double relativeEpsilon) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new RelativeEpsilonDoubleComparator(pairFactory, relativeEpsilon);
    }



    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> absoluteEpsilonLongComparator(long absoluteEpsilon) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new AbsoluteEpsilonLongComparator(pairFactory, absoluteEpsilon);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> absoluteEpsilonLongComparator(long absoluteEpsilon, PairFactory pairFactory) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new AbsoluteEpsilonLongComparator(pairFactory, absoluteEpsilon);
    }


    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> relativeEpsilonLongComparator(double relativeEpsilon) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new RelativeEpsilonLongComparator(pairFactory, relativeEpsilon);
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> relativeEpsilonLongComparator(double relativeEpsilon, PairFactory pairFactory) {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) new RelativeEpsilonLongComparator(pairFactory, relativeEpsilon);
    }
}
