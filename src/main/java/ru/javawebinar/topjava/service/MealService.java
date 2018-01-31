package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;

public interface MealService {
    /**
     * @throws NullPointerException if {@code meal == null}
     * @throws IllegalArgumentException if {@code meal} is not {@link Meal#isNew() new}
     */
    Meal add(Meal meal);
    /**
     * @throws NullPointerException if {@code meal == null}
     * @throws IllegalArgumentException if {@code meal} is {@link Meal#isNew() new}
     * @throws NotFoundException if the DB does not contain a meal with the {@link Meal#getId() meal.id}
     * or {@link User#getId() user.id} of the meal in the DB is not equal to {@link User#getId() user.id}
     * of the given {@code meal}
     */
    void update(Meal meal) throws NotFoundException;
    /**
     * @throws NotFoundException if the DB does not contain a meal with the {@code mealId}
     * or {@link User#getId() user.id} of the meal in the DB is not equal to {@code userId}
     */
    void remove(int userId, int mealId) throws NotFoundException;
    /**
     * @throws NotFoundException if the DB does not contain a meal with the {@code mealId}
     * or {@link User#getId() user.id} of the meal in the DB is not equal to {@code userId}
     */
    Meal get(int userId, int mealId) throws NotFoundException;
    /** @throws NullPointerException if {@code sorting == null} */
    Collection<Meal> list(int userId, MealListSorting sorting);
}
