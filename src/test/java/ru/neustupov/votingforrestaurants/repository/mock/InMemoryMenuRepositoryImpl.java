package ru.neustupov.votingforrestaurants.repository.mock;

import org.springframework.stereotype.Repository;
import ru.neustupov.votingforrestaurants.model.Menu;
import ru.neustupov.votingforrestaurants.repository.MenuRepository;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMenuRepositoryImpl implements MenuRepository {

    private Map<Integer, Map<Integer, Menu>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Menu save(Menu menu, int restId) {
        Map<Integer, Menu> menus = repository.computeIfAbsent(restId, ConcurrentHashMap::new);
        if (menu.isNew()) {
            menu.setId(counter.incrementAndGet());
            menus.put(menu.getId(), menu);
            return menu;
        }
        return menus.computeIfPresent(menu.getId(), (id, oldMenu) -> menu);
    }

    @Override
    public boolean delete(int id, int restId) {
        Map<Integer, Menu> menus = repository.get(restId);
        return menus != null && menus.remove(id) != null;
    }

    @Override
    public Menu get(int id, int restId) {
        Map<Integer, Menu> menus = repository.get(restId);
        return menus == null ? null : menus.get(id);
    }

    @Override
    public List<Menu> getAll(int restId) {
        return getAllAsStream(restId).collect(Collectors.toList());
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return null;
    }

    @Override
    public Menu getTodaysMenuWithMeals(int id, Date currDate) {
        return null;
    }

    @Override
    public Menu getWithRestaurantAndMeals(int id) {
        return null;
    }

    private Stream<Menu> getAllAsStream(int restId) {
        Map<Integer, Menu> menus = repository.get(restId);
        return menus == null ?
                Stream.empty() :
                menus.values().stream()
                        .sorted(Comparator.comparing(Menu::getId));
    }
}
