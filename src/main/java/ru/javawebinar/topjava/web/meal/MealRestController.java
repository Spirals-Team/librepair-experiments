package ru.javawebinar.topjava.web.meal;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AuthenticatedUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Controller
public final class MealRestController {
    private final MealService srvc;

    @Autowired
    MealRestController(MealService srvc) {
        Objects.requireNonNull(srvc, "srvc");
        this.srvc = srvc;
    }

    public void save(Meal meal) throws NotFoundException {
        Preconditions.checkArgument(meal.getUser().equals(AuthenticatedUser.getUser()), "not authorized");
        if (meal.isNew()) {
            srvc.add(meal);
        } else {
            srvc.update(meal);
        }
    }

    public void delete(int mealId) throws NotFoundException {
        srvc.remove(AuthenticatedUser.getUser().getId(), mealId);
    }

    public Meal get(int mealId) throws NotFoundException {
        return srvc.get(AuthenticatedUser.getUser().getId(), mealId);
    }

    public Collection<MealWithExceed> list(MealListSorting sorting, Predicate<LocalDateTime> filtering) {
        Objects.requireNonNull(sorting, "sorting");
        Objects.requireNonNull(filtering, "filtering");
        return meals2dtos(srvc.list(AuthenticatedUser.getUser().getId(), sorting),
                filtering, AuthenticatedUser.getUser().getCaloriesPerDayLimit());
    }

    private static Collection<MealWithExceed> meals2dtos(
            Collection<Meal> meals, Predicate<LocalDateTime> filtering, int caloriesPerDayLimit) {
        assert meals != null;
        assert filtering != null;

        final class Aggregate {
            private final List<Meal> meals = new ArrayList<>();
            private int sumOfCalories;

            private void accumulate(Meal meal) {
                sumOfCalories += meal.getCalories();
                if (filtering.test(meal.getWhen())) {
                    meals.add(meal);
                }
            }

            // never invoked if the upstream is sequential
            private Aggregate combine(Aggregate that) {
                /*
                this.sumOfCalories += that.sumOfCalories;
                this.meals.addAll(that.meals);
                return this;
                */
                throw new UnsupportedOperationException("MUST never be invoked! Ensure the upstream is sequential");
            }

            private Stream<MealWithExceed> stream() {
                final boolean exceed = sumOfCalories > caloriesPerDayLimit;
                return meals.stream().map(meal -> new MealWithExceed(meal, exceed));
            }
        }

        return meals.stream()
                .collect(groupingBy(meal -> meal.getWhen().toLocalDate(), LinkedHashMap::new, // WARN: non-thread-safe
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine)))
                .values().stream().flatMap(Aggregate::stream).collect(toList());
    }
}
