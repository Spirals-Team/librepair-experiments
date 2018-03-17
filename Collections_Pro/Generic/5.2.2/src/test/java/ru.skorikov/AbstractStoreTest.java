package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.10.17
 * @ version: java_kurs_standart
 */
public class AbstractStoreTest {
    /**
     * Для проверки исключений.
     */
    @Rule
    public final ExpectedException exp = ExpectedException.none();

    /**
     * Проверим добавление объекта в хранилище.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddJbjectThenReturnAddedObject() throws Exception {
        UserStore userStore = new UserStore(new SimpleArray(5));
        User user1 = new User("Id1", "name1");
        User user2 = new User("Id2", "name2");
        User user3 = new User("Id3", "name3");
        userStore.add(user1);
        userStore.add(user2);
        userStore.add(user3);

        User testUser = (User) userStore.getArray().get(1);

        Assert.assertThat(user2, is(testUser));
    }

    /**
     * Проверим обновление объекта в хранилище.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenUpdateObjectThenReturnUpdateObject() throws Exception {
        UserStore userStore = new UserStore(new SimpleArray(2));
        User user1 = new User("Id1", "name1");
        User user2 = new User("Id2", "name2");
        User user3 = new User("Id2", "name3");
        userStore.add(user1);
        userStore.add(user2);
        userStore.update(user3);

        User testUser = (User) userStore.getArray().get(1);

        Assert.assertThat(testUser, is(user3));

    }

    /**
     * Проверим удаление объекта из хранилища.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isDeleteObject() throws Exception {
        UserStore userStore = new UserStore(new SimpleArray(2));
        User user1 = new User("Id1", "name1");
        User user2 = new User("Id2", "name2");
        userStore.add(user1);
        userStore.add(user2);
        userStore.delete("Id1");

        User testUser = (User) userStore.getArray().get(0);

        Assert.assertThat(testUser, is(user2));
    }

    /**
     * Проверим что упадет исключение.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenReturnException() throws Exception {
        exp.expect(ArrayIndexOutOfBoundsException.class);
        UserStore userStore = new UserStore(new SimpleArray(1));
        User user1 = new User("Id1", "name1");
        User user2 = new User("Id2", "name2");
        userStore.add(user1);
        userStore.add(user2);
    }

}