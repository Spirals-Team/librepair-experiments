package ru.job4j.ioc.demo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    @Test
    public void whenAddUserToStorageShouldSafeIt() {
        MemoryStorage memory = new MemoryStorage();
        UserStorage storage = new UserStorage(memory);
        storage.add(new User());
    }

    @Test
    public void whenLoadContextShouldGetBeans() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        MemoryStorage memory = context.getBean(MemoryStorage.class);
        memory.add(new User());
        assertNotNull(memory);
    }

    @Test
    public void whenLoadContextShouldGetConstructorArg() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context-two.xml");
        UserStorage storage = context.getBean(UserStorage.class);
        storage.add(new User());
        assertNotNull(storage);
    }
}