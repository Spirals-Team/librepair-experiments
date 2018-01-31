package ru.javawebinar.topjava;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealListSorting;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Collection;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** @author danis.tazeev@gmail.com */
public final class TestHelper {
    private TestHelper() {}

    public static final int ID_ADMIN = 1;
    public static final int ID_USER = 2;

    public static final User ADMIN = new User("Admin", "admin@example.com",
            User.password("admin"), 2000).setId(ID_ADMIN);
    public static final User USER = new User("User", "user@example.com",
            User.password("user"), 3000).setId(ID_USER);
    static {
        makeAdmin(ADMIN);
    }

    public static User makeAdmin(User user) {
        try {
            Class<User> cls = User.class;
            Field f = cls.getDeclaredField("admin");
            f.setAccessible(true);
            f.set(user, Boolean.TRUE);
            return user;
        } catch (NoSuchFieldException | IllegalAccessException neverHappens) {
            LoggerFactory.getLogger(TestHelper.class).error("makeAdmin({})", user, neverHappens);
            throw new AssertionError("just to get compiled");
        }
    }

    private static final String NAME = "John Doe";
    public static final String EMAIL = "john-doe@example.com";
    private static final byte[] PASSWORD = User.password("john-doe");
    private static final int CALORIES_PER_DAY_LIMIT = 1500;

    public static User createJohnDoe() {
        return new User(NAME, EMAIL, PASSWORD, CALORIES_PER_DAY_LIMIT);
    }

    public static LocalDateTime now() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.now().truncatedTo(MINUTES));
    }

    public static void checkSorting(Collection<Meal> meals, int userId, MealListSorting sorting) {
        LocalDate date = sorting == MealListSorting.ASC ? LocalDate.MIN : LocalDate.MAX;
        LocalTime time = LocalTime.MIN;
        for (Meal meal : meals) {
            assertEquals((Integer)userId, meal.getUser().getId());
            LocalDate when = meal.getWhen().toLocalDate();
            if (sorting.getLocalDateComparator().compare(date, when) <= 0) {
                if (Math.abs(Period.between(date, when).getDays()) >= 1) {
                    time = LocalTime.MIN;
                }
                date = meal.getWhen().toLocalDate();
            } else {
                fail("Date sorting broken");
            }
            if (time.compareTo(meal.getWhen().toLocalTime()) <= 0) {
                time = meal.getWhen().toLocalTime();
            } else {
                fail("Time sorting broken");
            }
        }
    }
}
