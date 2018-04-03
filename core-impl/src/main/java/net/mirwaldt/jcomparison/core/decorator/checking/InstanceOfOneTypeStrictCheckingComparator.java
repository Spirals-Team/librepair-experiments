package net.mirwaldt.jcomparison.core.decorator.checking;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
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
@NotNullSafe
public class InstanceOfOneTypeStrictCheckingComparator<ObjectType>
		implements ItemComparator<ObjectType, ComparisonResult<?,?,?>> {

	private final ItemComparator<ObjectType, ? extends ComparisonResult<?,?,?>> delegate;
	private final Class<?> objectType;

	public InstanceOfOneTypeStrictCheckingComparator(Class<?> objectType, ItemComparator<ObjectType, ? extends ComparisonResult<?,?,?>> delegate) {
		super();
		this.delegate = delegate;
		this.objectType = objectType;
	}

	@Override
	public ComparisonResult<?,?,?> compare(ObjectType leftObject, 
										   ObjectType rightObject, 
										   VisitedObjectsTrace visitedObjectsTrace, 
										   ComparatorOptions comparatorOptions) throws ComparisonFailedException {
		if (!objectType.isInstance(leftObject)) {
			throw new ComparisonFailedException(String.format(
					"Objects cannot be compared because the left item is of type '%s' which is not an instance of '%s'.",
					leftObject.getClass().getName(), objectType.getClass().getName()), leftObject, rightObject);
		} else if (!objectType.isInstance(rightObject)) {
			throw new ComparisonFailedException(String.format(
					"Objects cannot be compared because the right item is of type '%s' which is not an instance of '%s'.",
					rightObject.getClass().getName(), objectType.getClass().getName()), leftObject, rightObject);
		} else {
			return delegate.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
		}
	}

}
