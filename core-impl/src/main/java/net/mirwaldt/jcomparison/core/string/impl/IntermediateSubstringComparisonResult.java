package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.util.SupplierHelper.createList;

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
public class IntermediateSubstringComparisonResult {
    private final Supplier<List> createListSupplier;
    private final Function<List, List> copyListFunction;

    private List<SubstringDifference> substringDifferences = Collections.emptyList();
    private List<SubstringSimilarity> substringSimilarities = Collections.emptyList();

    private List<SubstringDifference> unmodifiableSubstringDifferences = Collections.emptyList();
    private List<SubstringSimilarity> unmodifiableSubstringSimilarities = Collections.emptyList();

    public IntermediateSubstringComparisonResult(Supplier<List> createListSupplier, Function<List, List> copyListFunction) {
        this.createListSupplier = createListSupplier;
        this.copyListFunction = copyListFunction;
    }

    public List<SubstringDifference> readSubstringDifferences() {
        if(unmodifiableSubstringDifferences == null) {
            unmodifiableSubstringDifferences = Collections.unmodifiableList(substringDifferences);
        }
        return unmodifiableSubstringDifferences;
    }

    public List<SubstringSimilarity> readSubstringSimilarities() {
        if(unmodifiableSubstringSimilarities == null) {
            unmodifiableSubstringSimilarities = Collections.unmodifiableList(substringSimilarities);
        }
        return unmodifiableSubstringSimilarities;
    }

    public List<SubstringDifference> copyDifferentSubstrings() {
        return (List<SubstringDifference>) copyListFunction.apply(readSubstringDifferences());
    }

    public List<SubstringSimilarity> copySimilarSubstrings() {
        return (List<SubstringSimilarity>) copyListFunction.apply(readSubstringSimilarities());
    }

    public List<SubstringDifference> writeSubstringDifferences() {
        if(substringDifferences == Collections.<SubstringDifference>emptyList()) {
            substringDifferences = createList(createListSupplier);
            unmodifiableSubstringDifferences = null;
        }
        return substringDifferences;
    }

    public List<SubstringSimilarity> writeSimilarEntries() {
        if(substringSimilarities == Collections.<SubstringSimilarity>emptyList()) {
            substringSimilarities = createList(createListSupplier);
            unmodifiableSubstringSimilarities = null;
        }
        return substringSimilarities;
    }
}
