package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;


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
     * Пробуем задать день рождения.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenSetBirthdayThenReturnNewBirthday() throws Exception {
        User user = new User("Name", 1, 1, 12, 1990);
        user.setBirthday(15, 3, 1991);

        Assert.assertThat(user.getBirthday().getTime().getDate(),  is(15));
        Assert.assertThat(user.getBirthday().getTime().getMonth(),  is(2));
        Assert.assertThat(user.getBirthday().getTime().getYear(),  is(91));
    }

}