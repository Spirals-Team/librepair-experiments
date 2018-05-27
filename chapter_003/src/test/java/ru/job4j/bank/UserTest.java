package ru.job4j.bank;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserTest {

    @Test
    public void whenTestGetName() {
        User user = new User("Ivan", "70 00");
        assertThat(user.getName(), is("Ivan"));
    }

    @Test
    public void whenTestGetPasport() {
        User user = new User("Ivan", "78 00");
        assertThat(user.getPasport(), is("78 00"));
    }

    @Test
    public void whenHashCodeCreatOnlyPasport() {
        User userOne = new User("Ivan", "78 00");
        User userTwo = new User("Alex", "78 00");
        assertThat(userOne.hashCode(), is(userTwo.hashCode()));
    }
}