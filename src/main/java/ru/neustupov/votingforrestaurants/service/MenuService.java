package ru.neustupov.votingforrestaurants.service;

import ru.neustupov.votingforrestaurants.model.Menu;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface MenuService {

    Menu create(Menu menu, int restId);

    void delete(int id, int restId) throws NotFoundException;

    Menu get(int id, int restId) throws NotFoundException;

    void update(Menu menu, int restId);

    List<Menu> getAll(int restId);

    Menu getTodaysMenuWithMeals(int id);

    Menu getWithRestaurant(int id);

    Menu getWitMealsAndRestaurant(int id);
}
