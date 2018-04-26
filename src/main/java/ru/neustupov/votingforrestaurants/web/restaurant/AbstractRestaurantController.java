package ru.neustupov.votingforrestaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.votingforrestaurants.model.Restaurant;
import ru.neustupov.votingforrestaurants.service.RestaurantService;
import ru.neustupov.votingforrestaurants.service.VoteService;
import ru.neustupov.votingforrestaurants.to.RestaurantWithVotes;
import ru.neustupov.votingforrestaurants.util.RestaurantsUtil;

import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    public List<RestaurantWithVotes> getAll() {
        log.info("getAll restaurants {}");
        return RestaurantsUtil.getWithVotes(restaurantService.getAll(),voteService.getAllForCurrentDate());
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant {}", restaurant.getName());
        return restaurantService.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {}",restaurant.getId());
        restaurantService.update(restaurant);
    }
}
