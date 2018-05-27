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
public class UserAnnotationStorageTest {
    @Test
    public void whenAddUserToStorageShouldSafeIt() {
        MemoryAnnotationStorage memory = new MemoryAnnotationStorage();
        UserAnnotationStorage storage = new UserAnnotationStorage(memory);
        storage.add(new User());
    }

    @Test
    public void whenLoadContextShouldGetComponentScan() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context-three.xml");
        UserAnnotationStorage storage = context.getBean(UserAnnotationStorage.class);
        storage.add(new User());
        assertNotNull(storage);
    }
}