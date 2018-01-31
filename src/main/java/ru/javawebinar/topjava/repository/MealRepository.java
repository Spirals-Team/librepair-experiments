package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/** @author danis.tazeev@gmail.com */
public interface MealRepository {
    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    Meal add(Meal meal);
    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    Meal update(Meal meal) throws NotFoundException;
    void remove(int userId, int mealId) throws NotFoundException;
    Meal get(int userId, int mealId) throws NotFoundException;
    Collection<Meal> list(int userId, MealListSorting sorting);
}
