package net.mirwaldt.jcomparison.core.collection.set;

import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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
public class FindDifferencesAndSimilaritiesSetComparisonDemo {
    public static void main(String[] args) throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new LinkedHashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> rightSet = new LinkedHashSet<>(Arrays.asList(3, null));

        System.out.println("Left set :" + leftSet);
        System.out.println("Right set :" + rightSet);
        System.out.println();

        findDifferencesAndSimilaritiesInSets(leftSet, rightSet);
    }

    private static void findDifferencesAndSimilaritiesInSets(Set<Integer> leftSet, Set<Integer> rightSet)
            throws IllegalArgumentException, ComparisonFailedException {
        final SetComparator<Integer> intSetComparator = DefaultComparators.<Integer>createDefaultSetComparatorBuilder().build();

        final SetComparisonResult<Integer> intSetComparisonResult = intSetComparator.compare(leftSet, rightSet);

        System.out.println("Differences:");
        System.out.println("Elements only in left set :  " + intSetComparisonResult.getDifferences().getElementsOnlyInLeftSet());
        System.out.println("Elements only in right set :" + intSetComparisonResult.getDifferences().getElementsOnlyInRightSet());
        System.out.println();

        System.out.println("Similarities:");
        System.out.println("Elements in both sets: " + intSetComparisonResult.getSimilarities());
    }
}
