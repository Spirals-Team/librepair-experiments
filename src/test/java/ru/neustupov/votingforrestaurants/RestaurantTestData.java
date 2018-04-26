package ru.neustupov.votingforrestaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.votingforrestaurants.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.votingforrestaurants.web.json.JsonUtil.writeIgnoreProps;

public class RestaurantTestData {

    public static final int RUSSIA_ID = START_SEQ + 2;
    public static final int UKRAINE_ID = START_SEQ + 3;
    public static final int U_KOLYANA_ID = START_SEQ + 4;
    public static final int ALMAZ_ID = START_SEQ + 5;
    public static final int FART_ID = START_SEQ + 6;

    public static final Restaurant RUSSIA = new Restaurant(RUSSIA_ID, "Russia");
    public static final Restaurant UKRAINE = new Restaurant(UKRAINE_ID, "Ukraine");
    public static final Restaurant U_KOLYANA = new Restaurant(U_KOLYANA_ID, "U Kolyana");
    public static final Restaurant ALMAZ = new Restaurant(ALMAZ_ID, "Almaz");
    public static final Restaurant FART = new Restaurant(FART_ID, "Fart");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(writeIgnoreProps(expected));
    }
}
