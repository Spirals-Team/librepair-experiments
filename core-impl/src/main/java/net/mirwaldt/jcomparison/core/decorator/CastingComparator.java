package net.mirwaldt.jcomparison.core.decorator;

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
public class CastingComparator<TargetType> implements ItemComparator<Object, ComparisonResult<?,?,?>> {

	private final ItemComparator<TargetType, ? extends ComparisonResult<?,?,?>> targetObjectComparator;
	private final Class<? extends TargetType> targetType;
	
	public CastingComparator(ItemComparator<TargetType, ? extends ComparisonResult<?,?,?>> targetObjectComparator, Class<? extends TargetType> targetType) {
		super();
		this.targetObjectComparator = targetObjectComparator;
		this.targetType = targetType;
	}

	@Override
	public ComparisonResult<?,?,?> compare(Object leftObject, 
										   Object rightObject, 
										   VisitedObjectsTrace visitedObjectsTrace,
										   ComparatorOptions comparatorOptions)
			throws ComparisonFailedException, IllegalArgumentException {
		final TargetType castedLeftObject;
		try {
			castedLeftObject = targetType.cast(leftObject);
		} catch (ClassCastException e) {
			throw new ComparisonFailedException("Cannot cast left object of type '" + leftObject.getClass().getName() + "' to target type '" + targetType.getName() + "'!", e, leftObject, rightObject);
		}
		
		final TargetType castedRightObject;
		try {
			castedRightObject = targetType.cast(rightObject);
		} catch (ClassCastException e) {
			throw new ComparisonFailedException("Cannot cast right object of type '" + rightObject.getClass().getName() + "' to target type '" + targetType.getName() + "'!", e, leftObject, rightObject);
		}
		
		return targetObjectComparator.compare(castedLeftObject, castedRightObject, visitedObjectsTrace, comparatorOptions);
	}
}
