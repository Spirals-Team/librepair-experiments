package ru.neustupov.votingforrestaurants.web.json;

import org.junit.Test;
import ru.neustupov.votingforrestaurants.model.Meal;

import java.util.List;

import static ru.neustupov.votingforrestaurants.MealTestData.APPLE;
import static ru.neustupov.votingforrestaurants.MealTestData.MEALS;
import static ru.neustupov.votingforrestaurants.MealTestData.assertMatch;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(APPLE);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        assertMatch(meal, APPLE);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(MEALS);
        System.out.println(json);
        List<Meal> meals = JsonUtil.readValues(json, Meal.class);
        assertMatch(meals, MEALS);
    }

}