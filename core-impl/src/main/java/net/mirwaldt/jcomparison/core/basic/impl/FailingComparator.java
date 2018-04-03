package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.function.BiFunction;

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
public class FailingComparator<ObjectType> implements ItemComparator<ObjectType, ComparisonResult<?,?,?>> {

    private final BiFunction<ObjectType, ObjectType, String> errorMessageSupplier;

    public FailingComparator(BiFunction<ObjectType, ObjectType, String> errorMessageSupplier) {
        this.errorMessageSupplier = errorMessageSupplier;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftItem, 
                                           ObjectType rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        throw new ComparisonFailedException(errorMessageSupplier.apply(leftItem, rightItem), leftItem, rightItem);
    }
}
