package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

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

/**
 * Created by Michael on 06.05.2017.
 */
public class FilterComparator<ObjectType> extends DecoratingComparator<ObjectType> {
    private final BiPredicate<ObjectType, ObjectType> pairPredicate;

    public FilterComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?,?,?>> delegate, BiPredicate<ObjectType, ObjectType> pairPredicate) {
        super(delegate);
        this.pairPredicate = pairPredicate;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftObject, 
                                           ObjectType rightObject, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if(pairPredicate.test(leftObject, rightObject)) {
            return super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
        } else {
            return BasicComparisonResults.skipComparisonResult();
        }
    }
}
