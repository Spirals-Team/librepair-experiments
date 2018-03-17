package ru.job4j.collections.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {
    /**
     * Тест.
     */
    @Test
    public void name() {
    User user = new User("123");
    User user1 = new User("1");
    UserStore userStore = new UserStore(5);
    userStore.add(user);
    userStore.add(user1);
    boolean res = userStore.delete("1");
        assertThat(res, is(true));
    }

    /**
     *Тест методов addOrRemove update getByIndex.
     */
    @Test
    public void whenElementWasUpdate() {
    UserRole roleUserStore = new UserRole(5);
    Role role = new Role("1", "Petrov");
    roleUserStore.add(role);
    role.setName("Sidorov");
    Role result = (Role) roleUserStore.update(role);
    Role ex  = (Role) roleUserStore.getByIndex(0);
    assertThat(ex, is(result));

    }
}