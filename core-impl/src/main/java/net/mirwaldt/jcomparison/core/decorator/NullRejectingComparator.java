package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.annotation.NullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.decorator.DecoratingComparator;
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
 */
@NullSafe
public class NullRejectingComparator<ObjectType> extends DecoratingComparator<ObjectType> {

	public NullRejectingComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?,?,?>> delegate) {
		super(delegate);
	}

	@Override
	public ComparisonResult<?,?,?> compare(ObjectType leftObject, 
										   ObjectType rightObject, 
										   VisitedObjectsTrace visitedObjectsTrace,
										   ComparatorOptions comparatorOptions)
			throws ComparisonFailedException, NullPointerException {
		if (leftObject != null && rightObject != null) {
			return super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
		} else if(leftObject == null && rightObject == null) {
			throw new ComparisonFailedException("Objects cannot be compared.",
					new NullPointerException("Both objects are null."), null, null);
		} else if (leftObject == null) {
			throw new ComparisonFailedException("Objects cannot be compared.",
					new NullPointerException("Left object is null."), null, rightObject);
		} else {
			throw new ComparisonFailedException("Objects cannot be compared.",
					new NullPointerException("Right object is null."), leftObject, null);
		}
	}
}
