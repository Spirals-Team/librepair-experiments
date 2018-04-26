package ru.neustupov.votingforrestaurants.repository;

import ru.neustupov.votingforrestaurants.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    List<Restaurant> getAll();

    default Restaurant getWithVotes(int id){
        throw new UnsupportedOperationException();
    }

    default Restaurant getWithMenus(int id){
        throw new UnsupportedOperationException();
    }

    default Restaurant getWithVotesAndMenus(int id){
        throw new UnsupportedOperationException();
    }
}
