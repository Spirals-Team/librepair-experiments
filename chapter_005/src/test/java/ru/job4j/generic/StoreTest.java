package ru.job4j.generic;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StoreTest {
    //Add element
    @Test
    public void whenAddUserThenUserAddedInSimpleArray() {
        UserStore userStore = new UserStore(10);
        User user = new User("1", "Alex");
        userStore.add(user);
        assertThat(userStore.getElement(user.getId()).getName(), is("Alex"));
    }
    //Update element
    @Test
    public void whenUpdateElementThenElementIsUpdated() {
        UserStore userStore = new UserStore(10);
        User user = new User("1", "Alex");
        userStore.add(user);
        userStore.update(new User(user.getId(), "Ivan"));
        assertThat(userStore.getElement(user.getId()).getName(), is("Ivan"));
    }
    //Delete element
    @Test(expected = NullPointerException.class)
    public void whenDeleteElementThenElementIsDeleted() {
        UserStore userStore = new UserStore(10);
        User user = new User("1", "Alex");
        userStore.add(user);
        userStore.delete(user.getId());
        assertThat(userStore.getElement(user.getId()), null);
    }
}
