package ru.neustupov.votingforrestaurants.service;

import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.to.MealTo;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int menuId);

    void delete(int id, int menuId) throws NotFoundException;

    Meal get(int id, int menuId) throws NotFoundException;

    void update(Meal meal, int menuId);

    void update(MealTo mealTo, int menuId);

    List<Meal> getAll(int menuId);

    Meal getWithMenu(int id);
}
