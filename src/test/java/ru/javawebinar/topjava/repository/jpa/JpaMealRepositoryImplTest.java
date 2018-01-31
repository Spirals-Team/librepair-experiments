package ru.javawebinar.topjava.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.TestHelper;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.TestHelper.ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_USER;
import static ru.javawebinar.topjava.TestHelper.now;

@ContextConfiguration("classpath:/spring/spring-app.xml")
@Sql("classpath:/db/refill.sql")
@RunWith(SpringRunner.class)
public class JpaMealRepositoryImplTest {
    private static final int ID_MEAL_ADMINS = 4;
    private static final int ID_NON_EXISTING_MEAL = -100;

    @Autowired
    private MealRepository repo;
    private final Meal meal = new Meal(ADMIN, now(), "Перекус", 300);

    @Test
    public void addNewMeal() throws Exception {
        repo.add(meal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNonNewMeal() throws Exception {
        meal.setId(ID_MEAL_ADMINS);
        repo.add(meal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNewMeal() throws Exception {
        repo.update(meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNonExistingMeal() throws Exception {
        meal.setId(ID_NON_EXISTING_MEAL);
        repo.update(meal);
    }

    @Test
    public void updateWithValidMeal() throws Exception {
        meal.setId(ID_MEAL_ADMINS);
        repo.update(meal);
    }

    @Test(expected = NotFoundException.class)
    public void removeNonExistingMealId() throws Exception {
        repo.remove(ID_ADMIN, ID_NON_EXISTING_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void removeExistingMealIdByWrongUserId() throws Exception {
        repo.remove(ID_USER, ID_MEAL_ADMINS);
    }

    @Test
    public void removeValidMealIdByValidUserId() throws Exception {
        repo.remove(ID_ADMIN, ID_MEAL_ADMINS);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingMealId() throws Exception {
        repo.get(ID_ADMIN, ID_NON_EXISTING_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getExistingMealIdByWrongUserId() throws Exception {
        repo.get(ID_USER, ID_MEAL_ADMINS);
    }

    @Test
    public void getExistingMealIdByValidUserId() throws Exception {
        repo.get(ID_ADMIN, ID_MEAL_ADMINS);
    }

    @Test
    public void list() throws Exception {
        Collection<Meal> meals = repo.list(ID_ADMIN, MealListSorting.DESC);
        TestHelper.checkSorting(meals, ID_ADMIN, MealListSorting.DESC);
    }
}
