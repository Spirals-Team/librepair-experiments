package ru.javawebinar.topjava.web.meal;

import com.google.common.base.Preconditions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.function.Predicate;

/** @author danis.tazeev@gmail.com */
public final class MealListFilteringPredicate implements Predicate<LocalDateTime> {
    private final Predicate<LocalDate> date;
    private final Predicate<LocalTime> time;

    public MealListFilteringPredicate(final LocalDate d1, final LocalTime t1, final LocalDate d2, final LocalTime t2) {
        Objects.requireNonNull(d1, "d1");
        Objects.requireNonNull(t1, "t1");
        Objects.requireNonNull(d2, "d2");
        Objects.requireNonNull(t2, "t2");

        Preconditions.checkArgument(d1.compareTo(d2) <= 0, "d1 > d2: %s > %s", d1, d2);
        Preconditions.checkArgument(t1.compareTo(t2) <= 0, "t1 > t2: %s > %s", t1, t2);

        date = d -> d1.compareTo(d) <= 0 && d.compareTo(d2) <= 0;
        time = t -> t1.compareTo(t) <= 0 && t.compareTo(t2) <= 0;
    }

    @Override
    public boolean test(LocalDateTime dt) {
        return date.test(dt.toLocalDate()) && time.test(dt.toLocalTime());
    }
}
