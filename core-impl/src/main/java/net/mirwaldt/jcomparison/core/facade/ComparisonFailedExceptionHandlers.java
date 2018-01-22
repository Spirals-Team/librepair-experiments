package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.exception.ResultsCollectingComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;

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
public class ComparisonFailedExceptionHandlers {

    public static final ComparisonFailedExceptionHandler RETHROWING_HANDLER = (e) -> { throw e; };

    public static final ComparisonFailedExceptionHandler RESULTS_COLLECTING_HANDLER = (e) -> {
        if(e instanceof ResultsCollectingComparisonFailedException) {
            throw e;
        } else {
            throw new ResultsCollectingComparisonFailedException(e, ComparisonResults.emptyComparisonResult());
        }
    };

    public static final ComparisonFailedExceptionHandler EMPTY_HANDLER = (e) -> ComparisonResults.emptyComparisonResult();

    public static final ComparisonFailedExceptionHandler SKIPPING_HANDLER = (e) -> ComparisonResults.skipComparisonResult();

    public static final ComparisonFailedExceptionHandler STOPPING_HANDLER = (e) -> ComparisonResults.stopComparisonResult();
}
