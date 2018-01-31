package ru.javawebinar.topjava.model;

import org.junit.Before;
import org.junit.Test;

import java.security.MessageDigest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class UserTest extends IdentifiedEntityTest {
    private static final String NAME = "User-Test";
    private static final String EMAIL = "user-test@example.com";
    private static final byte[] PASSWORD = User.password("password");
    private static final int CALORIES_PER_DAY_LIMIT = 3000;
    private static final long REGISTERED_AT = System.currentTimeMillis();

    private User user;

    @Before
    public void createStandardUser() {
        user = new User(NAME, EMAIL, PASSWORD, CALORIES_PER_DAY_LIMIT);
        setEntity(user);
    }

    @Test
    public void password() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String password = "password";
            byte[] digest = md.digest(password.getBytes("UTF-8"));
            assertArrayEquals(digest, User.password(password));
        } catch (Exception nwvwerHappens) {
            throw new AssertionError("just to get compiled");
        }
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullName() {
        new User(null, EMAIL, PASSWORD, CALORIES_PER_DAY_LIMIT);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullEmail() {
        new User(NAME, null, PASSWORD, CALORIES_PER_DAY_LIMIT);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullPassword() {
        new User(NAME, EMAIL, null, CALORIES_PER_DAY_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalPasswordLength() {
        new User(NAME, EMAIL, new byte[0], CALORIES_PER_DAY_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalCaloriesPerDayLimit() {
        new User(NAME, EMAIL, PASSWORD, -1);
    }

    @Test
    public void constructorYieldsSameName() {
        assertSame(NAME, user.getName());
    }

    @Test
    public void constructorYieldsSameEmail() {
        assertSame(EMAIL, user.getEmail());
    }

    @Test
    public void constructorStoresDefensiveCopyPassword() {
        byte[] changed = PASSWORD.clone();
        assertNotEquals(0, changed[0]);
        changed[0] = 0;
        assertArrayEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void getPasswordReturnsDefensiveCopy() {
        byte[] changed = user.getPassword();
        assertNotEquals(0, changed[0]);
        changed[0] = 0;
        assertArrayEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void constructorYieldsSameCaloriesPerDayLimit() {
        assertEquals(CALORIES_PER_DAY_LIMIT, user.getCaloriesPerDayLimit());
    }

    @Test
    public void constructorYieldsCorrectRegisteredAtTimezone() {
        User user = new User(NAME, EMAIL, PASSWORD, CALORIES_PER_DAY_LIMIT, REGISTERED_AT);
        assertEquals(REGISTERED_AT, user.getRegisteredAt());
    }

    @Test
    public void setGetNameEqual() {
        String name = "Admin";
        user.setName(name);
        assertSame(name, user.getName());
    }

    @Test
    public void setGetEmailEqual() {
        String email = "admin@example.com";
        user.setEmail(email);
        assertSame(email, user.getEmail());
    }

    @Test
    public void setGetEnabledEqual() {
        assertSame(true, user.isEnabled());
        user.setEnabled(false);
        assertSame(false, user.isEnabled());
    }

    @Test
    public void setGetCaloriesPerDayLimitEqual() {
        int caloriesPerDayLimit = 1000;
        user.setCaloriesPerDayLimit(caloriesPerDayLimit);
        assertEquals(caloriesPerDayLimit, user.getCaloriesPerDayLimit());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIllegalCaloriesPerDayLimit() {
        user.setCaloriesPerDayLimit(-1);
    }

    @Test(expected = NullPointerException.class)
    public void setNullPassword() {
        user.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIllegalPassword() {
        user.setPassword(new byte[0]);
    }

    @Test
    public void setPasswordStoresDefensiveCopy() {
        byte[] password = User.password("secr3t");
        user.setPassword(password);
        byte[] changed = password.clone();
        assertNotEquals(0, changed[0]);
        changed[0] = 0;
        assertArrayEquals(password, user.getPassword());
    }

    @Test
    public void equalsWrongClass() {
        user.setId(ID);
        Meal meal = new Meal();
        boolean equals = user.equals(meal);
        assertFalse(equals);
    }

    @Test
    public void equalsEqualId() {
        User u = new User("another-user", "some-email@example.com", User.password("s3cret"), 1000);
        u.setId(ID);
        user.setId(ID);
        boolean equals = user.equals(u);
        assertTrue(equals);
    }
}
