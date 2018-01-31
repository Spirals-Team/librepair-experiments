package ru.javawebinar.topjava.web.meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

/** @author danis.tazeev@gmail.com */
public abstract class MealListFilteringParams {
    private static final MealListFilteringParams NO_PARAMS = new MealListFilteringParams() {
        @Override public String getD1() { return ""; }
        @Override public String getD2() { return ""; }
        @Override public String getT1() { return ""; }
        @Override public String getT2() { return ""; }
        @Override Predicate<LocalDateTime> getPredicate() { return dt -> true; }
    };

    static MealListFilteringParams noParams() { return NO_PARAMS; }

    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     * @throws DateTimeParseException if...
     */
    @SuppressWarnings("ReferenceEquality")
    static MealListFilteringParams from(HttpServletRequest req) {
        final String d1 = req.getParameter("d1"), d2 = req.getParameter("d2");
        final String t1 = req.getParameter("t1"), t2 = req.getParameter("t2");
        if ((d1 == t2 && d2 == t1 && t1 == t2) // all nulls
                || (d1.isEmpty() && t1.isEmpty() && d2.isEmpty() && t2.isEmpty())) {
            return noParams();
        }

        assert d1 != null && d2 != null;
        assert t1 != null && t2 != null;

        return new MealListFilteringParams() { // DateTimeParseException
            private final Predicate<LocalDateTime> predicate = new MealListFilteringPredicate(
                    d1.isEmpty() ? LocalDate.MIN : LocalDate.parse(d1),
                    t1.isEmpty() ? LocalTime.MIN : LocalTime.parse(t1),
                    d2.isEmpty() ? LocalDate.MAX : LocalDate.parse(d2),
                    t2.isEmpty() ? LocalTime.MAX : LocalTime.parse(t2));

            @Override public String getD1() { return d1; }
            @Override public String getD2() { return d2; }
            @Override public String getT1() { return t1; }
            @Override public String getT2() { return t2; }
            @Override Predicate<LocalDateTime> getPredicate() { return predicate; }
        };
    }

    private MealListFilteringParams() {}

    public abstract String getD1();
    public abstract String getD2();
    public abstract String getT1();
    public abstract String getT2();
    abstract Predicate<LocalDateTime> getPredicate();
}
