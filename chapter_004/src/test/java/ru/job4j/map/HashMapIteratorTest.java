package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashMapIteratorTest {
    class User {
        private String name;
        private int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private HashMapIterator it;
    private HashMap<String, User> hashMap = new HashMap<>();

    @Before
    public void setUp() {
        User user1 = new User("Yury", 26);
        User user2 = new User("Kiril", 20);
        User user3 = new User("Max", 30);
        User user4 = new User("Marina", 31);
        hashMap.insert("555-666", user1);
        hashMap.insert("666-333", user2);
        hashMap.insert("777-523", user3);
        hashMap.insert("333-666", user4);
        it = new HashMapIterator(hashMap);
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        User[] arrayUser = new User[4];
        int index = 0;
        for (Object item : hashMap) {
            arrayUser[index++] = (User) item;
        }
        for (User item : arrayUser) {
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(item));
        }
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        User[] arrayUser = new User[4];
        int index = 0;
        for (Object item : hashMap) {
            arrayUser[index++] = (User) item;
        }
        for (User item : arrayUser) {
            assertThat(it.next(), is(item));
        }
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        User[] arrayUser = new User[4];
        int index = 0;
        for (Object item : hashMap) {
            arrayUser[index++] = (User) item;
        }
        for (User item : arrayUser) {
            assertThat(it.hasNext(), is(true));
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(item));
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSequentialDoesntHaveNextElementThenGetException() {
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
    }
}