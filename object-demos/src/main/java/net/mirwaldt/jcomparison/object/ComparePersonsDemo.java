package net.mirwaldt.jcomparison.object;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;
import net.mirwaldt.jcomparison.core.decorator.IdentityHandlingComparator;
import net.mirwaldt.jcomparison.core.decorator.NullAcceptingComparator;
import net.mirwaldt.jcomparison.core.basic.impl.ExceptionAtCycleComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.SubstringComparisonResultFormatter;
import net.mirwaldt.jcomparison.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.object.facade.builder.DefaultObjectComparatorBuilder;
import net.mirwaldt.jcomparison.object.facade.version_001.DefaultComparatorProvider;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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
public class ComparePersonsDemo {

    public static void main(String[] args)
            throws NullPointerException, ComparisonFailedException, NoSuchFieldException, SecurityException {
        final EnumMap<Person.Feature, String> featuresOfPersonA = new EnumMap<>(Person.Feature.class);
        featuresOfPersonA.put(Person.Feature.HAIR_COLOR, "brown");
        featuresOfPersonA.put(Person.Feature.SKIN_COLOR, "white");
        featuresOfPersonA.put(Person.Feature.WRISTWATCH_BRAND, "CASIO");

        final Address addressOfPersonA = new Address("August-Exter-Str.", 10, 81245, "Munich", "GERMANY");

        final Person personA = new Person("Michael", Person.Sex.MALE, 32, false, 35.12, addressOfPersonA, featuresOfPersonA,
                new HashSet<>(Arrays.asList("Swimming", "Piano")));

        final EnumMap<Person.Feature, String> featuresOfPersonB = new EnumMap<>(Person.Feature.class);
        featuresOfPersonB.put(Person.Feature.HAIR_COLOR, "brown");
        featuresOfPersonB.put(Person.Feature.SKIN_COLOR, "black");
        featuresOfPersonB.put(Person.Feature.TATTOO_TEXT, "Mum");

        final Address addressOfPersonB = new Address("August-Exter-Str.", 12, 81245, "Munich", "GERMANY");

        final Person personB = new Person("Michel", Person.Sex.MALE, 45, true, 148.96, addressOfPersonB, featuresOfPersonB,
                new HashSet<>(Arrays.asList("Swimming", "Tennis", "Jogging")));

        final Predicate<Field> fieldPredicate = (field) -> !field.getName().equals("isMarried");

        final Function<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>, ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> preComparatorFunction =
                (itemComparator) -> new NullAcceptingComparator<>(new IdentityHandlingComparator<>(itemComparator), Function.identity());

        final DefaultComparatorProvider comparatorProvider = new DefaultComparatorProvider(
                true, false, preComparatorFunction,
                new ExceptionAtCycleComparator(), (builder, internalComparatorProvider)-> {
                    if(builder instanceof DefaultObjectComparatorBuilder) {
                        DefaultObjectComparatorBuilder defaultObjectComparatorBuilder = (DefaultObjectComparatorBuilder) builder;
                        defaultObjectComparatorBuilder.filterFields(fieldPredicate);
                    }
            }
        );

        ItemComparator<Object, ? extends ComparisonResult<?,?,?>> personComparator = comparatorProvider.provideComparator(Person.class, personA, personB);

        final ObjectComparisonResult personComparisonResult = (ObjectComparisonResult) personComparator.compare(personA, personB);
        final Map<Field, ComparisonResult<?, ?, ?>> fieldComparisonResult = personComparisonResult
                .getComparisonResults();

        printSimpleComparisonResult(personComparisonResult);

        System.out.println("Comparisons:");
        System.out.println();

        final Field nameField = Person.class.getDeclaredField("name");
        System.out.println("name:");
        final SubstringComparisonResultFormatter formatter = new SubstringComparisonResultFormatter();
        final String[] differencesWithBrackets = formatter.formatSubstringComparisonResult(personA.getName(),
                personB.getName(), (SubstringComparisonResult) fieldComparisonResult.get(nameField), true);
        System.out.println("personA : \t'" + differencesWithBrackets[0] + "'");
        System.out.println("personB : \t'" + differencesWithBrackets[1] + "'");
        System.out.println();

        final Field featuresField = Person.class.getDeclaredField("features");
        System.out.println("Features:");
        final MapComparisonResult<?, ?> mapComparisonResult = (MapComparisonResult<?, ?>) fieldComparisonResult
                .get(featuresField);

        final MapDifference<?, ?> mapDifference = mapComparisonResult.getDifferences();
        if (mapDifference != null) {
            System.out.println("Feature of personA only : \t'" + mapDifference.getEntriesOnlyInLeftMap() + "'");
            System.out.println("Feature of personB only : \t'" + mapDifference.getEntriesOnlyInRightMap() + "'");
            System.out.println(
                    "personA and personB have different features : \t'" + mapDifference.getDifferentEntries() + "'");
        }

        final Map<?, ?> mapSimilarity = mapComparisonResult.getSimilarities();
        if (mapSimilarity != null) {
            System.out.println(
                    "personA and personB have similar features: \t'" + mapSimilarity + "'");
        }
        System.out.println();

        final Field leisureActivities = Person.class.getDeclaredField("leisureActivities");
        System.out.println("Leisure activities:");
        final SetComparisonResult<?> setComparisonResult = (SetComparisonResult<?>) fieldComparisonResult
                .get(leisureActivities);

        final SetDifference<?> setDifference = setComparisonResult.getDifferences();
        if (mapDifference != null) {
            System.out.println(
                    "Leisure activities of personA only : \t'" + setDifference.getElementsOnlyInLeftSet() + "'");
            System.out.println(
                    "Leisure activities of personB only : \t'" + setDifference.getElementsOnlyInRightSet() + "'");
        }

        final Set<?> setSimilarity = setComparisonResult.getSimilarities();
        if (mapSimilarity != null) {
            System.out.println("personA and personB have similar leisureActivities: \t'"
                    + setSimilarity + "'");
        }
        System.out.println();
        System.out.println();


        final Field addressField = Person.class.getDeclaredField("address");
        ObjectComparisonResult objectComparisonResult = (ObjectComparisonResult) fieldComparisonResult.get(addressField);

        System.out.println("Address:");
        printSimpleComparisonResult(objectComparisonResult);
    }

    private static void printSimpleComparisonResult(ObjectComparisonResult personComparisonResult) {
        System.out.println("Similarities:");
        System.out.println();

        personComparisonResult.getSimilarities().forEach((key1, value1) -> System.out.println(key1 + ":\n" + value1 + "\n"));
        System.out.println();

        System.out.println("Differences:");
        System.out.println();

        personComparisonResult.getDifferences().forEach((key, value) -> System.out.println(key + ":\n" + value + "\n"));
        System.out.println();
    }

}
