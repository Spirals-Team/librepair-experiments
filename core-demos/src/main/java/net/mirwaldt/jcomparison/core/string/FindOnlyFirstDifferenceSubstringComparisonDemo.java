package net.mirwaldt.jcomparison.core.string;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.SubstringComparisonResultFormatter;

import static net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators.createDefaultSubstringComparatorBuilder;

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
public class FindOnlyFirstDifferenceSubstringComparisonDemo {
    public static void main(String[] args) throws IllegalArgumentException, ComparisonFailedException {
        final String leftString = "This is a car and I drove it.";
        final String rightString = "That is awesome and I love it.";

        System.out.println("leftString : '" + leftString + "'");
        System.out.println("rightString : '" + rightString + "'");
        System.out.println();

        findOnlyFirstDifferenceInStrings(leftString, rightString);
    }

    public static void findOnlyFirstDifferenceInStrings(String leftString, String rightString)
            throws IllegalArgumentException, ComparisonFailedException {
        final SubstringComparator substringComparator = createDefaultSubstringComparatorBuilder().findDifferencesOnly().findFirstResultOnly().build();

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);

        final SubstringComparisonResultFormatter formatter = new SubstringComparisonResultFormatter();

        System.out.println("Differences:");

        final String[] differencesWithBrackets = formatter.formatSubstringComparisonResult(leftString, rightString, substringComparisonResult, true);
        System.out.println("leftString : \t'" + differencesWithBrackets[0] + "'");
        System.out.println("rightString : \t'" + differencesWithBrackets[1] + "'");
        System.out.println();

        System.out.println(substringComparisonResult.getDifferences().toString().replace(", ", ",\n"));
        System.out.println();

        System.out.println("Similarities:");
        System.out.println(substringComparisonResult.getSimilarities());
    }
}
