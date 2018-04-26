package ru.neustupov.votingforrestaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.votingforrestaurants.model.Menu;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.votingforrestaurants.web.json.JsonUtil.writeIgnoreProps;

public class MenuTestData {

    public static final int RUSSIA_MENU_ID1 = START_SEQ + 7;
    public static final int UKRAINE_MENU_ID = START_SEQ + 8;
    public static final int U_KOLYANA_MENU_ID = START_SEQ + 9;
    public static final int ALMAZ_MENU_ID = START_SEQ + 10;
    public static final int FART_MENU_ID = START_SEQ + 11;
    public static final int RUSSIA_MENU_ID2 = START_SEQ + 12;
    public static final int MENU_TODAYS_WITH_MEALS_ID = START_SEQ + 13;

    public static final Menu RUSSIA_MENU1 = new Menu(RUSSIA_MENU_ID1, Date.valueOf("2015-05-01"));
    public static final Menu UKRAINE_MENU = new Menu(UKRAINE_MENU_ID, Date.valueOf("2015-05-02"));
    public static final Menu U_KOLYANA_MENU = new Menu(U_KOLYANA_MENU_ID, Date.valueOf("2015-05-03"));
    public static final Menu ALMAZ_MENU = new Menu(ALMAZ_MENU_ID, Date.valueOf("2015-05-01"));
    public static final Menu FART_MENU = new Menu(FART_MENU_ID, Date.valueOf("2015-05-02"));
    public static final Menu RUSSIA_MENU2 = new Menu(RUSSIA_MENU_ID2, Date.valueOf("2015-05-02"));

    public static final Menu MENU_TODAYS_WITH_MEALS = new Menu(MENU_TODAYS_WITH_MEALS_ID, Date.valueOf(LocalDate.now()));

    public static final List<Menu> MENUS = Arrays.asList(RUSSIA_MENU1, UKRAINE_MENU, U_KOLYANA_MENU, ALMAZ_MENU, FART_MENU, RUSSIA_MENU2);

    public static Menu getCreated() {
        return new Menu(null, Date.valueOf("2015-06-01"));
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "meals", "restaurant", "addDate");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("meals", "restaurant", "addDate").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Menu... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "addDate"));
    }

    public static ResultMatcher contentJson(Menu expected) {
        return content().json(writeIgnoreProps(expected, "addDate"));
    }
}
