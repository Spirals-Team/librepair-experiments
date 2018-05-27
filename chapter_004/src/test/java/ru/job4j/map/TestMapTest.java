package ru.job4j.map;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestMapTest {
    @Test
    public void whenCreatedTwoSameObjectUserWhereOverrideHashCodeAndPutInMap() {
        TestMap<UserAndHash> testMap = new TestMap<>();
        UserAndHash userOne = new UserAndHash("Name", 0, null);
        UserAndHash userTwo = new UserAndHash("Name", 0, null);
        System.out.println(String.format("Hash code User One: %d", userOne.hashCode()));
        System.out.println(String.format("Hash code User Two: %d", userTwo.hashCode()));
        System.out.print("User One equals user Two?: ");
        System.out.println(userOne.equals(userTwo));
        assertThat(userOne.equals(userTwo), is(false));
        testMap.put(userOne, 1);
        testMap.put(userTwo, 1);
        System.out.println(testMap.getMap());
    }
    @Test
    public void whenCreatedTwoSameObjectUserWhereOverrideEqualsAndPutInMap() {
        TestMap<UserAndEquals> testMap = new TestMap<>();
        UserAndEquals userOne = new UserAndEquals("Name", 0, null);
        UserAndEquals userTwo = new UserAndEquals("Name", 0, null);
        System.out.println(String.format("Hash code User One: %d", userOne.hashCode()));
        System.out.println(String.format("Hash code User Two: %d", userTwo.hashCode()));
        System.out.print("User One equals user Two?: ");
        System.out.println(userOne.equals(userTwo));
        assertThat(userOne.equals(userTwo), is(true));
        testMap.put(userOne, 1);
        testMap.put(userTwo, 1);
        System.out.println(testMap.getMap());
    }
    @Test
    public void whenCreatedTwoSameObjectUserWhereOverrideEqualsAndHashCodeAndPutInMap() {
        TestMap<UserHandE> testMap = new TestMap<>();
        UserHandE userOne = new UserHandE("Name", 0, null);
        UserHandE userTwo = new UserHandE("Name", 0, null);
        System.out.println(String.format("Hash code User One: %d", userOne.hashCode()));
        System.out.println(String.format("Hash code User Two: %d", userTwo.hashCode()));
        System.out.print("User One equals user Two?: ");
        System.out.println(userOne.equals(userTwo));
        assertThat(userOne.equals(userTwo), is(true));
        testMap.put(userOne, 1);
        testMap.put(userTwo, 1);
        System.out.println(testMap.getMap());
    }
}