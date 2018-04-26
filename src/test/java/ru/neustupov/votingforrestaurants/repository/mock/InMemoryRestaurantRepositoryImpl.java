package ru.neustupov.votingforrestaurants.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.neustupov.votingforrestaurants.model.Restaurant;
import ru.neustupov.votingforrestaurants.repository.RestaurantRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryRestaurantRepositoryImpl implements RestaurantRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRepositoryImpl.class);

    private Map<Integer, Restaurant> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            restaurant.setId(counter.incrementAndGet());
            repository.put(restaurant.getId(), restaurant);
            log.info("create {} with id = {}", restaurant, restaurant.getId());
            return restaurant;
        }
        return repository.computeIfPresent(restaurant.getId(), (id, oldRestaurant) -> restaurant);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Restaurant get(int id) {
        return repository.values().stream().filter(restaurant -> restaurant.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(Restaurant::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getWithVotes(int id) {
        return null;
    }

    @Override
    public Restaurant getWithMenus(int id) {
        return null;
    }

    @Override
    public Restaurant getWithVotesAndMenus(int id) {
        return null;
    }
}
