package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 04.11.17
 * @ version: java_kurs_standart
 */
public class UserTest {

    /**
     * Пробуем получить имя.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenGetNameThenReturnName() throws Exception {
        User user = new User("Name", 1, 1, 12, 1990);
        Assert.assertEquals("Name", user.getName());
    }

    /**
     * Пробуем задать имя.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenSetNameThenReturnName() throws Exception {
        User user = new User("Name", 1, 1, 12, 1990);
        user.setName("Name2");
        Assert.assertEquals("Name2", user.getName());
    }

    /**
     * Пробуем получить детей.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenGetChildrenThenReturnChildren() throws Exception {
        User user = new User("Name", 3, 1, 12, 1990);
        Assert.assertEquals(3, user.getChildren());
    }

    /**
     * Пробуем задать детей.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenSetChildrenThenReturnNewChildren() throws Exception {
        User user = new User("Name", 1, 1, 12, 1990);
        user.setChildren(4);
        Assert.assertEquals(4, user.getChildren());
    }

    /**
     * Проверим идентичность объектов.
     * поля одинаковы, хэши разные.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenEqualsFieldAndNotEqualsHashThenEqualsObject() throws Exception {
        User user = new User("Name", 1, 1, 12, 1990);
        User user2 = new User("Name", 1, 1, 12, 1990);
        Assert.assertEquals(user, user2);
    }
    /**
     * Пробуем получить birthday.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetBirthday() throws Exception {
        User user = new User("Name", 3, 1, 12, 1990);
        Calendar calendar = user.getBirthday();
    }

    /**
     * Пробуем задать birthday.
     *
     * @throws Exception исключение.
     */
    @Test
    public void trySetBirthday() throws Exception {
        User user = new User("Name", 3, 1, 12, 1990);
        user.setBirthday(5, 5, 1995);
    }

    /**
     * Пробуем сравнить объекты.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryExpectTwoUser() throws Exception {
        User user = new User("Name", 3, 1, 12, 1990);
        User user2 = new User("Name", 3, 1, 12, 1990);
        User user3 = new User("Name3", 3, 1, 12, 1990);
        User user4 = new User("Name", 13, 1, 12, 1990);
        User user5 = new User("Name", 3, 11, 12, 1990);
        User user6 = new User("Name", 3, 1, 2, 1990);
        User user7 = new User("Name", 3, 1, 12, 1991);
        Assert.assertTrue(user.equals(user2));
        Assert.assertFalse(user.equals(null));
        Assert.assertTrue(user.equals(user));
        Assert.assertFalse(user.equals(user3));
        Assert.assertFalse(user.equals(user4));
        Assert.assertFalse(user.equals(user5));
        Assert.assertFalse(user.equals(user6));
        Assert.assertFalse(user.equals(user7));
    }

}