package net.mirwaldt.jcomparison.core.iterable.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.iterable.api.EachWithEachComparator;
import net.mirwaldt.jcomparison.core.iterable.api.EachWithEachComparisonResult;

import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
public class DefaultEachWithEachComparator implements EachWithEachComparator {
    private final Supplier<IntermediateEachWithEachComparisonResult> intermediateResultSupplier;
    private final Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> resultFunction;

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> comparator;
    private final ComparisonFailedExceptionHandler exceptionHandler;

    private final Predicate<IntermediateEachWithEachComparisonResult> stopPredicate;
    private final BiPredicate<Object, Object> pairFilter;

    /**
     * optimizations
     */
    private final ComparatorOptions comparatorOptions = new ComparatorOptions(true, true, true);
    
    public DefaultEachWithEachComparator(
            Supplier<IntermediateEachWithEachComparisonResult> intermediateResultSupplier,
            Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> resultFunction,
            ItemComparator<Object, ? extends ComparisonResult<?,?,?>> comparator,
            ComparisonFailedExceptionHandler exceptionHandler,
            Predicate<IntermediateEachWithEachComparisonResult> stopPredicate,
            BiPredicate<Object, Object> pairFilter) {
        this.intermediateResultSupplier = intermediateResultSupplier;
        this.resultFunction = resultFunction;
        this.comparator = comparator;
        this.exceptionHandler = exceptionHandler;
        this.stopPredicate = stopPredicate;
        this.pairFilter = pairFilter;
    }

    @Override
    public EachWithEachComparisonResult compare(Iterable leftIterable, 
                                                Iterable rightIterable, 
                                                VisitedObjectsTrace visitedObjectsTrace,
                                                ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final IntermediateEachWithEachComparisonResult intermediateResult = intermediateResultSupplier.get();

            final Iterator<?> leftIterator = leftIterable.iterator();
            final Iterator<?> rightIterator = rightIterable.iterator();

            if (leftIterator.hasNext() || rightIterator.hasNext()) {
                final ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator<>(exceptionHandler);
                exceptionHandlingComparator.setComparator(comparator);
                exceptionHandlingComparator.setComparisonResultSafeSupplier(() -> resultFunction.apply(intermediateResult));
                
                iterateAndCompare(leftIterable, rightIterable, visitedObjectsTrace, comparatorOptions, intermediateResult, exceptionHandlingComparator);
            }

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both list.", e, leftIterable, rightIterable);
        }
    }

    private void iterateAndCompare(
            Iterable<?> leftIterable, 
            Iterable<?> rightIterable, 
            VisitedObjectsTrace visitedObjectsTrace,
            ComparatorOptions comparatorOptions,
            IntermediateEachWithEachComparisonResult intermediateResult,
            ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator) throws ComparisonFailedException {
        for (Object leftItem : leftIterable) {
            for (Object rightItem : rightIterable) {
                if (pairFilter.test(leftItem, rightItem)) {
                    if (compareItems(leftItem, rightItem, visitedObjectsTrace, comparatorOptions, intermediateResult, exceptionHandlingComparator)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean compareItems(Object leftItem, 
                                 Object rightItem, 
                                 VisitedObjectsTrace visitedObjectsTrace,
                                 ComparatorOptions comparatorOptions,
                                 IntermediateEachWithEachComparisonResult intermediateResult,
                                 ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator) throws ComparisonFailedException {
        final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(leftItem, rightItem, visitedObjectsTrace, this.comparatorOptions);
        if (comparisonResult == BasicComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
            return true;
        } else {
            final Pair<Object> pair = new ImmutablePair<>(leftItem, rightItem);
            final ComparisonResult<?,?,?> previousComparisonResult = intermediateResult.writeComparisonResults().put(pair, comparisonResult);

            if (previousComparisonResult != null) {
                //TODO: how to handle it?
            }

            return stopPredicate.test(intermediateResult);
        }
    }
}
