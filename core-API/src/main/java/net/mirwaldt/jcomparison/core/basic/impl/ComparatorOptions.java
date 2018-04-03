package net.mirwaldt.jcomparison.core.basic.impl;

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
 * These options allow optimzations.
 *
 * E.g. if canReturnMutuableResult is true,
 * mutuable results can be used to avoid heap pollution 
 * by creating to many comparison result objects 
 * that are used only for a short moment. 
 *
 * E.g. if doValueComparison is false,
 * the comparator can immediately return an empty comparison result.
 */
public class ComparatorOptions {
    private final boolean canReturnMutuableResult;
    private final boolean doValueComparison;
    private final boolean doNonValueComparison;

    public ComparatorOptions() {
        this(false, true, true);
    }

    public ComparatorOptions(boolean canReturnMutuableResult, boolean doValueComparison, boolean doNonValueComparison) {
        this.canReturnMutuableResult = canReturnMutuableResult;
        this.doValueComparison = doValueComparison;
        this.doNonValueComparison = doNonValueComparison;
    }

    public boolean canReturnMutuableResult() {
        return canReturnMutuableResult;
    }

    public boolean doValueComparison() {
        return doValueComparison;
    }

    public boolean doNonValueComparison() {
        return doNonValueComparison;
    }
}
