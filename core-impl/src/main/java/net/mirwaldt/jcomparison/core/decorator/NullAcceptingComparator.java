package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.annotation.NullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueSimilarity;

import java.util.function.Function;

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
public class NullAcceptingComparator<ObjectType> extends DecoratingComparator<ObjectType> {
	private final Function<Object, Object> checkingArrayWrapperFunction;

	public NullAcceptingComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?, ?, ?>> delegate, 
								   Function<Object, Object> arrayWrapperFunction) {
		super(delegate);
		this.checkingArrayWrapperFunction = arrayWrapperFunction;
	}

	@Override
	public ComparisonResult<?,?,?> compare(ObjectType leftObject, 
										   ObjectType rightObject, 
										   VisitedObjectsTrace visitedObjectsTrace,
										   ComparatorOptions comparatorOptions) throws ComparisonFailedException {
		if (leftObject != null && rightObject != null) {
			return super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
		} else if (leftObject == null && rightObject == null) {
			ValueSimilarity<ObjectType> similarity = ImmutableValueSimilarity.createValueSimilarityWithValue(null);
			return new ImmutableValueComparisonResult<>(true, similarity, false, null);
		} else {
			return new ImmutableValueComparisonResult<>(false, null, true,
                    new ImmutablePair<>(
                    		checkingArrayWrapperFunction.apply(leftObject),
							checkingArrayWrapperFunction.apply(rightObject)
					)
			);
		}
	}

}
