package ru.neustupov.votingforrestaurants.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.votingforrestaurants.model.Restaurant;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.neustupov.votingforrestaurants.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    private RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(service.getAll(), RUSSIA, UKRAINE, U_KOLYANA, ALMAZ, FART,  newRestaurant);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        service.create(new Restaurant(null, "Russia"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(RUSSIA_ID);
        assertMatch(service.getAll(), UKRAINE, U_KOLYANA, ALMAZ, FART);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = service.get(UKRAINE_ID);
        assertMatch(restaurant, UKRAINE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(U_KOLYANA);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(U_KOLYANA_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RUSSIA, UKRAINE, U_KOLYANA, ALMAZ, FART);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Restaurant("   ")), ConstraintViolationException.class);
    }
}
