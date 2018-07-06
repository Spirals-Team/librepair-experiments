package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class RoleStoreTest {

    private RoleStore userStore;

    @Before
    public void getUp() {
        userStore = new RoleStore(5);
    }

    @Test
    public void whenAddRoleThenUserStoreHasTheRole() {
        Role role = new Role("first");
        userStore.add(role);
        assertThat(userStore.findById("first"), is(role));
    }

    @Test
    public void whenReplaceRoleWithTheIdThenRoleStoreHasNewRoleWithTheSameId() {
        userStore.add(new Role("first"));
        Role role = new Role("second");
        userStore.replace("first", role);
        assertThat(userStore.findById("second"), is(role));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteRoleWithTheIdThenRoleStoreHasNewRoleWithTheSameId() {
        Role role = new Role("first");
        userStore.add(role);
        assertThat(userStore.findById("first"), is(role));
        userStore.delete("first");
        userStore.findById("first");
    }
}