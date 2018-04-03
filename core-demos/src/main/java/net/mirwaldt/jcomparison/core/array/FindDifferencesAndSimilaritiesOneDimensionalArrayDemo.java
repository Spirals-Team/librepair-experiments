package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;

import java.util.Arrays;

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
public class FindDifferencesAndSimilaritiesOneDimensionalArrayDemo {
    public static void main(String[] args) throws ComparisonFailedException {
        final int[] leftIntArray = new int[] { 1, 2 };
        final int[] rightIntArray = new int[] { 1, 3, 4 };

        System.out.println("Left int array :" + Arrays.toString(leftIntArray));
        System.out.println("Right int array:" + Arrays.toString(rightIntArray));
        System.out.println();

        final ArrayComparator<int[]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        System.out.println("Similarities:");
        System.out.println();

        System.out.println(
                "Similar values : \t'" + comparisonResult.getSimilarities() + "'");
        System.out.println();

        System.out.println("Differences:");
        System.out.println();

        System.out.println("Additional elements only in left array : \t'" + comparisonResult.getDifferences().getAdditionalItemsOnlyInLeftArray() + "'");
        System.out.println("Additional elements only in right array : \t'" + comparisonResult.getDifferences().getAdditionalItemsOnlyInRightArray() + "'");
        System.out.println(
                "Different values : \t'" + comparisonResult.getDifferences().getDifferentElements() + "'");

        System.out.println();
    }
}
