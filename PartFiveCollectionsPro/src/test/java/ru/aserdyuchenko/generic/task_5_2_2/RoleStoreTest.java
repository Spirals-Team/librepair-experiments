package ru.aserdyuchenko.generic.task_5_2_2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

/**
 * @author anton.
 */
public class RoleStoreTest {
    /**
     * @param store - new store.
     */
    private RoleStore<Role> store = new RoleStore();
    /**
     * @param role - new role.
     */
    private Role role = new Role("157");

    /**
     * Add role.
     */
    @Test
    public void whenAddRole() {
        store.add(role);
        Role result = store.get(0);

        assertThat(result.getId(), is("157"));
    }

    /**
     * Update role.
     */
    @Test
    public void whenUpdateRole() {
        Role roleNew = new Role("2017");

        store.add(role);
        store.update(0, roleNew);
        Role result = store.get(0);

        assertThat(result.getId(), is("2017"));
    }

    /**
     * Delete role.
     */
    @Test
    public void whenDeleteRole() {
        store.add(role);
        store.delete(0);

        assertNull(store.get(0));
    }

}