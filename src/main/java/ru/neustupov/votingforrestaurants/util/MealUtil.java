package ru.neustupov.votingforrestaurants.util;

import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.to.MealTo;

public class MealUtil {

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(mealTo.getName(), mealTo.getPrice());
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setName(mealTo.getName());
        meal.setPrice(mealTo.getPrice());
        return meal;
    }
}
