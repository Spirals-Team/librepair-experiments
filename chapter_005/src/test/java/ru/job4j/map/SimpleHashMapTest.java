package ru.job4j.map;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleHashMapTest {

    private static final class User {

        public String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            User user = (User) o;

            return name != null ? name.equals(user.name) : user.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

    @Test
    public void whenInsertKeyAndValueInSimpleHashMapThenOneCanGetValueByKeyFromSimpleHashMap() {

        SimpleHashMap simpleHashMap = new SimpleHashMap();

        User first = new User("Petr");

        simpleHashMap.insert(first, "first");

        String result = (String) simpleHashMap.get(first);

        String expected = "first";

        assertThat(result, is(expected));

    }

    @Test
    public void whenDeleteFromSimpleHashMapThenOneCanNoteGetDeletedValueFromSimpleHashMap() {

        SimpleHashMap simpleHashMap = new SimpleHashMap();

        User first = new User("Petr");
        User second = new User("Serg");
        User third = new User("King");


        simpleHashMap.insert(first, "first");
        simpleHashMap.insert(second, "second");
        simpleHashMap.insert(third, "third");

        String result = (String) simpleHashMap.get(second);

        String expected = "second";

        assertThat(result, is(expected));



        simpleHashMap.delete(third);
        String result2 = (String) simpleHashMap.get(third);
        String expected2 = null;

        assertThat(result2, is(expected2));

        String result3 = (String) simpleHashMap.get(second);
        String expected3 = "second";
        assertThat(result3, is(expected3));

    }

    @Test
    public void whenAddElementsWithTheSameKeysThenGetTheLastElement() {

        SimpleHashMap simpleHashMap = new SimpleHashMap();

        User first = new User("Petr");

        simpleHashMap.insert(first, "first");
        simpleHashMap.insert(first, "second");

        String result = (String) simpleHashMap.get(first);

        String expected = "second";

        assertThat(result, is(expected));

        System.out.println(simpleHashMap.get(first));



    }

    @Test
    public void whenSimpleHashMapHasElementsThenOneCanGetKeysByIterator() {

        SimpleHashMap simpleHashMap = new SimpleHashMap();

        User first = new User("Petr");
        User second = new User("Serg");
        User third = new User("King");


        simpleHashMap.insert(first, "first");
        simpleHashMap.insert(second, "second");
        simpleHashMap.insert(third, "third");

        Iterator it = simpleHashMap.iterator();

        while (it.hasNext()) {

//            User currUser = (User) it.next();

            System.out.println(it.next());
        }

    }

}
