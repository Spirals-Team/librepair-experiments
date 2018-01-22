package net.mirwaldt.jcomparison.core.facade.version_001;

import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayComparator;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.impl.DefaultDuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.decorator.CastingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.ExactTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.ExactTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.InstanceOfSameTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.InstanceOfSameTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.facade.*;
import net.mirwaldt.jcomparison.core.iterable.impl.DefaultEachWithEachComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.object.impl.DefaultObjectComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;

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

    public static DefaultLongestSubstringComparatorBuilder createDefaultLongestSubstringComparatorBuilder() {
        return new DefaultLongestSubstringComparatorBuilder().
                useDefaultCreateListSupplier().
                useDefaultCopyListFunction().
                readIntermediateResults().
                useDefaultImmutableResultFunction().
                findAllResults().
                findSimilaritiesAndDifferences().
                useDefaultWordDelimiter();
    }

    public static SubstringComparator createDefaultLongestSubstringComparator() {
        return createDefaultLongestSubstringComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultLongestSubstringComparator(boolean isStrict) {
        return createSafeDefaultLongestSubstringComparator(createDefaultLongestSubstringComparator(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultLongestSubstringComparator(DefaultLongestSubstringComparatorBuilder substringComparatorBuilder, boolean isStrict) {
        return createSafeDefaultLongestSubstringComparator(substringComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultLongestSubstringComparator(SubstringComparator longestSubstringComparator, boolean isStrict) {
        final CastingComparator<String> castingComparator = new CastingComparator<>(longestSubstringComparator, String.class);
        if(isStrict) {
            return new InstanceOfSameTypeStrictCheckingComparator<>(String.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(String.class, castingComparator, ItemComparators.equalsComparator());
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
            return new InstanceOfSameTypeStrictCheckingComparator<>(Set.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(Set.class, castingComparator, ItemComparators.equalsComparator());
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
            return new InstanceOfSameTypeStrictCheckingComparator<>(Map.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(Map.class, castingComparator, ItemComparators.equalsComparator());
        }
    }

    public static <ValueType> DefaultDuplicatesListComparatorBuilder<ValueType> createDefaultDuplicatesListComparatorBuilder() {
        return new DefaultDuplicatesListComparatorBuilder<ValueType>().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultComparator().
                useDefaultExceptionHandler().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                findAllResults();
    }

    public static <ValueType> DefaultDuplicatesListComparator<ValueType> createDefaultDuplicatesListComparator() {
        return DefaultComparators.<ValueType>createDefaultDuplicatesListComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultDuplicatesListComparator(boolean isStrict) {
        return createTypeSafeDefaultDuplicatesListComparator(createDefaultDuplicatesListComparator(), isStrict);
    }

    public static <ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultDuplicatesListComparator(DefaultDuplicatesListComparatorBuilder<ValueType> duplicatesListComparatorBuilder, boolean isStrict) {
        return createTypeSafeDefaultDuplicatesListComparator(duplicatesListComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultDuplicatesListComparator(DuplicatesListComparator duplicatesListComparator, boolean isStrict) {
        final CastingComparator<List> castingComparator = new CastingComparator<List>(duplicatesListComparator, List.class);
        if(isStrict) {
            return new InstanceOfSameTypeStrictCheckingComparator<>(List.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(List.class, castingComparator, ItemComparators.equalsComparator());
        }
    }

    public static <ValueType> DefaultUniquesListComparatorBuilder<ValueType> createDefaultUniquesListComparatorBuilder() {
        return new DefaultUniquesListComparatorBuilder<ValueType>().
                readIntermediateResultsForFinalResult().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
                useDefaultImmutableResultFunction().
                allowAllFilter().
                findAllResults().
                findSimilaritiesAndDifferences();
    }

    public static <ValueType> UniquesListComparator<ValueType> createDefaultUniquesListComparator() {
        return DefaultComparators.<ValueType>createDefaultUniquesListComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultUniquesListComparator(boolean isStrict) {
        return createSafeDefaultUniquesListComparator(createDefaultUniquesListComparator(), isStrict);
    }

    public static <ValueType> ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultUniquesListComparator(DefaultUniquesListComparatorBuilder<ValueType> uniquesListComparatorBuilder, boolean isStrict) {
        return createSafeDefaultUniquesListComparator(uniquesListComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createSafeDefaultUniquesListComparator(UniquesListComparator uniquesListComparator, boolean isStrict) {
        final CastingComparator<List> castingComparator = new CastingComparator<List>(uniquesListComparator, List.class);
        if(isStrict) {
            return new InstanceOfSameTypeStrictCheckingComparator<>(List.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(List.class, castingComparator, ItemComparators.equalsComparator());
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
                allowAllElementsFilter();
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
            return new ExactTypeStrictCheckingComparator<>(defaultArrayComparator);
        } else {
            return new ExactTypeNiceCheckingComparator<>(defaultArrayComparator, ItemComparators.equalsComparator());
        }
    }

    public static <ObjectType> DefaultObjectComparatorBuilder<ObjectType> createDefaultObjectComparatorBuilder() {
        return new DefaultObjectComparatorBuilder<ObjectType>().
                useNoDeduplicator().
                useDefaultCopyMapFunction().
                useDefaultFieldValueAccessor().
                useDefaultExceptionHandler().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                findAllResults().
                allowAllFields().
                useDefaultComparatorProvider();
    }

    public static <ObjectType> DefaultObjectComparator<ObjectType> createDefaultObjectComparator() {
        return DefaultComparators.<ObjectType>createDefaultObjectComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultObjectComparator(DefaultObjectComparatorBuilder<Object> defaultObjectComparatorBuilder, boolean isStrict) {
        return createTypeSafeDefaultObjectComparator(defaultObjectComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultObjectComparator(DefaultObjectComparator<Object> defaultObjectComparator, boolean isStrict) {
        if(isStrict) {
            return new ExactTypeStrictCheckingComparator<>(defaultObjectComparator);
        } else {
            return new ExactTypeNiceCheckingComparator<>(defaultObjectComparator, ItemComparators.equalsComparator());
        }
    }

    public static DefaultEachWithEachComparatorBuilder createDefaultEachWithEachComparatorBuilder() {
        return new DefaultEachWithEachComparatorBuilder().
                readIntermediateResultsForFinalResult().
                useDefaultCreateMapSupplier().
                useDefaultCopyMapFunction().
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
            return new InstanceOfSameTypeStrictCheckingComparator<>(Iterable.class, castingComparator);
        } else {
            return new InstanceOfSameTypeNiceCheckingComparator<>(Iterable.class, castingComparator, ItemComparators.equalsComparator());
        }
    }
}
