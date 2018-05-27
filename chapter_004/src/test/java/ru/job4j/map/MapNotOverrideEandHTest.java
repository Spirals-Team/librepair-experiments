package ru.job4j.map;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MapNotOverrideEandHTest {
    MapNotOverrideEandH mapNotOverrideEandH = new MapNotOverrideEandH();

    @Test
    public void whenCreatedTwoSameObjectUserAndPutInMap() {
        User userOne = new User("Name", 0, null);
        User userTwo = new User("Name", 0, null);
        System.out.println(String.format("Hash code User One: %d", userOne.hashCode()));
        System.out.println(String.format("Hash code User Two: %d", userTwo.hashCode()));
        System.out.print("User One equals user Two?: ");
        System.out.println(userOne.equals(userTwo));
        assertThat(userOne.equals(userTwo), is(false));
        mapNotOverrideEandH.put(userOne, 1);
        mapNotOverrideEandH.put(userTwo, 1);
        System.out.println(mapNotOverrideEandH.getMap());
    }
}