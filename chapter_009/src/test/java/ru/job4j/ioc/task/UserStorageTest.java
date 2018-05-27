package ru.job4j.ioc.task;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    private ApplicationContext context;
    private Storage<User> storage;

    @Before
    public void sutUp() {
        context = new ClassPathXmlApplicationContext("/context.xml");
        storage = context.getBean(MemoryStorage.class);
    }

    @Test
    public void whenLoadContextShouldGetBeans() {
        assertNotNull(storage);
    }

    @Test
    public void whenLoadContextShouldGetBeansAndAddEntity() {
        int id = 1;
        String name = "Test";
        User user = new User();
        user.setId(id);
        user.setName(name);
        assertTrue(storage.add(user));
        assertNotNull(storage.getUsers());
        assertThat(storage.getUsers().size(), is(1));
        assertThat(storage.getUsers().get(0).getId(), is(id));
        assertThat(storage.getUsers().get(0).getName(), is(name));
    }

    @Test
    public void whenLoadContextShouldGetBeansAndFindEntity() {
        int id = 1;
        String name = "Test";
        User user = new User(id, name);
        storage.add(user);
        assertTrue(storage.find(user));
        assertThat(storage.find(name), is(new User(1, "Test")));
        assertThat(storage.find(1), is(new User(1, "Test")));
        assertFalse(storage.find(new User()));
        assertNull(storage.find(2));
    }

    @Test
    public void whenLoadContextShouldGetBeansAndDelEntity() {
        int id = 1;
        String name = "Test";
        User user = new User(id, name);
        storage.add(user);
        assertTrue(storage.del(id));
        assertThat(storage.getUsers().size(), is(0));
        assertFalse(storage.del(id));
        storage.add(user);
        assertTrue(storage.del(user));
    }
}