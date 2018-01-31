package ru.javawebinar.topjava.repository;

import java.time.LocalDate;
import java.util.Comparator;

/** @author danis.tazeev@gmail.com */
public enum MealListSorting {
    ASC {
        @Override public MealListSorting getReversed() { return DESC; }
        @Override public Comparator<LocalDate> getLocalDateComparator() { return Comparator.naturalOrder(); }
    },
    DESC {
        @Override public MealListSorting getReversed() { return ASC; }
        @Override public Comparator<LocalDate> getLocalDateComparator() { return Comparator.reverseOrder(); }
    };

    public abstract MealListSorting getReversed();
    public abstract Comparator<LocalDate> getLocalDateComparator();
    public String getSqlPhrase() { return name(); }
}
