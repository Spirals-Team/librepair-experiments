package net.mirwaldt.jcomparison.core.facade.version_001;

import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayComparator;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.impl.DefaultListComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.decorator.CastingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.SameTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.SameTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.InstanceOfOneTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.InstanceOfOneTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.facade.builder.*;
import net.mirwaldt.jcomparison.core.iterable.impl.DefaultEachWithEachComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;

import java.util.List;
import java.util.Map;
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
public class DefaultComparators {

    public static DefaultSubstringComparatorBuilder createDefaultSubstringComparatorBuilder() {
        return new DefaultSubstringComparatorBuilder().
                useDefaultCreateListSupplier().
                useDefaultCopyListFunction().
                readIntermediateResults().
                useDefaultImmutableResultFunction().
                findAllResults().
                findSimilaritiesAndDifferences().
                useDefaultWordDelimiter();
    }

    public static SubstringComparator createDefaultSubstringComparator() {
        return createDefaultSubstringComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSubstringComparator(boolean isStrict) {
        return createSafeDefaultSubstringComparator(createDefaultSubstringComparator(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSubstringComparator(DefaultSubstringComparatorBuilder substringComparatorBuilder, boolean isStrict) {
        return createSafeDefaultSubstringComparator(substringComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSubstringComparator(SubstringComparator substringComparator, boolean isStrict) {
        final CastingComparator<String> castingComparator = new CastingComparator<>(substringComparator, String.class);
        if(isStrict) {
            return new InstanceOfOneTypeStrictCheckingComparator<>(String.class, castingComparator);
        } else {
            return new InstanceOfOneTypeNiceCheckingComparator<>(String.class, castingComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }

    public static <ValueType> DefaultSetComparatorBuilder<ValueType> createDefaultSetComparatorBuilder() {
        return new DefaultSetComparatorBuilder<ValueType>().
                readIntermediateResultsForFinalResult().
                useDefaultCreateSetSupplier().
                useDefaultCopySetFunction().
                useDefaultImmutableResultFunction().
                allowAllFilter().
                findAllResults().
                findSimilaritiesAndDifferences();
    }

    public static <ValueType> SetComparator<ValueType> createDefaultSetComparator() {
        return DefaultComparators.<ValueType>createDefaultSetComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSetComparator(boolean isStrict) {
        return createSafeDefaultSetComparator(createDefaultSetComparator(), isStrict);
    }

    public static <ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSetComparator(DefaultSetComparatorBuilder<ValueType> setComparatorBuilder, boolean isStrict) {
        return createSafeDefaultSetComparator(setComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultSetComparator(SetComparator setComparator, boolean isStrict) {
        final CastingComparator<Set> castingComparator = new CastingComparator<>(setComparator, Set.class);
        if(isStrict) {
            return new InstanceOfOneTypeStrictCheckingComparator<>(Set.class, castingComparator);
        } else {
            return new InstanceOfOneTypeNiceCheckingComparator<>(Set.class, castingComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }

    public static <KeyType, ValueType> DefaultMapComparatorBuilder<KeyType, ValueType> createDefaultMapComparatorBuilder() {
        return new DefaultMapComparatorBuilder<KeyType, ValueType>().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultComparator().
                useDefaultExceptionHandler().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                findAllResults().
                allowAllFilter();
    }

    public static <KeyType, ValueType> MapComparator<KeyType, ValueType> createDefaultMapComparator() {
        return DefaultComparators.<KeyType, ValueType>createDefaultMapComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultMapComparator(boolean isStrict) {
        return createSafeDefaultMapComparator(createDefaultMapComparator(), isStrict);
    }

    public static <KeyType, ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultMapComparator(DefaultMapComparatorBuilder<KeyType, ValueType> mapComparatorBuilder, boolean isStrict) {
        return createSafeDefaultMapComparator(mapComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultMapComparator(MapComparator mapComparator, boolean isStrict) {
        final CastingComparator<Map> castingComparator = new CastingComparator<Map>(mapComparator, Map.class);
        if(isStrict) {
            return new InstanceOfOneTypeStrictCheckingComparator<>(Map.class, castingComparator);
        } else {
            return new InstanceOfOneTypeNiceCheckingComparator<>(Map.class, castingComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }

    public static <ValueType> DefaultListComparatorBuilder<ValueType> createDefaultListComparatorBuilder() {
        return new DefaultListComparatorBuilder<ValueType>().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultComparator().
                useDefaultExceptionHandler().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                allowAllFilter().
                findAllResults();
    }

    public static <ValueType> DefaultListComparator<ValueType> createDefaultListComparator() {
        return DefaultComparators.<ValueType>createDefaultListComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultListComparator(boolean isStrict) {
        return createTypeSafeDefaultListComparator(createDefaultListComparator(), isStrict);
    }

    public static <ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultListComparator(DefaultListComparatorBuilder<ValueType> listComparatorBuilder, boolean isStrict) {
        return createTypeSafeDefaultListComparator(listComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultListComparator(ListComparator listComparator, boolean isStrict) {
        final CastingComparator<List> castingComparator = new CastingComparator<List>(listComparator, List.class);
        if(isStrict) {
            return new InstanceOfOneTypeStrictCheckingComparator<>(List.class, castingComparator);
        } else {
            return new InstanceOfOneTypeNiceCheckingComparator<>(List.class, castingComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }

    public static <ArrayType> DefaultArrayComparatorBuilder<ArrayType> createDefaultArrayComparatorBuilder() {
        return new DefaultArrayComparatorBuilder<ArrayType>().
                useNoDeduplicator().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultElementsComparator().
                useDefaultExceptionHandler().
                useDefaultCycleProtectingPreComparatorFunction().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                findAllResults().
                considerAllPositions().
                allowAllElementsFilter().
                useDefaultCheckingArrayWrapperFunction();
    }

    public static <ArrayType> DefaultArrayComparator<ArrayType> createDefaultArrayComparator() {
        return DefaultComparators.<ArrayType>createDefaultArrayComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultArrayComparator(boolean isStrict) {
        return createTypeSafeDefaultArrayComparator(createDefaultArrayComparator(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultArrayComparator(DefaultArrayComparatorBuilder<Object> defaultArrayComparatorBuilder, boolean isStrict) {
        return createTypeSafeDefaultArrayComparator(defaultArrayComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultArrayComparator(DefaultArrayComparator<Object> defaultArrayComparator, boolean isStrict) {
        if(isStrict) {
            return new SameTypeStrictCheckingComparator<>(defaultArrayComparator);
        } else {
            return new SameTypeNiceCheckingComparator<>(defaultArrayComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }
    
    public static DefaultEachWithEachComparatorBuilder createDefaultEachWithEachComparatorBuilder() {
        return new DefaultEachWithEachComparatorBuilder().
                readIntermediateResultsForFinalResult().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultComparator().
                useDefaultImmutableResultFunction().
                allowAllPairs().
                findAllResults();
    }

    public static DefaultEachWithEachComparator createDefaultEachWithEachComparator() {
        return DefaultComparators.createDefaultEachWithEachComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultEachWithEachComparator(boolean isStrict) {
        return createSafeDefaultUniquesListComparator(createDefaultEachWithEachComparator(), isStrict);
    }

    public static <ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultUniquesListComparator(DefaultEachWithEachComparatorBuilder eachWithEachComparatorBuilder, boolean isStrict) {
        return createSafeDefaultUniquesListComparator(eachWithEachComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultUniquesListComparator(DefaultEachWithEachComparator eachWithEachComparator, boolean isStrict) {
        final CastingComparator<Iterable> castingComparator = new CastingComparator<>(eachWithEachComparator, Iterable.class);
        if(isStrict) {
            return new InstanceOfOneTypeStrictCheckingComparator<>(Iterable.class, castingComparator);
        } else {
            return new InstanceOfOneTypeNiceCheckingComparator<>(Iterable.class, castingComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }
}
