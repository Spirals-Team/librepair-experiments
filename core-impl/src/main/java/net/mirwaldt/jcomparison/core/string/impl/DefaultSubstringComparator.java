package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.view.api.IntRange;
import net.mirwaldt.jcomparison.core.util.view.impl.ImmutableIntRange;

import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
 *
 * compares two strings by finding the longest similar and different substrings.
 * E.g. 
 * 1) This [i]s a[n] [in]te[res]t[ing] t[est].     
 *         [1]	 [2] [3]   [4]   [5]    [6] 
 * 2) This [wa]s a[] []te[x]t[,] t[oo]. 
 *         [1]	  [2][3] [4] [5]  [6] 
 *         
 * Be careful:
 * 1) a[b]c[de]g
 *     [1] [2]
 * 2) a[e]c[fb]g
 *     [1] [2]
 */
@NotNullSafe
public class DefaultSubstringComparator implements SubstringComparator {
    public static final IntPredicate NO_WORD_DELIMITER = (character) -> false;

    private final Supplier<IntermediateSubstringComparisonResult> intermediateResultField;
    private final Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateSubstringComparisonResult> stopPredicate;
    private final IntPredicate isWordDelimiter;

    public DefaultSubstringComparator(Supplier<IntermediateSubstringComparisonResult> intermediateResultField, Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction, EnumSet<ComparisonFeature> comparisonFeatures, Predicate<IntermediateSubstringComparisonResult> stopPredicate, IntPredicate isWordDelimiter) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.isWordDelimiter = isWordDelimiter;
    }

    @Override
    public SubstringComparisonResult compare(String leftString, 
                                             String rightString, 
                                             VisitedObjectsTrace visitedObjectsTrace,
                                             ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        return compare(leftString, rightString);
    }

    @Override
    public SubstringComparisonResult compare(String leftString, String rightString) throws ComparisonFailedException {
        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, leftString.length());
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, rightString.length());

        return compare(leftString, rightString, leftIntRange, rightIntRange);
    }

    @Override
    public SubstringComparisonResult compare(String leftString, String rightString, IntRange leftStringRange, IntRange rightStringRange) throws ComparisonFailedException {
        checkRanges(leftString, rightString, leftStringRange, rightStringRange);

        try {
            final IntermediateSubstringComparisonResult intermediateResult = intermediateResultField.get();

            new DefaultSubstringComparator.InternalSubstringComparator(leftString, rightString, leftStringRange, rightStringRange, intermediateResult).compare();

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both strings.", e, leftString, rightString);
        }
    }

    class InternalSubstringComparator {
        private final IntermediateSubstringComparisonResult intermediateResult;

        private int startPositionOfSubstringInLeftString;
        private int endPositionOfSubstringInLeftString;
        private final int lastPositionOfLeftString;

        private int startPositionOfSubstringInRightString;
        private int endPositionOfSubstringInRightString;
        private final int lastPositionOfRightString;

        private boolean areLettersDifferent = true;

        private final String leftString;
        private final String rightString;

        public InternalSubstringComparator(String leftString, 
                                           String rightString, 
                                           IntRange leftStringRange, 
                                           IntRange rightStringRange, 
                                           IntermediateSubstringComparisonResult intermediateResult) {
            super();
            this.leftString = leftString;
            this.rightString = rightString;

            endPositionOfSubstringInLeftString = leftStringRange.getStartIndex();
            startPositionOfSubstringInLeftString = endPositionOfSubstringInLeftString;
            lastPositionOfLeftString = leftStringRange.getEndIndex();

            endPositionOfSubstringInRightString = rightStringRange.getStartIndex();
            startPositionOfSubstringInRightString = endPositionOfSubstringInRightString;
            lastPositionOfRightString = rightStringRange.getEndIndex();

            this.intermediateResult = intermediateResult;
        }

        public void compare() {
            if(isWordDelimiter == NO_WORD_DELIMITER) {
                compare(lastPositionOfLeftString, lastPositionOfRightString);
            } else {
                compareWordByWord();
            }
        }

        private void compareWordByWord() {
            int leadingDelimitersOfWordInLeftString = leadingDelimitersOfWordInString(leftString, startPositionOfSubstringInLeftString, lastPositionOfLeftString);
            startPositionOfSubstringInLeftString = 
                    Math.min(startPositionOfSubstringInLeftString + leadingDelimitersOfWordInLeftString, lastPositionOfLeftString);
            endPositionOfSubstringInLeftString = startPositionOfSubstringInLeftString;
            
            int leadingDelimitersOfWordInRightString = leadingDelimitersOfWordInString(rightString, startPositionOfSubstringInRightString, lastPositionOfRightString);
            startPositionOfSubstringInRightString = 
                    Math.min(startPositionOfSubstringInRightString + leadingDelimitersOfWordInRightString, lastPositionOfRightString);
            endPositionOfSubstringInRightString = startPositionOfSubstringInRightString;
            
            while((endPositionOfSubstringInLeftString < lastPositionOfLeftString)
                    && (endPositionOfSubstringInRightString < lastPositionOfRightString)) {
                final int exclusiveEndIndexOfNextLeftWord = exclusiveEndIndexOfNextWord(leftString, endPositionOfSubstringInLeftString, lastPositionOfLeftString);
                final int exclusiveEndIndexOfNextRightWord = exclusiveEndIndexOfNextWord(rightString, endPositionOfSubstringInRightString, lastPositionOfRightString);

                areLettersDifferent = true;
                
                compare(exclusiveEndIndexOfNextLeftWord, exclusiveEndIndexOfNextRightWord);

                leadingDelimitersOfWordInLeftString = leadingDelimitersOfWordInString(leftString, exclusiveEndIndexOfNextLeftWord + 1, lastPositionOfLeftString);
                startPositionOfSubstringInLeftString = Math.min(exclusiveEndIndexOfNextLeftWord + 1 + leadingDelimitersOfWordInLeftString, lastPositionOfLeftString);
                endPositionOfSubstringInLeftString = startPositionOfSubstringInLeftString;
                
                leadingDelimitersOfWordInRightString = leadingDelimitersOfWordInString(rightString, exclusiveEndIndexOfNextRightWord + 1, lastPositionOfRightString);
                startPositionOfSubstringInRightString = Math.min(exclusiveEndIndexOfNextRightWord + 1 + leadingDelimitersOfWordInRightString, lastPositionOfRightString);
                endPositionOfSubstringInRightString = startPositionOfSubstringInRightString;
            }
            
            handleEnd(lastPositionOfLeftString, lastPositionOfRightString);
        }

        private int leadingDelimitersOfWordInString(String string, int startPosition, int lastPositionOfString) {
            int numberOfDelimiters = 0;
            for (int index = startPosition; index < lastPositionOfString; index++) {
                final char currentCharOfString = string.charAt(index);
                if(isWordDelimiter.test(currentCharOfString)) {
                    numberOfDelimiters++;
                } else {
                    break;
                }
            }
            return numberOfDelimiters;
        }

        private void compare(int exclusiveEndIndexOfNextLeftWord, int exclusiveEndIndexOfNextRightWord) {
            while ((endPositionOfSubstringInLeftString < exclusiveEndIndexOfNextLeftWord)
                    && (endPositionOfSubstringInRightString < exclusiveEndIndexOfNextRightWord)) {

                final char currentCharOfLeftString = leftString.charAt(endPositionOfSubstringInLeftString);
                final char currentCharOfRightString = rightString.charAt(endPositionOfSubstringInRightString);

                if (currentCharOfLeftString == currentCharOfRightString) {
                    if (areLettersDifferent) {
                        if (addSubstringDifference()) {
                            break;
                        }
                    }
                    areLettersDifferent = false;
                    handleEqualChars();
                } else {
                    if (!areLettersDifferent) {
                        if (addSubstringSimilarity()) {
                            break;
                        }
                    }
                    areLettersDifferent = true;
                    handleDifferentChars(currentCharOfLeftString, currentCharOfRightString, exclusiveEndIndexOfNextLeftWord, exclusiveEndIndexOfNextRightWord);
                }
            }

            handleEnd(exclusiveEndIndexOfNextLeftWord, exclusiveEndIndexOfNextRightWord);
        }

        private int exclusiveEndIndexOfNextWord(String string, int endPositionOfSubstringInString, int maxPositionOfString) {
            for (int index = endPositionOfSubstringInString; index < maxPositionOfString; index++) {
                final char currentCharOfString = string.charAt(index);
                if(isWordDelimiter.test(currentCharOfString)) {
                    return index;
                }
            }
            return maxPositionOfString;
        }

        private void handleEnd(int maxPositionOfLeftString, int maxPositionOfRightString) {
            if ((endPositionOfSubstringInLeftString == maxPositionOfLeftString)
                    || (endPositionOfSubstringInRightString == maxPositionOfRightString)) {
                if (!areLettersDifferent) {
                    if (addSubstringSimilarity()) {
                        return;
                    }
                }

                handleEndOfAtLeastOneString(maxPositionOfLeftString, maxPositionOfRightString);

                if (areLettersDifferent) {
                    addSubstringDifference();
                }
            }
        }

        private void handleEndOfAtLeastOneString(int maxPositionOfLeftString, int maxPositionOfRightString) {
            endPositionOfSubstringInLeftString = maxPositionOfLeftString;
            endPositionOfSubstringInRightString = maxPositionOfRightString;

            areLettersDifferent = areLettersDifferent
                    || endPositionOfSubstringInLeftString != endPositionOfSubstringInRightString;
        }

        private void handleDifferentChars(char currentCharOfLeftString, char currentCharOfRightString, int maxPositionOfLeftString, int maxPositionOfRightString) {
            //TODO: implement a indexOf-method for String that also accepts an endIndex
            int absoluteIndexOfCharOfLeftStringInRightString = rightString.indexOf(currentCharOfLeftString,
                    startPositionOfSubstringInRightString);
            int absoluteIndexOfCharOfRightStringInLeftString = leftString.indexOf(currentCharOfRightString,
                    startPositionOfSubstringInLeftString);
            
            if(maxPositionOfRightString < absoluteIndexOfCharOfLeftStringInRightString) {
                absoluteIndexOfCharOfLeftStringInRightString = -1;
            }
            
            if(maxPositionOfLeftString < absoluteIndexOfCharOfRightStringInLeftString) {
                absoluteIndexOfCharOfRightStringInLeftString = -1;
            }
            
            if ((absoluteIndexOfCharOfLeftStringInRightString == -1)
                    && (absoluteIndexOfCharOfRightStringInLeftString == -1)) {
                // no char was found at all -> continue search
                endPositionOfSubstringInLeftString++;
                endPositionOfSubstringInRightString++;
            } else {
                // at least one char was found
                int relativeIndexOfCharOfLeftStringInRightString = absoluteIndexOfCharOfLeftStringInRightString
                        - startPositionOfSubstringInRightString;
                int relativeIndexOfCharOfRightStringInLeftString = absoluteIndexOfCharOfRightStringInLeftString
                        - startPositionOfSubstringInLeftString;

                if ((absoluteIndexOfCharOfLeftStringInRightString > -1)
                        && (absoluteIndexOfCharOfRightStringInLeftString == -1)) {
                    endPositionOfSubstringInRightString = absoluteIndexOfCharOfLeftStringInRightString;
                } else if ((absoluteIndexOfCharOfLeftStringInRightString == -1)
                        && (absoluteIndexOfCharOfRightStringInLeftString > -1)) {
                    endPositionOfSubstringInLeftString = absoluteIndexOfCharOfRightStringInLeftString;
                } else if (relativeIndexOfCharOfLeftStringInRightString < relativeIndexOfCharOfRightStringInLeftString) {
                    endPositionOfSubstringInRightString = absoluteIndexOfCharOfLeftStringInRightString;
                } else if (relativeIndexOfCharOfLeftStringInRightString > relativeIndexOfCharOfRightStringInLeftString) {
                    endPositionOfSubstringInLeftString = absoluteIndexOfCharOfRightStringInLeftString;
                } else if (relativeIndexOfCharOfLeftStringInRightString == relativeIndexOfCharOfRightStringInLeftString) {
                    endPositionOfSubstringInLeftString++;
                    endPositionOfSubstringInRightString++;
                }
            }
        }

        private void handleEqualChars() {
            endPositionOfSubstringInLeftString++;
            endPositionOfSubstringInRightString++;
        }

        private boolean addSubstringSimilarity() {
            final int lengthOfDiffSubstringInLeftString = endPositionOfSubstringInLeftString
                    - startPositionOfSubstringInLeftString;

            if (0 < lengthOfDiffSubstringInLeftString) {
                if (comparisonFeatures.contains(ComparisonFeature.SUBSTRING_SIMILARITY)) {
                    final String commonSubstring = leftString.substring(startPositionOfSubstringInLeftString,
                            endPositionOfSubstringInLeftString);

                    intermediateResult.writeSimilarEntries().add(new ImmutableSubstringSimilarity(startPositionOfSubstringInLeftString,
                            startPositionOfSubstringInRightString, commonSubstring));

                }

                startPositionOfSubstringInLeftString = endPositionOfSubstringInLeftString;
                startPositionOfSubstringInRightString = endPositionOfSubstringInRightString;

                if (comparisonFeatures.contains(ComparisonFeature.SUBSTRING_SIMILARITY) && stopPredicate.test(intermediateResult)) {
                    return true;
                }
            }

            return false;
        }

        private boolean addSubstringDifference() {
            final int lengthOfSubstringInLeftString = endPositionOfSubstringInLeftString
                    - startPositionOfSubstringInLeftString;

            final int lengthOfSubstringInRightString = endPositionOfSubstringInRightString
                    - startPositionOfSubstringInRightString;

            if (areLettersDifferent && ((0 < lengthOfSubstringInLeftString) || (0 < lengthOfSubstringInRightString))) {
                // end of new substring diff found

                if (comparisonFeatures.contains(ComparisonFeature.SUBSTRING_DIFFERENCE)) {
                    final String diffSubstringInLeftString = leftString.substring(startPositionOfSubstringInLeftString,
                            endPositionOfSubstringInLeftString);

                    final String diffSubstringInRightString = rightString
                            .substring(startPositionOfSubstringInRightString, endPositionOfSubstringInRightString);

                    intermediateResult.writeSubstringDifferences().add(new ImmutableSubstringDiff(startPositionOfSubstringInLeftString,
                            startPositionOfSubstringInRightString, diffSubstringInLeftString,
                            diffSubstringInRightString));
                }

                startPositionOfSubstringInLeftString = endPositionOfSubstringInLeftString;
                startPositionOfSubstringInRightString = endPositionOfSubstringInRightString;

                if (comparisonFeatures.contains(ComparisonFeature.SUBSTRING_DIFFERENCE) && stopPredicate.test(intermediateResult)) {
                    return true;
                }
            }

            return false;
        }
    }
}
