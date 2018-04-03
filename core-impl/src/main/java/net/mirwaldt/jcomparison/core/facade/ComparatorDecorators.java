package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.impl.IdentityComparingItem;
import net.mirwaldt.jcomparison.core.decorator.*;
import net.mirwaldt.jcomparison.core.decorator.checking.*;
import net.mirwaldt.jcomparison.core.decorator.tracing.CycleDetectingComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.MaxTraceLengthComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.TracingComparator;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.util.supplier.api.ThreadSafeSupplier;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

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
 */
public class ComparatorDecorators {
    public static <TargetType> ItemComparator<TargetType,? extends ComparisonResult<?,?,?>> specializing(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return (ItemComparator<TargetType, ? extends ComparisonResult<?, ?, ?>>) itemComparator;
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> nullRejecting(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return new NullRejectingComparator<>(itemComparator);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> nullAccepting(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return nullAccepting(itemComparator, ArrayWrapperFunctions.checkingArrayWrapperFunction());
    }


    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> nullAccepting(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            Function<Object, Object> arrayWrapperFunction) {
        return new NullAcceptingComparator<>(itemComparator, arrayWrapperFunction);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> strictCheckingInstanceOfSameType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator, Class<?> type) {
        return new InstanceOfOneTypeStrictCheckingComparator<>(type, itemComparator);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> niceCheckingInstanceOfSameType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator, 
            Class<?> type,
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> alternativeDelegate) {
        return new InstanceOfOneTypeNiceCheckingComparator<>(type, itemComparator, alternativeDelegate);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> strictCheckingSameType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return new SameTypeStrictCheckingComparator<>(itemComparator);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> niceCheckingSameType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> alternativeDelegate) {
        return new SameTypeNiceCheckingComparator<>(itemComparator, alternativeDelegate);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> strictCheckingExactType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator, Class<?> type) {
        return new ExactTypeStrictCheckingComparator<>(type, itemComparator);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> niceCheckingExactType(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            Class<?> type,
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> alternativeDelegate) {
        return new ExactTypeNiceCheckingComparator<>(type, itemComparator, alternativeDelegate);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> identityHandlingAsEqual(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return new IdentityHandlingComparator<>(itemComparator);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> proxyUnwrapping(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            Function<Object, Object> proxyUnwrappingFunction) {
        return new ProxyUnwrappingComparator<>(itemComparator, proxyUnwrappingFunction);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> optionsOptimized(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            boolean isValueComparator) {
        return new OptionsOptimizedComparator<>(itemComparator, isValueComparator);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> filtering(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            BiPredicate<Object, Object> filterPredicate) {
        return new FilterComparator<>(itemComparator, filterPredicate);
    }

    public static <TargetType> ItemComparator<Object,? extends ComparisonResult<?,?,?>> casting(
            ItemComparator<TargetType, ComparisonResult<?,?,?>> itemComparator, 
            Class<? extends TargetType> type) {
        return new CastingComparator<>(itemComparator, type);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> tracing(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return new TracingComparator(itemComparator);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> cycleDetecting(
            ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator,
            ItemComparator<Object, ? extends ComparisonResult<?,?,?>> cycleHandlingComparator) {
        return new CycleDetectingComparator(itemComparator, cycleHandlingComparator);
    }

    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> maxTraceLength(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            int maxTraceLength,
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> alternativeDelegate) {
        return new MaxTraceLengthComparator(itemComparator, maxTraceLength, alternativeDelegate);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> nullCheckingOnComparisonResult(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator) {
        return new NullCheckingOnComparisonResultComparator(itemComparator);
    }
    
    public static ItemComparator<Object,? extends ComparisonResult<?,?,?>> caching(
            ItemComparator<Object,? extends ComparisonResult<?,?,?>> itemComparator,
            BiPredicate<Object, Object> cachingPredicate,
            ThreadSafeSupplier<Map<Pair<IdentityComparingItem>, ComparisonResult<?,?,?>>> comparisonResultCacheSupplier) {
        return new CachingComparator<>(itemComparator, cachingPredicate, comparisonResultCacheSupplier);
    }
}
