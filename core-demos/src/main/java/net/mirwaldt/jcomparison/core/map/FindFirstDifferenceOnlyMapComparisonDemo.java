package net.mirwaldt.jcomparison.core.map;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;

import java.util.LinkedHashMap;
import java.util.Map;

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
public class FindFirstDifferenceOnlyMapComparisonDemo {

    public static void main(String[] args)
            throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "3");
        leftMap.put(5, "5");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(1, "1");
        rightMap.put(2, "4");
        rightMap.put(6, "6");

        System.out.println("Left map :" + leftMap);
        System.out.println("Right map :" + rightMap);
        System.out.println();

        findOnlyFirstDifferenceInMaps(leftMap, rightMap);
    }

    public static void findOnlyFirstDifferenceInMaps(Map<Integer, String> leftMap, Map<Integer, String> rightMap)
            throws IllegalArgumentException, ComparisonFailedException {
        final MapComparator<Integer, String> intToStringMapComparator =
                DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder().findDifferencesOnly().findFirstResultOnly().build();

        final MapComparisonResult<Integer, String> intToStringMapComparisonResult = intToStringMapComparator.compare(leftMap, rightMap);

        System.out.println("Differences:");
        System.out.println("entries only in left map :  " + intToStringMapComparisonResult.getDifferences().getEntriesOnlyInLeftMap());
        System.out.println("changed entries :" + intToStringMapComparisonResult.getDifferences().getDifferentEntries());
        System.out.println("entries only in right map :" + intToStringMapComparisonResult.getDifferences().getEntriesOnlyInRightMap());
        System.out.println();

        System.out.println("Similarities:");
        System.out.println("similar entries: " + intToStringMapComparisonResult.getSimilarities());
    }
}
