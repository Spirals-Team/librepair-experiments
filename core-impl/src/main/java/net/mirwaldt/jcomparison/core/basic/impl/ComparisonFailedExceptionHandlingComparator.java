package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.ResultsCollectingComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;

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
@NotThreadSafe
public class ComparisonFailedExceptionHandlingComparator<Type> implements ItemComparator<Type, ComparisonResult<?,?,?>> {

    private final ComparisonFailedExceptionHandler comparisonFailedExceptionHandler;
    
    private ItemComparator<Type, ? extends ComparisonResult<?,?,?>> comparator;
    private Supplier<ComparisonResult<?,?,?>> comparisonResultSafeSupplier;

    public ComparisonFailedExceptionHandlingComparator(ComparisonFailedExceptionHandler comparisonFailedExceptionHandler) {
        this.comparisonFailedExceptionHandler = comparisonFailedExceptionHandler;
    }

    public void setComparator(ItemComparator<Type, ? extends ComparisonResult<?, ?, ?>> comparator) {
        this.comparator = comparator;
    }

    public void setComparisonResultSafeSupplier(Supplier<ComparisonResult<?, ?, ?>> comparisonResultSafeSupplier) {
        this.comparisonResultSafeSupplier = comparisonResultSafeSupplier;
    }

    public ComparisonResult<?,?,?> compare(Type leftObject, 
                                           Type rightObject,
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        ComparisonResult<?,?, ?> comparisonResult;
        try {
            comparisonResult = comparator.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
        } catch(ComparisonFailedException comparisonFailedException) {
            try {
                comparisonResult = comparisonFailedExceptionHandler.handleException(comparisonFailedException);
            } catch (ResultsCollectingComparisonFailedException re) {
                try {
                    throw new ResultsCollectingComparisonFailedException(re, comparisonResultSafeSupplier.get());
                } catch (Exception e) {
                    final ComparisonFailedException exceptionWithSuppressedException = new ComparisonFailedException("Cannot collect result for exception.", e);
                    exceptionWithSuppressedException.addSuppressed(re);
                    throw exceptionWithSuppressedException;
                }
            }
        }
        return comparisonResult;
    }
}
