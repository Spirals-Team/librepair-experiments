package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.impl.EpsilonComparator;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.pair.impl.DefaultPairFactory;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableResultValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.MutableResultValueComparator;
import net.mirwaldt.jcomparison.core.value.impl.MutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 * Important:
 * decorate this basic comparators with other comparators
 * that handle null values and check the type of the parameters!
 * You find them in the package net.mirwaldt.jcomparison.net.mirwaldt.jcomparison.core.basic.decorators.impl.
 * Use a precomparator-chain for sanity checks.
 */
public class ItemComparators {
    private static final PairFactory pairFactory = new DefaultPairFactory();

    public static final ItemComparator IDENTITY_COMPARATOR = new ImmutableResultValueComparator((left, right)->left==right, pairFactory);

    public static final ItemComparator EQUALS_COMPARATOR = new ImmutableResultValueComparator(Object::equals, pairFactory);

    public static final ItemComparator MUTABLE_EQUALS_COMPARATOR = new MutableResultValueComparator(Object::equals, pairFactory, new LazySupplier<>(MutableValueComparisonResult::new));


    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> equalsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) EQUALS_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> ItemComparator<T, ? extends ComparisonResult<?,?,?>> mutableEqualsComparator() {
        return (ItemComparator<T, ? extends ComparisonResult<?,?,?>>) MUTABLE_EQUALS_COMPARATOR;
    }

    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> NO_COMPARATOR = (leftItem, rightItem, trace) -> { throw new ComparisonFailedException("NO_COMPARATOR cannot compare.", leftItem, rightItem); };

    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> EMPTY_COMPARATOR = (leftItem, rightItem, trace) -> ComparisonResults.emptyComparisonResult();

    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> SKIPPING_COMPARATOR = (leftItem, rightItem, trace) -> ComparisonResults.skipComparisonResult();

    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> STOPPING_COMPARATOR = (leftItem, rightItem, trace) -> ComparisonResults.stopComparisonResult();

    /**
     * http://stackoverflow.com/questions/3728246/what-should-be-the-epsilon-value-when-performing-double-value-equal-comparison
     */
    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> DOUBLE_ULP_EPSILON_COMPARATOR = (ValueComparator<Object>) (leftItem, rightItem, trace) -> {
        final double leftDouble;
        if(leftItem instanceof MutableDouble) {
            final MutableDouble mutableDouble = (MutableDouble) leftItem;
            leftDouble = mutableDouble.getValue();
        } else if(leftItem instanceof Double) {
            leftDouble = (Double) leftItem;
        } else {
            throw new ComparisonFailedException("Left item is neither a Double nor a " + MutableDouble.class.getSimpleName(), leftItem, rightItem);
        }

        final double rightDouble;
        if(rightItem instanceof MutableDouble) {
            final MutableDouble mutableDouble = (MutableDouble) rightItem;
            rightDouble = mutableDouble.getValue();
        } else if(rightItem instanceof Double) {
            rightDouble = (Double) rightItem;
        } else {
            throw new ComparisonFailedException("Right item is neither a Double nor a " + MutableDouble.class.getSimpleName(), leftItem, rightItem);
        }

        final double epsilon = Math.max(Math.ulp(leftDouble),
                Math.ulp(rightDouble));
        return EpsilonComparator.compareDoubles(leftDouble, rightDouble, epsilon);
    };

    public static final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> FLOAT_ULP_EPSILON_COMPARATOR = (ValueComparator<Object>) (leftItem, rightItem, trace) -> {
        final float leftFloat;
        if(leftItem instanceof MutableFloat) {
            final MutableFloat mutableFloat = (MutableFloat) leftItem;
            leftFloat = mutableFloat.getValue();
        } else if(leftItem instanceof Float) {
            leftFloat = (Float) leftItem;
        } else {
            throw new ComparisonFailedException("Left item is neither a Float nor a " + MutableFloat.class.getSimpleName(), leftItem, rightItem);
        }

        final float rightFloat;
        if(rightItem instanceof MutableFloat) {
            final MutableFloat mutableFloat = (MutableFloat) rightItem;
            rightFloat = mutableFloat.getValue();
        } else if(rightItem instanceof Float) {
            rightFloat = (Float) rightItem;
        } else {
            throw new ComparisonFailedException("Right item is neither a Float nor a " + MutableFloat.class.getSimpleName(), leftItem, rightItem);
        }

        final float epsilon = Math.max(Math.ulp(leftFloat),
                Math.ulp(rightFloat));
        return EpsilonComparator.compareFloats(leftFloat, rightFloat, epsilon);
    };
}
