package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;

import java.util.Objects;

/**
 * Instances of this class are thread-safe.
 * @author danis.tazeev@gmail.com
 */
public final class MealWithExceed {
    private final Meal meal;
    private final boolean exceed;

    MealWithExceed(Meal meal, boolean exceed) {
        Objects.requireNonNull(meal, "meal");
        this.meal = meal;
        this.exceed = exceed;
    }

    public Meal getMeal() { return meal; }
    public boolean isExceed() { return exceed; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '#' + meal.getId() + "U#" + meal.getUser().getId()
                + ':' + meal.getDesc() + '@'+ meal.getWhen() + '*'+ meal.getCalories()
                + (exceed ? ";exceeds" : "");
    }
}
