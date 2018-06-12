package ru.job4j.map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashMapTest {
    class User {
        private String name;
        private int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void whenInsertThenHashMapHasElement() {
        HashMap<String, User> hashMap = new HashMap<>();
        User user = new User("Max", 26);
        assertThat(hashMap.insert("555-645", user), is(true));
        assertThat(hashMap.get("555-645"), is(user));
    }

    @Test
    public void whenInsertElementWithExistingKeyThenHashMapDoesntAddTheElement() {
        HashMap<String, User> hashMap = new HashMap<>();
        User user = new User("Max", 26);
        assertThat(hashMap.insert("555-645", user), is(true));
        assertThat(hashMap.insert("555-645", user), is(false));
    }

    @Test
    public void whenDeleteElementThenHashMapDoesntHaveTheElement() {
        HashMap<String, User> hashMap = new HashMap<>();
        User user = new User("Max", 26);
        hashMap.insert("555-645", user);
        assertThat(hashMap.delete("666"), is(false));
        assertThat(hashMap.delete("555-645"), is(true));
        assertNull(hashMap.get("555-645"));
    }

    @Test
    public void whenAmountOfElementsMoreThanHalfOfSizeOfArrayThenArrayGettingBigger() {
        HashMap<String, User> hashMap = new HashMap<>();
        assertThat(hashMap.getHashArray().length, is(10));
        hashMap.insert("555-666", new User("Yury", 27));
        hashMap.insert("666-333", new User("Maxim", 31));
        hashMap.insert("777-523", new User("Kiril", 50));
        hashMap.insert("333-666", new User("Maksim", 26));
        hashMap.insert("555-777", new User("Paul", 31));
        hashMap.insert("225-889", new User("Pavel", 18));
        assertThat(hashMap.getHashArray().length, is(20));
    }
}