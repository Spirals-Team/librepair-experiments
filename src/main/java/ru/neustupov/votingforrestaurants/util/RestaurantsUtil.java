package ru.neustupov.votingforrestaurants.util;

import ru.neustupov.votingforrestaurants.model.Restaurant;
import ru.neustupov.votingforrestaurants.model.Vote;
import ru.neustupov.votingforrestaurants.to.RestaurantWithVotes;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantsUtil {

    private RestaurantsUtil() {
    }

    public static List<RestaurantWithVotes> getWithVotes(Collection<Restaurant> restaurants, List<Vote> allVotes){
        return restaurants.stream()
                .map(restaurant -> createWithVotes(restaurant, allVotes))
                .collect(Collectors.toList());
    }

    private static RestaurantWithVotes createWithVotes(Restaurant restaurant, List<Vote> allVotes){
        List<Vote> votes = allVotes.stream()
                .filter(vote -> vote.getRestaurant().getId().equals(restaurant.getId()))
                .collect(Collectors.toList());
        return new RestaurantWithVotes(restaurant.getId(), restaurant.getName(), votes.size());
    }
}
