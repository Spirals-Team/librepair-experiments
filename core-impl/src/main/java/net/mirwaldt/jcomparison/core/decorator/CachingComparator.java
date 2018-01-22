package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.Map;
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
public class CachingComparator<ObjectType> extends DecoratingComparator<ObjectType> {

    /**
     * must always supply the same object! May be a weak reference.
     */
    private final Supplier<Map<Pair<ObjectType>, ComparisonResult<?,?,?>>> comparisonResultCacheSupplier;

    public CachingComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?,?,?>> delegate, Supplier<Map<Pair<ObjectType>, ComparisonResult<?,?,?>>> comparisonResultCacheSupplier) {
        super(delegate);
        this.comparisonResultCacheSupplier = comparisonResultCacheSupplier;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftObject, ObjectType rightObject, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        final Map<Pair<ObjectType>, ComparisonResult<?,?,?>> cache = comparisonResultCacheSupplier.get();
        final ImmutablePair<ObjectType> comparisonPair = new ImmutablePair<>(leftObject, rightObject);
        final ComparisonResult<?,?,?> cachedComparisonResult = cache.get(comparisonPair);
        if(cachedComparisonResult == null) {
            final ComparisonResult<?,?,?> comparisonResult = super.compare(leftObject, rightObject, visitedObjectsTrace);

            if(comparisonResult != null && comparisonResult != ComparisonResults.emptyComparisonResult()) {
                cache.put(comparisonPair, comparisonResult);
            }

            return comparisonResult;
        } else {
            return cachedComparisonResult;
        }
    }
}
