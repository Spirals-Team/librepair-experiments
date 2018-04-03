package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueSimilarity;

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
 * Wrap it in ZeroHandlingRelativeEpsilonComparator to avoid zero as divisor!
 *
 */
public class RelativeEpsilonDoubleComparator extends EpsilonDoubleComparator implements ValueComparator<Object> {
    private final double relativeEpsilon;

    public RelativeEpsilonDoubleComparator(PairFactory pairFactory, 
                                           double relativeEpsilon) {
        super(pairFactory);
        this.relativeEpsilon = relativeEpsilon;
    }

    @Override
    protected ValueComparisonResult<Object> compareFloats(float leftFloat, 
                                                          float rightFloat, 
                                                          MutableFloat leftMutableFloat, 
                                                          MutableFloat rightMutableFloat) throws ComparisonFailedException {
        final double relativeDistance =
                ((double) Math.abs(leftFloat - rightFloat)) / Math.min(Math.abs(leftFloat), Math.abs(rightFloat)) - 1.0d;

        final Pair<?> floatPair = createFloatPair(leftFloat, rightFloat, leftMutableFloat, rightMutableFloat);
        if (relativeDistance <= relativeEpsilon) {
            final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithPair(floatPair);
            return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
        } else {
            return new ImmutableValueComparisonResult(false, null, true,
                    floatPair);
        }
    }

    @Override
    protected ValueComparisonResult<Object> compareDouble(double leftDouble, 
                                                          double rightDouble, 
                                                          MutableDouble leftMutableDouble, 
                                                          MutableDouble rightMutableDouble) throws ComparisonFailedException {
        final double relativeDistance = 
                (Math.abs(leftDouble - rightDouble)) / Math.min(Math.abs(leftDouble), Math.abs(rightDouble)) - 1.0d;
        final Pair<?> doublePair = createDoublePair(leftDouble, rightDouble, leftMutableDouble, rightMutableDouble);
        if (relativeDistance <= relativeEpsilon) {
            final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithPair(doublePair);
            return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
        } else {
            return new ImmutableValueComparisonResult(false, null, true,
                    doublePair);
        }
    }
}
