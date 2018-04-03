package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
public class FindDifferencesAndSimilaritiesTwoDimensionalArrayDemo {
    public static void main(String[] args) throws ComparisonFailedException {
        final double[][] leftDoubleArray = new double[][] { {}, { 1.0d , 2.0d, 3.3d }, {} };
        final double[][] rightDoubleArray = new double[][] { null, { 1.0d, 2.1d }, { 4.5 }};

        System.out.println("Left double array :" + Arrays.deepToString(leftDoubleArray));
        System.out.println("Right double array:" + Arrays.deepToString(rightDoubleArray));
        System.out.println();

        final ArrayComparator<double[][]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftDoubleArray, rightDoubleArray);

        System.out.println("Similarities:");
        System.out.println();

        System.out.println(
                "Similar values : \t'" + getMapAsString(comparisonResult.getSimilarities()) + "'");
        System.out.println();

        System.out.println("Differences:");
        System.out.println();

        System.out.println("Additional elements only in left array : \t'" + getMapAsString(comparisonResult.getDifferences().getAdditionalItemsOnlyInLeftArray()) + "'");
        System.out.println("Additional elements only in right array : \t'" + getMapAsString(comparisonResult.getDifferences().getAdditionalItemsOnlyInRightArray()) + "'");
        System.out.println(
                "Different values : \t'" + getMapAsString(comparisonResult.getDifferences().getDifferentElements()) + "'");

        System.out.println();
    }

    public static String getMapAsString(Map<List<Integer>, ?> map) {
        StringJoiner stringJoiner = new StringJoiner(",");
        map.forEach((key, value) -> stringJoiner.add(key + "=" + value));
        return stringJoiner.toString();
    }
}
