package ru.javawebinar.topjava.repository;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.repository.MealListSorting.ASC;
import static ru.javawebinar.topjava.repository.MealListSorting.DESC;

/** @author danis.tazeev@gmail.com */
public final class MealListSortingTest {
    @Test
    public void ascReversed() {
        assertEquals(DESC, ASC.getReversed());
    }

    @Test
    public void ascComparator() {
        assertEquals(Comparator.naturalOrder(), ASC.getLocalDateComparator());
    }

    @Test
    public void ascSqlPhrase() {
        assertEquals(ASC.name(), ASC.getSqlPhrase());
    }

    @Test
    public void descReversed() {
        assertEquals(ASC, DESC.getReversed());
    }

    @Test
    public void descComparator() {
        assertEquals(Comparator.reverseOrder(), DESC.getLocalDateComparator());
    }

    @Test
    public void descSqlPhrase() {
        assertEquals(DESC.name(), DESC.getSqlPhrase());
    }
}
