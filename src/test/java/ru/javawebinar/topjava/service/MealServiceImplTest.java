package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.TestHelper.ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_USER;
import static ru.javawebinar.topjava.TestHelper.checkSorting;
import static ru.javawebinar.topjava.TestHelper.now;

/** @author danis.tazeev@gmail.com */
@ContextConfiguration("classpath:/spring/spring-app.xml")
@Sql("classpath:/db/refill.sql")
@RunWith(SpringRunner.class)
public class MealServiceImplTest {
    private static final int ID_MEAL_ADMINS = 4;
    private static final int ID_MEAL_USERS = 9;
    private static final int ID_NON_EXISTING_MEAL = -100;

    @ClassRule public static final TestClassStopwatch testClassStopwatch = new TestClassStopwatch();
    @Rule public final TestMethodStopwatch testMethodStopwatch = new TestMethodStopwatch(testClassStopwatch);

    @Autowired
    private MealService srvc;
    private final Meal adminsMeal = new Meal(ADMIN, now(), "Перекус", 300);

    @Test
    public void addNewMeal() throws Exception {
        srvc.add(adminsMeal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNonNewMeal() throws Exception {
        srvc.add(adminsMeal.setId(ID_MEAL_ADMINS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNewMeal() throws Exception {
        srvc.update(adminsMeal);
    }

    @Test
    public void updateWithNonNewMeal() throws Exception {
        srvc.update(adminsMeal.setId(ID_MEAL_ADMINS));
    }

    @Test(expected = NotFoundException.class)
    public void updateNonExistingMeal() throws Exception {
        srvc.update(adminsMeal.setId(ID_NON_EXISTING_MEAL));
    }

    @Test(expected = NotFoundException.class)
    public void removeAdminsMeal() throws Exception {
        srvc.remove(ID_USER, ID_MEAL_ADMINS);
    }

    @Test(expected = NotFoundException.class)
    public void removeUsersMeal() throws Exception {
        srvc.remove(ID_ADMIN, ID_MEAL_USERS);
    }

    @Test
    public void remove() throws Exception {
        srvc.remove(ID_ADMIN, ID_MEAL_ADMINS);
    }

    @Test(expected = NotFoundException.class)
    public void getAdminsMeal() throws Exception {
        srvc.get(ID_USER, ID_MEAL_ADMINS);
    }

    @Test(expected = NotFoundException.class)
    public void getUsersMeal() throws Exception {
        srvc.get(ID_ADMIN, ID_MEAL_USERS);
    }

    @Test
    public void get() throws Exception {
        srvc.get(ID_ADMIN, ID_MEAL_ADMINS);
    }

    @Test
    public void list() throws Exception {
        Collection<Meal> meals = srvc.list(ID_USER, MealListSorting.ASC);
        checkSorting(meals, ID_USER, MealListSorting.ASC);
    }
}
