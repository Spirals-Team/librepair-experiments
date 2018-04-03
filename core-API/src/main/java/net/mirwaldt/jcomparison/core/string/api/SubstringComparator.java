package net.mirwaldt.jcomparison.core.string.api;

import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.util.view.api.IntRange;

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
public interface SubstringComparator extends ItemComparator<String, SubstringComparisonResult>{
    enum ComparisonFeature {
        SUBSTRING_SIMILARITY,
        SUBSTRING_DIFFERENCE
    }

    default void checkRanges(String leftString, String rightString, IntRange leftStringRange, IntRange rightStringRange) throws ComparisonFailedException {
        if (!"".equals(leftString) && leftString.length() <= leftStringRange.getStartIndex()) {
            throw new ComparisonFailedException("The left start-index must be lower than the length of left string. The left-end index is '" + leftStringRange.getEndIndex() + "' but the length of left string is '" + leftString.length() + "'.", leftString, rightString);
        } else if (!"".equals(rightString) && rightString.length() <= rightStringRange.getStartIndex()) {
            throw new ComparisonFailedException("The right start-index must be lower than the length of right string. The right-end index is '" + rightStringRange.getEndIndex() + "' but the length of right string is '" + rightString.length() + "'.", leftString, rightString);
        } else if (leftString.length() < leftStringRange.getEndIndex()) {
            throw new ComparisonFailedException("The left end-index must be lower than or equal to the length of left string. The left-end index is '" + leftStringRange.getEndIndex() + "' but the length of left string is '" + leftString.length() + "'.", leftString, rightString);
        } else if (rightString.length() < rightStringRange.getEndIndex()) {
            throw new ComparisonFailedException("The right end-index must be lower than or equal to the length of right string. The right-end index is '" + rightStringRange.getEndIndex() + "' but the length of right string is '" + rightString.length() + "'.", leftString, rightString);
        }
    }

    SubstringComparisonResult compare(String leftString, String rightString, IntRange leftStringRange, IntRange rightStringRange) throws ComparisonFailedException;
}
