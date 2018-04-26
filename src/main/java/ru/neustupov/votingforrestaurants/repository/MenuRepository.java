package ru.neustupov.votingforrestaurants.repository;

import ru.neustupov.votingforrestaurants.model.Menu;

import java.sql.Date;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu, int restId);

    // false if not found
    boolean delete(int id, int restId);

    // null if not found
    Menu get(int id, int restId);

    List<Menu> getAll(int restId);

    default Menu getWithRestaurant(int id) {
        throw new UnsupportedOperationException();
    }

    default Menu getTodaysMenuWithMeals(int id, Date currDate) {
        throw new UnsupportedOperationException();
    }

    default Menu getWithRestaurantAndMeals(int id) {
        throw new UnsupportedOperationException();
    }
}
