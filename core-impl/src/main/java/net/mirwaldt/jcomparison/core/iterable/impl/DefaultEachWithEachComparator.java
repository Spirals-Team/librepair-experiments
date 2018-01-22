package net.mirwaldt.jcomparison.core.iterable.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparatorProvider;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
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

    private final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> comparatorProvider;
    private final ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator;

    private final Predicate<IntermediateEachWithEachComparisonResult> stopPredicate;
    private final BiPredicate<Object, Object> pairFilter;

    public DefaultEachWithEachComparator(
            Supplier<IntermediateEachWithEachComparisonResult> intermediateResultSupplier,
            Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> resultFunction,
            ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> comparatorProvider,
            ComparisonFailedExceptionHandler comparisonFailedExceptionHandler,
            Predicate<IntermediateEachWithEachComparisonResult> stopPredicate,
            BiPredicate<Object, Object> pairFilter) {
        this.intermediateResultSupplier = intermediateResultSupplier;
        this.resultFunction = resultFunction;
        this.comparatorProvider = comparatorProvider;
        this.exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator(comparisonFailedExceptionHandler);
        this.stopPredicate = stopPredicate;
        this.pairFilter = pairFilter;
    }

    @Override
    public EachWithEachComparisonResult compare(Iterable leftIterable, Iterable rightIterable, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        try {
            final IntermediateEachWithEachComparisonResult intermediateResult = intermediateResultSupplier.get();

            final Iterator<?> leftIterator = leftIterable.iterator();
            final Iterator<?> rightIterator = rightIterable.iterator();

            if (leftIterator.hasNext() || rightIterator.hasNext()) {
                iterateAndCompare(leftIterable, rightIterable, intermediateResult, visitedObjectsTrace);
            }

            return resultFunction.apply(intermediateResult);
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new list.", e, leftIterable, rightIterable);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both list.", e, leftIterable, rightIterable);
        }
    }

    private void iterateAndCompare(Iterable<?> leftIterable, Iterable<?> rightIterable, IntermediateEachWithEachComparisonResult intermediateResult, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException, InvalidSupplyException {
        for (Object leftItem : leftIterable) {
            for (Object rightItem : rightIterable) {
                if (pairFilter.test(leftItem, rightItem)) {
                    if (compareItems(leftItem, rightItem, intermediateResult, visitedObjectsTrace)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean compareItems(Object leftItem, Object rightItem, IntermediateEachWithEachComparisonResult intermediateResult, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException, InvalidSupplyException {

        final Class<?> type = getType(leftItem, rightItem);

        final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> comparator = comparatorProvider.provideComparator(type, leftItem, rightItem);
        final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(comparator, leftItem, rightItem, () -> resultFunction.apply(intermediateResult), visitedObjectsTrace);
        if (comparisonResult == ComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == ComparisonResults.stopComparisonResult()) {
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

    private Class<?> getType(Object leftItem, Object rightItem) {
        final Class<?> type;
        if(leftItem != null) {
            type = leftItem.getClass();
        } else if(rightItem != null) {
            type = rightItem.getClass();
        } else {
            //TODO: Use really null?
            type = null;
        }
        return type;
    }
}
