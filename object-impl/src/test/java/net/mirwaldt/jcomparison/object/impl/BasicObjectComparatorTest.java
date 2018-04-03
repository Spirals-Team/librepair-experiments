package net.mirwaldt.jcomparison.object.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.impl.ExceptionAtCycleComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import net.mirwaldt.jcomparison.core.string.impl.ImmutableSubstringDiff;
import net.mirwaldt.jcomparison.core.string.impl.ImmutableSubstringSimilarity;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import net.mirwaldt.jcomparison.object.facade.version_001.DefaultComparatorProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BasicObjectComparatorTest {

    final DefaultComparatorProvider comparatorProvider = new DefaultComparatorProvider(
            true, false, Function.identity(),
            new ExceptionAtCycleComparator(), (builder, internalComparatorProvider)-> {

        }
    );

    @Test
    public void test_basicObjectComparison() throws ComparisonFailedException, NoSuchFieldException {
        final DummyAddress addressOfPersonA = new DummyAddress("Verdistr.", 42);
        final DummyPerson personA = new DummyPerson("Max Huber", 33, DummyPerson.Sex.MALE, addressOfPersonA);

        final DummyAddress addressOfPersonB = new DummyAddress("Verdistr.", 44);
        final DummyPerson personB = new DummyPerson("Udo Huber", 73, DummyPerson.Sex.MALE, addressOfPersonB);

        final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> personComparator =
                comparatorProvider.provideComparator(DummyPerson.class, personA, personB);

        final ComparisonResult<?, ?, ?> personComparisonResult = personComparator.compare(personA, personB);



        assertTrue(personComparisonResult.hasSimilarities());
        final Map<Field, ValueSimilarity<Object>> personFieldSimilarities = (Map<Field, ValueSimilarity<Object>>) personComparisonResult.getSimilarities();
        assertEquals(1, personFieldSimilarities.size());

        final Field sexField = DummyPerson.class.getDeclaredField("sex");
        assertEquals(personA.getSex(), personFieldSimilarities.get(sexField).getSimilarValue());



        assertTrue(personComparisonResult.hasDifferences());
        final Map<Field, Pair<Object>> personFieldDifferences = (Map<Field, Pair<Object>>) personComparisonResult.getDifferences();
        assertEquals(1, personFieldDifferences.size());

        final Field ageField = DummyPerson.class.getDeclaredField("age");
        assertEquals(new ImmutableIntPair(personA.getAge(), personB.getAge()), personFieldDifferences.get(ageField));



        assertTrue(personComparisonResult.hasComparisonResults());
        final Map<Field, ComparisonResult<?, ?, ?>> fieldComparisonResults =
                (Map<Field, ComparisonResult<?, ?, ?>>) personComparisonResult.getComparisonResults();
        assertEquals(2, fieldComparisonResults.size());


        final Field nameField = DummyPerson.class.getDeclaredField("name");
        final SubstringComparisonResult nameComparisonResult = (SubstringComparisonResult) fieldComparisonResults.get(nameField);

        assertTrue(nameComparisonResult.hasSimilarities());
        final List<SubstringSimilarity> nameSimilarity = nameComparisonResult.getSimilarities();
        assertEquals(1, nameSimilarity.size());
        assertEquals(new ImmutableSubstringSimilarity(3, 3, " Huber"), nameSimilarity.get(0));

        assertTrue(nameComparisonResult.hasDifferences());
        final List<SubstringDifference> nameDifference = nameComparisonResult.getDifferences();
        assertEquals(1, nameDifference.size());
        assertEquals(new ImmutableSubstringDiff(0, 0, "Max", "Udo"), nameDifference.get(0));

        assertFalse(nameComparisonResult.hasComparisonResults());


        final Field addressField = DummyPerson.class.getDeclaredField("address");
        final ComparisonResult<Field, ?, ?> addressComparisonResult = (ComparisonResult<Field, ?, ?>) fieldComparisonResults.get(addressField);

        assertFalse(addressComparisonResult.hasSimilarities());


        assertTrue(addressComparisonResult.hasDifferences());
        final Map<Field, Pair<Object>> addressFieldDifferences = (Map<Field, Pair<Object>>) addressComparisonResult.getDifferences();
        assertEquals(1, addressFieldDifferences.size());

        final Field houseNumberField = DummyAddress.class.getDeclaredField("houseNumber");
        assertEquals(new ImmutableIntPair(addressOfPersonA.getHouseNumber(), addressOfPersonB.getHouseNumber()),
                addressFieldDifferences.get(houseNumberField));


        assertTrue(addressComparisonResult.hasComparisonResults());
        final Field streetNameField = DummyAddress.class.getDeclaredField("streetName");
        final SubstringComparisonResult streetNameComparisonResult = (SubstringComparisonResult) addressComparisonResult.getComparisonResults().get(streetNameField);

        assertTrue(nameComparisonResult.hasSimilarities());
        final List<SubstringSimilarity> streetNameSimilarity = streetNameComparisonResult.getSimilarities();
        assertEquals(1, streetNameSimilarity.size());
        assertEquals(new ImmutableSubstringSimilarity(0, 0, addressOfPersonA.getStreetName()), streetNameSimilarity.get(0));

        assertFalse(streetNameComparisonResult.hasDifferences());

        assertFalse(streetNameComparisonResult.hasComparisonResults());
    }
}
