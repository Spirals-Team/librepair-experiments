package net.mirwaldt.jcomparison.core.util;

import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;

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
public class SubstringComparisonResultFormatter {

	public String[] formatSubstringComparisonResult(
			String leftString, String rightString,
			SubstringComparisonResult substringComparisonResult,
			boolean bracketsForDifferences) {
		final StringBuilder leftStringBuilder = new StringBuilder(leftString);
		final StringBuilder rightStringBuilder = new StringBuilder(rightString);

		int bracketsCounter = 0;
		
		if(bracketsForDifferences) {
			for(SubstringDifference substringDifference : substringComparisonResult.getDifferences()) {
				final int startPositionInLeftString = substringDifference.getPositionInLeftString() + bracketsCounter;

				final int endPositionInLeftString = startPositionInLeftString + substringDifference.getSubstringInLeftString().length();
				
				leftStringBuilder.insert(endPositionInLeftString, "]");					
				leftStringBuilder.insert(startPositionInLeftString, "[");
				
				final int startPositionInRightString = substringDifference.getPositionInRightString() + bracketsCounter;
				final int endPositionInRightString = startPositionInRightString + substringDifference.getSubstringInRightString().length();
				
				rightStringBuilder.insert(endPositionInRightString, "]");	
				rightStringBuilder.insert(startPositionInRightString, "[");
				
				bracketsCounter+=2;
			}
		} else {
			for(SubstringSimilarity substringSimilarity : substringComparisonResult.getSimilarities()) {
				final int startPositionInLeftString = substringSimilarity.getPositionInLeftString() + bracketsCounter;

				final int endPositionInLeftString = startPositionInLeftString + substringSimilarity.getSimilarSubstring().length();
				
				leftStringBuilder.insert(endPositionInLeftString, "]");					
				leftStringBuilder.insert(startPositionInLeftString, "[");
				
				final int startPositionInRightString = substringSimilarity.getPositionInRightString() + bracketsCounter;
				final int endPositionInRightString = startPositionInRightString + substringSimilarity.getSimilarSubstring().length();
				
				rightStringBuilder.insert(endPositionInRightString, "]");	
				rightStringBuilder.insert(startPositionInRightString, "[");
				
				bracketsCounter+=2;
			}
		}
		
		return new String[] { leftStringBuilder.toString(), rightStringBuilder.toString()};
	}
}
