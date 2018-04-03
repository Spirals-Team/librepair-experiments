package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
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
 * http://stackoverflow.com/questions/3728246/what-should-be-the-epsilon-value-when-performing-double-value-equal-comparison
 */
public class UlpEpsilonDoubleComparator extends EpsilonDoubleComparator implements ValueComparator<Object> {

    public UlpEpsilonDoubleComparator(PairFactory pairFactory) {
        super(pairFactory);
    }

    @Override
    protected ValueComparisonResult<Object> compareFloats(float leftFloat, 
                                                          float rightFloat, 
                                                          MutableFloat leftMutableFloat, 
                                                          MutableFloat rightMutableFloat) throws ComparisonFailedException {
        final float epsilon = Math.max(Math.ulp(leftFloat), Math.ulp(rightFloat));
        return compareFloats(leftFloat, rightFloat, leftMutableFloat, rightMutableFloat, epsilon);
    }

    @Override
    protected ValueComparisonResult<Object> compareDouble(double leftDouble, 
                                                          double rightDouble, 
                                                          MutableDouble leftMutableDouble, 
                                                          MutableDouble rightMutableDouble) throws ComparisonFailedException {
        final double epsilon = Math.max(Math.ulp(leftDouble), Math.ulp(rightDouble));
        return compareDouble(leftDouble, rightDouble, leftMutableDouble, rightMutableDouble, epsilon);
    }
}
