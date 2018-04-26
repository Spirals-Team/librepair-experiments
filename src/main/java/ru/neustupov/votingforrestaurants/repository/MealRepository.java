package ru.neustupov.votingforrestaurants.repository;

import ru.neustupov.votingforrestaurants.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int menuId);

    // false if not found
    boolean delete(int id, int menuId);

    // null if not found
    Meal get(int id, int menuId);

    List<Meal> getAll(int menuId);

    default Meal getWithMenu(int id){
        throw new UnsupportedOperationException();
    }
}
