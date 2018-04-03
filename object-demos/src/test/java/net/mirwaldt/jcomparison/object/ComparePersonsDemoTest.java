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
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableDoublePair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import net.mirwaldt.jcomparison.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.object.facade.builder.DefaultObjectComparatorBuilder;
import net.mirwaldt.jcomparison.object.facade.version_001.DefaultComparatorProvider;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparePersonsDemoTest {
    @Test
    public void test_comparePersons() throws ComparisonFailedException, NoSuchFieldException {
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

        final Predicate<Field> fieldPredicate = (field) -> !field.getName().equals("isMarried") 
                && !Modifier.isStatic(field.getModifiers()); // fix for jacoco

        final Function<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>, ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> preComparatorFunction =
                (itemComparator) -> new NullAcceptingComparator<>(new IdentityHandlingComparator<>(itemComparator), Function.identity());

        final DefaultComparatorProvider comparatorProvider = new DefaultComparatorProvider(
                true, false, preComparatorFunction,
                new ExceptionAtCycleComparator(), (builder, internalComparatorProvider) -> {
            if (builder instanceof DefaultObjectComparatorBuilder) {
                DefaultObjectComparatorBuilder defaultObjectComparatorBuilder = (DefaultObjectComparatorBuilder) builder;
                defaultObjectComparatorBuilder.filterFields(fieldPredicate);
            }
        }
        );

        ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> personComparator = comparatorProvider.provideComparator(Person.class, personA, personB);

        final ObjectComparisonResult personComparisonResult = (ObjectComparisonResult) personComparator.compare(personA, personB);


        assertTrue(personComparisonResult.hasSimilarities());
        final Map<Field, ValueSimilarity<Object>> similarities = personComparisonResult.getSimilarities();

        System.out.println(similarities);
        assertEquals(1, similarities.size());
        
        final Field sexField = Person.class.getDeclaredField("sex");
        assertEquals(personA.getSex(), similarities.get(sexField).getSimilarValue());


        assertTrue(personComparisonResult.hasDifferences());
        final Map<Field, Pair<Object>> differences = personComparisonResult.getDifferences();
        assertEquals(2, differences.size());

        final Field moneyInPocketField = Person.class.getDeclaredField("moneyInPocket");
        assertEquals(new ImmutableDoublePair(personA.getMoneyInPocket(), personB.getMoneyInPocket()), differences.get(moneyInPocketField));

        final Field ageField = Person.class.getDeclaredField("age");
        assertEquals(new ImmutableIntPair(personA.getAge(), personB.getAge()), differences.get(ageField));


        assertTrue(personComparisonResult.hasComparisonResults());
        final Map<Field, ComparisonResult<?, ?, ?>> fieldComparisonResult = personComparisonResult.getComparisonResults();
        assertEquals(4, fieldComparisonResult.size());


        final Field nameField = Person.class.getDeclaredField("name");
        final SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) fieldComparisonResult.get(nameField);

        assertTrue(substringComparisonResult.hasSimilarities());
        //TODO: check similarities

        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(4, substringDifference.getPositionInLeftString());
        assertEquals(4, substringDifference.getPositionInRightString());
        assertEquals("a", substringDifference.getSubstringInLeftString());
        assertEquals("", substringDifference.getSubstringInRightString());

        assertFalse(substringComparisonResult.hasComparisonResults());


        final Field featuresField = Person.class.getDeclaredField("features");
        final MapComparisonResult<Person.Feature, String> mapComparisonResult = (MapComparisonResult<Person.Feature, String>) fieldComparisonResult.get(featuresField);

        assertTrue(mapComparisonResult.hasSimilarities());
        final Map<Person.Feature, ValueSimilarity<String>> mapSimilarity = mapComparisonResult.getSimilarities();
        assertEquals(1, mapSimilarity.size());
        assertEquals(personA.getFeatures().get(Person.Feature.HAIR_COLOR), mapSimilarity.get(Person.Feature.HAIR_COLOR).getSimilarValue());

        assertTrue(mapComparisonResult.hasDifferences());
        MapDifference<Person.Feature, String> mapDifference = mapComparisonResult.getDifferences();

        final Map<Person.Feature, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());
        assertEquals(personA.getFeatures().get(Person.Feature.WRISTWATCH_BRAND), entriesOnlyInLeftMap.get(Person.Feature.WRISTWATCH_BRAND));

        final Map<Person.Feature, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInRightMap.size());
        assertEquals(personB.getFeatures().get(Person.Feature.TATTOO_TEXT), entriesOnlyInRightMap.get(Person.Feature.TATTOO_TEXT));

        final Map<Person.Feature, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());
        assertEquals(new ImmutablePair<>(personA.getFeatures().get(Person.Feature.SKIN_COLOR), personB.getFeatures().get(Person.Feature.SKIN_COLOR)),
                differentValueEntries.get(Person.Feature.SKIN_COLOR));

        assertFalse(substringComparisonResult.hasComparisonResults());


        final Field leisureActivitiesField = Person.class.getDeclaredField("leisureActivities");
        final SetComparisonResult<String> setComparisonResult = (SetComparisonResult<String>) fieldComparisonResult.get(leisureActivitiesField);

        assertTrue(setComparisonResult.hasSimilarities());
        final Set<String> similarSetElements = setComparisonResult.getSimilarities();
        assertEquals(1, similarSetElements.size());
        assertTrue(similarSetElements.contains("Swimming"));


        assertTrue(setComparisonResult.hasDifferences());
        final SetDifference<String> setDifference = setComparisonResult.getDifferences();

        final Set<String> elementsOnlyInLeftSet = setDifference.getElementsOnlyInLeftSet();
        assertEquals(1, elementsOnlyInLeftSet.size());
        assertTrue(elementsOnlyInLeftSet.contains("Piano"));

        final Set<String> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(2, elementsOnlyInRightSet.size());
        assertTrue(elementsOnlyInRightSet.contains("Tennis"));
        assertTrue(elementsOnlyInRightSet.contains("Jogging"));


        assertFalse(setComparisonResult.hasComparisonResults());


        final Field addressField = Person.class.getDeclaredField("address");
        final ObjectComparisonResult addressObjectComparisonResult = (ObjectComparisonResult) fieldComparisonResult.get(addressField);

        assertTrue(addressObjectComparisonResult.hasSimilarities());
        final Map<Field, ValueSimilarity<Object>> addressSimilarities = addressObjectComparisonResult.getSimilarities();
        assertEquals(4, addressSimilarities.size());


        final Field zipCodeField = Address.class.getDeclaredField("zipCode");
        assertEquals(personA.getAddress().getZipCode(), addressSimilarities.get(zipCodeField).getSimilarValue());

        final Field countryField = Address.class.getDeclaredField("country");
        assertEquals(personA.getAddress().getCountry(), addressSimilarities.get(countryField).getSimilarValue());

        final Field streetNameField = Address.class.getDeclaredField("streetName");
        assertEquals(personA.getAddress().getStreetName(), addressSimilarities.get(streetNameField).getSimilarValue());

        final Field cityField = Address.class.getDeclaredField("city");
        assertEquals(personA.getAddress().getCity(), addressSimilarities.get(cityField).getSimilarValue());


        assertTrue(addressObjectComparisonResult.hasDifferences());
        final Map<Field, Pair<Object>> addressDifferences = addressObjectComparisonResult.getDifferences();
        assertEquals(1, addressDifferences.size());

        final Field houseNumberField = Address.class.getDeclaredField("houseNumber");
        assertEquals(new ImmutableIntPair(personA.getAddress().getHouseNumber(), personB.getAddress().getHouseNumber()), addressDifferences.get(houseNumberField));


        assertFalse(addressObjectComparisonResult.hasComparisonResults());
    }
}
