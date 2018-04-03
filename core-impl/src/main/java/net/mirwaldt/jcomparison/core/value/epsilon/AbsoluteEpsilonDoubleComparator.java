package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * <p>
 * <p>
 * compares two items that cannot be compared by equals,
 * e.g. float and double values (because they are rounded values)
 * <p>
 * http://stackoverflow.com/questions/1088216/whats-wrong-with-using-to-compare-floats-in-java
 */
@NotNullSafe
public class AbsoluteEpsilonDoubleComparator extends EpsilonDoubleComparator implements ValueComparator<Object> {
    private final double absoluteEpsilon;

    public AbsoluteEpsilonDoubleComparator(PairFactory pairFactory, 
                                           double absoluteEpsilon) {
        super(pairFactory);
        this.absoluteEpsilon = absoluteEpsilon;
    }

    @Override
    protected ValueComparisonResult<Object> compareFloats(float leftFloat,
                                                          float rightFloat,
                                                          MutableFloat leftMutableFloat,
                                                          MutableFloat rightMutableFloat) throws ComparisonFailedException {
        return compareFloats(leftFloat, rightFloat, leftMutableFloat, rightMutableFloat, absoluteEpsilon);
    }

    @Override
    protected ValueComparisonResult<Object> compareDouble(double leftDouble,
                                                          double rightDouble,
                                                          MutableDouble leftMutableDouble,
                                                          MutableDouble rightMutableDouble) throws ComparisonFailedException {
        return compareDouble(leftDouble, rightDouble, leftMutableDouble, rightMutableDouble, absoluteEpsilon);
    }
}
