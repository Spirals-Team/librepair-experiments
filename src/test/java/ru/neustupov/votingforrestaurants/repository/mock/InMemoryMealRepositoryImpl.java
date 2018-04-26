package ru.neustupov.votingforrestaurants.repository.mock;

import org.springframework.stereotype.Repository;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.repository.MealRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository{

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int menuId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(menuId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldmeal) -> meal);
    }

    @Override
    public boolean delete(int id, int menuId) {
        Map<Integer, Meal> meals = repository.get(menuId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int menuId) {
        Map<Integer, Meal> meals = repository.get(menuId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int menuId) {
        return getAllAsStream(menuId).collect(Collectors.toList());
    }

    @Override
    public Meal getWithMenu(int id) {
        return null;
    }

    private Stream<Meal> getAllAsStream(int menuId) {
        Map<Integer, Meal> meals = repository.get(menuId);
        return meals == null ?
                Stream.empty() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Meal::getId));
    }
}
