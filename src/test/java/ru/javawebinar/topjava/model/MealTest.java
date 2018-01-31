package ru.javawebinar.topjava.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.TestHelper.now;

public final class MealTest extends IdentifiedEntityTest {
    private static final User USER = new User("Test-User", "test-user@example.com", User.password("password"), 15000);
    private static final LocalDateTime NOW = now();
    private static final String DESC = "desc";
    private static final int CALORIES = 500;

    private Meal meal;

    @Before
    public void createStandardMeal() {
        meal = new Meal(USER, NOW, DESC, CALORIES);
        setEntity(meal);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullUser() {
        new Meal(null, NOW, DESC, CALORIES);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullWhen() {
        new Meal(USER, null, DESC, CALORIES);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullDesc() {
        new Meal(USER, NOW, null, CALORIES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalCalories() {
        new Meal(USER, NOW, DESC, -1);
    }

    @Test
    public void constructorYieldsSameUser() {
        assertSame(USER, meal.getUser());
    }

    @Test
    public void constructorYieldsSameWhen() {
        assertSame(NOW, meal.getWhen());
    }

    @Test
    public void constructorYieldsSameDesc() {
        assertSame(DESC, meal.getDesc());
    }

    @Test
    public void constructorYieldsSameCalories() {
        assertEquals(CALORIES, meal.getCalories());
    }

    @Test
    public void setGetWhenEqual() {
        LocalDateTime dt = LocalDateTime.MIN;
        meal.setWhen(dt);
        assertSame(dt, meal.getWhen());
    }

    @Test
    public void setGetDescEqual() {
        String desc = "food";
        meal.setDesc(desc);
        assertSame(desc, meal.getDesc());
    }

    @Test
    public void setGetCaloriesEqual() {
        int calories = 0;
        meal.setCalories(calories);
        assertSame(calories, meal.getCalories());
    }

    @Test
    public void setWhenReturnsThis() {
        assertSame(meal, meal.setWhen(LocalDateTime.MAX));
    }

    @Test
    public void setDescReturnsThis() {
        assertSame(meal, meal.setDesc("meal"));
    }

    @Test
    public void setCaloriesReturnsThis() {
        assertSame(meal, meal.setCalories(100));
    }

    @Test
    public void equalsWrongClass() {
        meal.setId(ID);
        User user = new User();
        boolean equals = meal.equals(user);
        assertFalse(equals);
    }

    @Test
    public void equalsEqualId() {
        Meal m = new Meal(USER, NOW, "diff-desc", 100);
        m.setId(ID);
        meal.setId(ID);
        boolean equals = meal.equals(m);
        assertTrue(equals);
    }
}
