package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueSimilarity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

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
public class RelativeEpsilonBigIntegerComparator implements ValueComparator<BigInteger> {
    private final BigDecimal relativeEpsilon;
    private final PairFactory pairFactory;
    private final RoundingMode roundingMode;

    public RelativeEpsilonBigIntegerComparator(BigDecimal relativeEpsilon,
                                               PairFactory pairFactory, RoundingMode roundingMode) {
        this.relativeEpsilon = relativeEpsilon;
        this.pairFactory = pairFactory;
        this.roundingMode = roundingMode;
    }

    @Override
    public ValueComparisonResult<BigInteger> compare(BigInteger leftBigInteger, 
                                                     BigInteger rightBigInteger, 
                                                     VisitedObjectsTrace visitedObjectsTrace, 
                                                     ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final Pair<?> pair = pairFactory.createPair(leftBigInteger, rightBigInteger);
            
            final BigInteger maxBigInteger = leftBigInteger.max(rightBigInteger);

            // == is faster than equals and can be used here!
            final BigInteger minBigInteger = maxBigInteger==leftBigInteger ? rightBigInteger : leftBigInteger;
            
            final BigInteger absoluteDistance = maxBigInteger.subtract(minBigInteger).abs();

            final BigInteger absLeftBigInteger = leftBigInteger.abs();
            final BigInteger absRightBigInteger = rightBigInteger.abs();
            final BigInteger minAbsBigInteger = absLeftBigInteger.compareTo(absRightBigInteger) < 0 ? absLeftBigInteger : absRightBigInteger;

            final BigDecimal relativeDistance = new BigDecimal(absoluteDistance)
                    .divide(new BigDecimal(minAbsBigInteger), roundingMode)
                    .subtract(BigDecimal.ONE);

            if(relativeDistance.compareTo(relativeEpsilon) <= 0) {
                final ImmutableValueSimilarity<BigInteger> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithPair(pair);
                return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
            } else {
                return new ImmutableValueComparisonResult(false, null, true,
                        pair);
            }  
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare two BigIntegers.", e, leftBigInteger, rightBigInteger);
        }
    }
}
