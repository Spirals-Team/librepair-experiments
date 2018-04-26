package ru.neustupov.votingforrestaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.votingforrestaurants.model.Meal;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.votingforrestaurants.web.json.JsonUtil.writeIgnoreProps;

public class MealTestData {

    public static final int APPLE_ID = START_SEQ + 14;
    public static final int JUICE_ID = START_SEQ + 15;
    public static final int POTATO_ID = START_SEQ + 16;
    public static final int BANANAS_ID = START_SEQ + 17;
    public static final int BREAD_ID = START_SEQ + 18;
    public static final int BOTTLE_OF_WATER_ID = START_SEQ + 19;
    public static final int MEAL_IN_MENU_ID = START_SEQ + 20;

    public static final Meal APPLE = new Meal(APPLE_ID, "Apple", 5);
    public static final Meal JUICE = new Meal(JUICE_ID, "Juice", 10);
    public static final Meal POTATO = new Meal(POTATO_ID, "Potato", 20);
    public static final Meal BANANAS = new Meal(BANANAS_ID, "Bananas", 30);
    public static final Meal BREAD = new Meal(BREAD_ID, "Bread", 40);
    public static final Meal BOTTLE_OF_WATER = new Meal(BOTTLE_OF_WATER_ID, "Bottle of water", 50);

    public static final Meal MEAL_IN_MENU = new Meal(MEAL_IN_MENU_ID, "Mango", 100);

    public static final List<Meal> MEALS = Arrays.asList(APPLE, JUICE, POTATO, BANANAS, BREAD, BOTTLE_OF_WATER);

    public static Meal getCreated() {
        return new Meal("New meal", 100);
    }

    public static Meal getUpdated() {
        return new Meal(APPLE_ID, "Very good apple", 200);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Meal... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "menu"));
    }

    public static ResultMatcher contentJson(Meal expected) {
        return content().json(writeIgnoreProps(expected, "menu"));
    }
}
