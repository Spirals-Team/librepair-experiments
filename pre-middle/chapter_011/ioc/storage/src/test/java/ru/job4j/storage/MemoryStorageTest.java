package ru.job4j.storage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.UserStorage;
import ru.job4j.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test.
 */
public class MemoryStorageTest {

    private User user;
    private UserStorage storage;

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("storage-context.xml");
        storage = ctx.getBean(UserStorage.class);
        user = new User().setId(1L).setEmail("user@user.email").setPassw("1234");
        storage.create(user);
    }

    @Test
    public void findOne() {
        assertThat(storage.findOne(user.getId()), is(user));
    }

    @Test
    public void findAll() {
        assertThat(storage.findAll(), hasSize(1));
    }

    @Test
    public void create() {
        User newUser = new User().setId(2L).setEmail("new@user.email").setPassw("4321");
        assertThat(storage.create(newUser), is(2L));
    }

    @Test
    public void update() {
        User newUser = new User().setId(1L).setEmail("new@user.email").setPassw("4321");
        storage.update(newUser);
        assertThat(storage.findOne(1L), not(user));
        assertThat(storage.findOne(1L), is(newUser));
        assertThat(storage.findAll(), hasSize(1));
    }

    @Test
    public void delete() {
        assertThat(storage.findAll(), hasSize(1));
        storage.delete(user);
        assertThat(storage.findAll(), hasSize(0));
    }

    @Test
    public void deleteById() {
        assertThat(storage.findAll(), hasSize(1));
        storage.deleteById(user.getId());
        assertThat(storage.findAll(), hasSize(0));
    }
}