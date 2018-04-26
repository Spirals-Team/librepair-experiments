package ru.neustupov.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static ru.neustupov.votingforrestaurants.MealTestData.*;
import static ru.neustupov.votingforrestaurants.MenuTestData.RUSSIA_MENU_ID1;
import static ru.neustupov.votingforrestaurants.MealTestData.assertMatch;
import static ru.neustupov.votingforrestaurants.MenuTestData.UKRAINE_MENU_ID;

public class MealServiceTest extends AbstractServiceTest{

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(APPLE_ID, RUSSIA_MENU_ID1);
        assertMatch(service.getAll(RUSSIA_MENU_ID1), BOTTLE_OF_WATER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(APPLE_ID, 1);
    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, RUSSIA_MENU_ID1);
        assertMatch(service.getAll(RUSSIA_MENU_ID1), APPLE, BOTTLE_OF_WATER, created);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(APPLE_ID, RUSSIA_MENU_ID1);
        assertMatch(actual, APPLE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(BANANAS_ID, RUSSIA_MENU_ID1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(RUSSIA_MENU_ID1), APPLE, BOTTLE_OF_WATER);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, RUSSIA_MENU_ID1);
        assertMatch(service.get(APPLE_ID, RUSSIA_MENU_ID1), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(BANANAS, RUSSIA_MENU_ID1);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Meal("   ", 10), UKRAINE_MENU_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal("Bananas", null), UKRAINE_MENU_ID), ConstraintViolationException.class);
    }
}
