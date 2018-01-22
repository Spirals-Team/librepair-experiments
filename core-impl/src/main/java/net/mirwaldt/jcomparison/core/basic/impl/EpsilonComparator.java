package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueComparisonResult;
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
 *
 *
 *
 * compares two items that cannot be compared by equals, 
 * e.g. float and double values (because they are rounded values)
 * 
 * http://stackoverflow.com/questions/1088216/whats-wrong-with-using-to-compare-floats-in-java
 */
@NotNullSafe
public class EpsilonComparator implements ValueComparator<Object> {

	private final double epsilon;

	public EpsilonComparator(double epsilon) {
		super();
		this.epsilon = epsilon;
	}

	@Override
	public ValueComparisonResult<Object> compare(Object leftValue, Object rightValue) throws ComparisonFailedException {
		if (leftValue instanceof Double && rightValue instanceof Double) {
			return EpsilonComparator.compareDoubles((Double) leftValue, (Double) rightValue, epsilon);
		} else if (leftValue instanceof Float && rightValue instanceof Float) {
			return EpsilonComparator.compareFloats((Float) leftValue, (Float) rightValue, epsilon);
		} else {
			throw new ComparisonFailedException(String.format(
					"At least one value is neither a double nor a float. (The type of leftValue is '%s' and the type of the right value is '%s')",
					leftValue.getClass().getName(), rightValue.getClass().getName()), leftValue, rightValue);
		}
	}

	@Override
	public ValueComparisonResult<Object> compare(Object leftValue, Object rightValue, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
		return compare(leftValue, rightValue);
	}

	public static ValueComparisonResult<Object> compareDoubles(double leftValue, double rightValue, double epsilon) {
		if (Math.abs(leftValue - rightValue) < epsilon) {
			return new ImmutableValueComparisonResult<>(true, leftValue, false, null);
		} else {
			return new ImmutableValueComparisonResult<>(false, null, true,
					new ImmutablePair<>(leftValue, rightValue));
		}
	}

	public static ValueComparisonResult<Object> compareFloats(float leftValue, float rightValue, float epsilon) {
		if (Math.abs(leftValue - rightValue) < epsilon) {
			return new ImmutableValueComparisonResult<>(true, leftValue, false, null);
		} else {
			return new ImmutableValueComparisonResult<>(false, null, true,
					new ImmutablePair<>(leftValue, rightValue));
		}
	}

	public static ValueComparisonResult<Object> compareFloats(float leftValue, float rightValue, double epsilon) {
		if (Math.abs(leftValue - rightValue) < epsilon) {
			return new ImmutableValueComparisonResult<>(true, leftValue, false, null);
		} else {
			return new ImmutableValueComparisonResult<>(false, null, true,
					new ImmutablePair<>(leftValue, rightValue));
		}
	}
}
