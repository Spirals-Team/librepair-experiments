package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.IdentityComparingItem;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.util.supplier.api.ThreadSafeSupplier;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;

import java.util.Map;
import java.util.function.BiPredicate;
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

    private final BiPredicate<ObjectType, ObjectType> cachingPredicate;
    
    /**
     * must always supply the same object and be thread safe!
     */
    private final ThreadSafeSupplier<Map<Pair<IdentityComparingItem>, ComparisonResult<?,?,?>>> comparisonResultCacheSupplier;


    public CachingComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?, ?, ?>> delegate, 
                             BiPredicate<ObjectType, ObjectType> cachingPredicate, 
                             ThreadSafeSupplier<Map<Pair<IdentityComparingItem>, ComparisonResult<?, ?, ?>>> comparisonResultCacheSupplier) {
        super(delegate);
        this.cachingPredicate = cachingPredicate;
        this.comparisonResultCacheSupplier = comparisonResultCacheSupplier;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftObject, 
                                           ObjectType rightObject, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if(cachingPredicate.test(leftObject, rightObject)) {
            final Map<Pair<IdentityComparingItem>, ComparisonResult<?,?,?>> cache = comparisonResultCacheSupplier.get();
            final ImmutablePair<IdentityComparingItem> comparisonPair =
                    new ImmutablePair<>(
                            new IdentityComparingItem(leftObject),
                            new IdentityComparingItem(rightObject)
                    );
            final ComparisonResult<?,?,?> cachedComparisonResult = cache.get(comparisonPair);
            if(cachedComparisonResult == null) {
                final ComparisonResult<?,?,?> comparisonResult = super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);

                if(comparisonResult != null && comparisonResult != BasicComparisonResults.emptyNonValueComparisonResult()) {
                    cache.put(comparisonPair, comparisonResult);
                }

                return comparisonResult;
            } else {
                return cachedComparisonResult;
            }
        } else {
            return super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
        }
    }
}
